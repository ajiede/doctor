package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.User;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;

public interface UserService {
    User getUserById(int uid);
    User getUserByPhone(String phone);
    List<User> getAllUser();
    User getUserByName(String name);
    // login
    Result<User> getUserByAccount(String account, String password);
    Result<User> getUserByPhone(String Phone, String captcha);
    // 采用账号密码，注册，需要填写基本信息
    Result<User> insertUser(User user);
    // 采用电话注册，需要跳转页面去补全信息
    Result<User> insertUserWithPhone(User user);
    // 个人信息修改，admin修改信息
    Result<User> updateUser(User user);
    // 删除(admin)/注销(user)
    Result<Object> deleteUser(int uid);
    // 分页查询
    PageInfo<User> getUsersBySearch(Search search);
}
