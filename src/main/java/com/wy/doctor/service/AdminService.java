package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Admin;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;

public interface AdminService {
    Result<Admin> getAdminById(int aid);
    List<Admin> getAllAdmin();
    Result<Admin> getAdminByPassword(String account, String password);
    Result<Admin> insertAdmin(Admin admin);
    Result<Admin> updateAdmin(Admin admin);
    Result<Object> deleteAdmin(int aid);
    PageInfo<Admin> getAdminBySearch(Search search);
}
