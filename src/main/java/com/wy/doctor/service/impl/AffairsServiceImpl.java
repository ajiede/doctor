package com.wy.doctor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.AffairsDao;
import com.wy.doctor.entity.Affairs;
import com.wy.doctor.entity.URHistory;
import com.wy.doctor.service.AffairsService;
import com.wy.doctor.service.URHistoryService;
import com.wy.doctor.utils.MinioUtil;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class AffairsServiceImpl implements AffairsService {
    @Autowired
    private AffairsDao affairsDao;
    @Autowired
    private URHistoryService urHistoryService;

    @Autowired
    private MinioUtil minioUtil;

    @Override
    public Result<Affairs> getAffairsById(int affairs_id) {
        Affairs temp = affairsDao.getOneAffairs(affairs_id);
        System.out.println(temp);
        return Result.ok(temp);
    }

    @Override
    public List<Affairs> getAllAffairs() {
        return affairsDao.getAllAffairs();
    }

    @Override
    public Result<Affairs> insertAffairs(Affairs affairs) {
        // 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        Date currentDate = Date.from(now.atZone(ZoneId.systemDefault()).toInstant());
        affairs.setCreateTime(currentDate);

        long timeDifferenceInMillis = affairs.getStartTime().getTime() - currentDate.getTime();

        // 比较设置状态
        if (timeDifferenceInMillis < 72 * 60 * 60 * 1000) {
            affairs.setState(0);
        } else {
            affairs.setState(1);
        }
        //添加事务
        affairsDao.insertAffairs(affairs);
        // 获取事务id
        Affairs temp = affairsDao.getAffairsByUid(affairs.getUid(), affairs.getHid(), affairs.getCreateTime());
        System.out.println(temp);
        // 更新到历史中
        return Result.ok(temp);
    }

    @Override
    public Result<Affairs> updateAffairs(Affairs affairs) {
        affairsDao.updateAffairs(affairs);
        return Result.ok(affairs);
    }

    @Override
    @Transactional
    public Result<Affairs> acceptAffairs(Affairs affairs) {
        affairsDao.acceptAffairs(affairs.getAffairs_id(), affairs.getState());
        return Result.ok("接单成功");
    }

    @Override
    public Result<Affairs> startAffairs(Affairs affairs) {
        affairsDao.startAffairs(affairs.getAffairs_id(), affairs.getStartTime(), affairs.getState());
        Affairs temp = affairsDao.getOneAffairs(affairs.getAffairs_id());
        return Result.ok(temp);
    }

    @Override
    public Result<Affairs> endAffairs(Affairs affairs) {
        affairsDao.startAffairs(affairs.getAffairs_id(), affairs.getEndTime(), affairs.getState());
        Affairs temp = affairsDao.getOneAffairs(affairs.getAffairs_id());
        return Result.ok(temp);
    }

    @Override
    public Result<Object> deleteAffairs(Affairs affairs) {
        // 取消时 需要查看 是否是进行时，是否需要退款
        // 推荐 urHistory.pay_state == 9
        affairsDao.deleteAffairs( affairs.getAffairs_id() );
        return Result.ok("取消成功");
    }

    @Override
    public PageInfo<Affairs> getAffairsBySearchA(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(affairsDao.getAffairsBySearchA(search))
                .orElse(Collections.emptyList()));
    }

    @Override
    public PageInfo<Affairs> getAffairsBySearchUid(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        if (search.getSort() != null && search.getSort().equalsIgnoreCase("UID")) {
            return new PageInfo<>(Optional
                    .ofNullable(affairsDao.getAffairsBySearchUid(search))
                    .orElse(Collections.emptyList()));
        }else if (search.getSort() != null && search.getSort().equalsIgnoreCase("HID")){
            return new PageInfo<>(Optional
                    .ofNullable(affairsDao.getAffairsBySearchHid(search))
                    .orElse(Collections.emptyList()));
        }else
            return null;
    }

    @Override
    public PageInfo<Affairs> getAffairsBySearchB(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        List<Affairs> affairsList = affairsDao.getAffairsBySearchC(search);
        for (int i = 0; i < affairsList.size(); i++) {
            // 获得第i个对象
            Affairs affair = affairsList.get(i);
            // 寻找真路径
            String url = minioUtil.preview(affair.getUserAvatarUrl());
            affair.setUserAvatarUrl(url);
            // 将修改后的结果修改到原本的位置
            affairsList.set(i,affair);
        }
        return new PageInfo<>(affairsList);
    }

}
