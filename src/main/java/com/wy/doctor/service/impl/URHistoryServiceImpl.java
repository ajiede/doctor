package com.wy.doctor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.URHistoryDao;
import com.wy.doctor.entity.Hospital;
import com.wy.doctor.entity.Runner;
import com.wy.doctor.entity.URHistory;
import com.wy.doctor.entity.User;
import com.wy.doctor.service.HospitalService;
import com.wy.doctor.service.RunnerService;
import com.wy.doctor.service.URHistoryService;
import com.wy.doctor.service.UserService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class URHistoryServiceImpl implements URHistoryService {

    @Autowired
    URHistoryDao urHistoryDao;
    @Autowired
    UserService userService;
    @Autowired
    RunnerService runnerService;
    @Autowired
    HospitalService hospitalService;
//  查看的详细的历史记录
    @Override
    public Result<URHistory> getOneURHistory(int affairs_id) {
        URHistory temp = urHistoryDao.getOneHistory(affairs_id);
        if( temp == null )
            return Result.failed("未查找到该条记录");
        return Result.ok(temp);
    }

    @Override
    public URHistory getURHistoryById(int affairs_id) {
        return urHistoryDao.getOneHistory(affairs_id);
    }

    @Override
    public void updateURHistoryById(int affairs_id, int pay_state) {
        urHistoryDao.updateState(affairs_id, pay_state);
    }

    //    针对  某一个人 查询他的历史
    @Override
    public List<URHistory> getAllURHistory(URHistory urHistory) {
        if (urHistory.getUid() != null ) {
            return urHistoryDao.getAllHistory("uid", urHistory.getUid());
        } else if (urHistory.getRid() != null) {
            return urHistoryDao.getAllHistory("rid", urHistory.getRid());
        } else if (urHistory.getHid() != null) {
            return urHistoryDao.getAllHistory("hid", urHistory.getHid());
        } else
            return null;
    }

//    针对 事务 完成 后将相关内容添加到历史里面
    @Override
    public Result<URHistory> insertURHistory(URHistory urHistory) {
        urHistoryDao.insertHistory(urHistory);
        return Result.ok(urHistory);
    }
// 更新历史记录的信息
    @Override
    public Result<URHistory> updateURHistory(URHistory urHistory) {
        urHistoryDao.updateHistory(urHistory);
        return Result.ok(urHistory);
    }

// 查询两个人之间的历史记录,或者三个人的历史记录
    @Override
    public List<URHistory> getPartURHistory(URHistory urHistory) {
        urHistory.setUid(null);
        urHistory.setRid(null);
        urHistory.setHid(null);
        User user;
        Runner runner;
        Hospital hospital;
        if(urHistory.getUserName() != null){
            System.out.println(1);
            user = userService.getUserByName(urHistory.getUserName());
            urHistory.setUid(user.getUid());
        }
        if(urHistory.getRunnerName() != null){
            System.out.println(2);
            runner = runnerService.getRunnerByName(urHistory.getRunnerName());
            urHistory.setRid(runner.getRid());
        }
        if( urHistory.getHospitalName() != null) {
            System.out.println(3);
            hospital = hospitalService.getHospitalByName(urHistory.getHospitalName());
            urHistory.setHid(hospital.getHid());
        }
        if( urHistory.getUid() != null && urHistory.getRid() != null && urHistory.getHid() != null ){
            System.out.println("urh");
            return urHistoryDao.getPartHistory(urHistory.getUid(), urHistory.getRid(), urHistory.getHid());
        }
        else if( urHistory.getUid() != null && urHistory.getRid() != null ){
            System.out.println("ur");
            return urHistoryDao.getPartHistoryWithTwo("uid", urHistory.getUid(), "rid", urHistory.getRid());
        }
        else if( urHistory.getUid() != null && urHistory.getHid() != null ){
            System.out.println("uh");
            return urHistoryDao.getPartHistoryWithTwo("uid", urHistory.getUid(), "hid", urHistory.getHid());
        }
        else if( urHistory.getRid() != null && urHistory.getHid() != null ){
            System.out.println("rh");
            return urHistoryDao.getPartHistoryWithTwo("rid", urHistory.getRid(), "hid", urHistory.getHid());
        }
        return null;
    }
//  分页查询
    @Override
    public PageInfo<URHistory> getURHistoryBySearch(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(urHistoryDao.getURHistoryBySearch(search))
                .orElse(Collections.emptyList()));
    }
}
