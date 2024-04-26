package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Runner;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;
import java.util.Objects;

public interface RunnerService {
    Runner getRunnerById(int rid);
    Runner getRunnerByPhone(String phone);
    Runner getRunnerByName(String name);
    List<Runner> getAllRunner();
    // 电话，验证码
    Result<Runner> getRunnerByPhone(String phone, String captcha);
    // 账号，密码
    Result<Runner> getRunnerByAccount(String account, String password);
    // 账号注册
    Result<Runner> insertRunner(Runner runner);
    // 手机注册
    Result<Runner> insertRunnerByPhone(Runner runner);
    Result<Runner> updateRunner(Runner runner);
    Result<Object> deleteRunner(Runner runner);
    Result<Object> deleteRunner(int id);
    PageInfo<Runner> getRunnersBySearch(Search search);
}
