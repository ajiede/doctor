package com.wy.doctor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.HospitalDao;
import com.wy.doctor.entity.Hospital;
import com.wy.doctor.service.HospitalService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalDao hospitalDao;

    @Override
    public Hospital getHospitalById(int hid) {
        Hospital temp = hospitalDao.getById(hid);
        return temp;
    }

    @Override
    public Hospital getHospitalByName(String name) {
        Hospital temp = hospitalDao.getByName(name);
        return temp;
    }

    @Override
    public List<Hospital> getAllHospital() {
        return hospitalDao.findAll();
    }

    @Override
    public List<Hospital> getHospitalByAddress(String address) {
        List<Hospital> hospitalList = hospitalDao.getHospitalByAddress(address);
//        System.out.println(hospitalList);
        return hospitalList;
    }

    @Override
    public Result<Hospital> getHospitalByAccount(String account, String password) {
        Hospital temp = hospitalDao.getByAccount(account);
        if( temp == null || !temp.getPassword().equals(password) )
            return Result.failed("账号密码错误");
        if (temp != null && temp.getPassword().equals(password) )
            return Result.ok(temp);
        else
            return Result.failed("账号密码错误");
    }

    @Override
    public Result<Hospital> insertHospital(Hospital hospital) {
        // 判断是否 账号重复问题
//        Hospital temp = hospitalDao.getByAccount( hospital.getAccount() );
//        if( temp != null)
//            return Result.failed("账号重复");
        hospitalDao.insertHospital(hospital);
        hospital = hospitalDao.getByAccount(hospital.getAccount());
        return Result.ok(hospital);
    }

    @Override
    public Result<Hospital> updateHospital(Hospital hospital) {
        hospitalDao.updateHospital(hospital);
        return Result.ok(hospital);
    }

    @Override
    public Result<Object> deleteHospital(int hid) {
        hospitalDao.deleteHospital(hid);
        return Result.ok("删除成功");
    }

    @Override
    public PageInfo<Hospital> getHospitalBySearch(Search search) {
        // 初始化search对象
        search.initSearch();
        // 开启分页
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        // 返回 pageInfo 对象
        return new PageInfo<>(Optional
                .ofNullable(hospitalDao.getHospitalBySearch(search))
                .orElse(Collections.emptyList()));
    }
}
