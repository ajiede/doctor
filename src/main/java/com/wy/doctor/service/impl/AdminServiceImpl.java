package com.wy.doctor.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.AdminDao;
import com.wy.doctor.entity.Admin;
import com.wy.doctor.service.AdminService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminDao adminDao;

    @Override
    public Result<Admin> getAdminById(int aid) {
        Admin temp = adminDao.getById(aid);
        if( temp == null )
            return Result.failed("查无此人");
        return Result.ok(temp);
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminDao.findAll();
    }

    @Override
    public Result<Admin> getAdminByPassword(String account, String password) {
        Admin temp = adminDao.getByAccount(account);
        if(temp != null && temp.getPassword().equals(password) ){
//            StpUtil.login(temp.getAid());
//            temp.setToken(StpUtil.getTokenValue());
            return Result.ok(temp);
        }
        return Result.failed("账号密码错误");
    }

    @Override
    public Result<Admin> insertAdmin(Admin admin) {
        Admin temp = adminDao.getByAccount(admin.getAccount());
        if(temp != null)
            return Result.failed("该账号已存在");
        adminDao.insertAdmin(admin);
        return Result.ok(admin);
    }

    @Override
    public Result<Admin> updateAdmin(Admin admin) {
        adminDao.updateAdmin(admin);
        return Result.ok(admin);
    }

    @Override
    public Result<Object> deleteAdmin(int aid) {
        adminDao.deleteAdmin(aid);
        return Result.ok();
    }

    @Override
    public PageInfo<Admin> getAdminBySearch(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(adminDao.getAdminBySearch(search))
                .orElse(Collections.emptyList()));
    }


}
