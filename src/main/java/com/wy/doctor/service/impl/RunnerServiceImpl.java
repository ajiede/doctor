package com.wy.doctor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.RunnerDao;
import com.wy.doctor.entity.Runner;
import com.wy.doctor.service.RunnerService;
import com.wy.doctor.utils.MinioUtil;
import com.wy.doctor.utils.MyRandomUtil;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RunnerServiceImpl implements RunnerService {
    @Autowired
    RunnerDao runnerDao;
    @Autowired
    private MinioUtil minioUtil;

    MyRandomUtil myRandom = new MyRandomUtil();

    private RedisServiceImpl redisService;

    @Override
    public Runner getRunnerById(int rid) {
        Runner runner = runnerDao.getById(rid);
        return runner;
    }

    @Override
    public Runner getRunnerByPhone(String phone) {
        Runner runner = runnerDao.getByPhone(phone);
        return runner;
    }

    @Override
    public Runner getRunnerByName(String name) {
        Runner temp = runnerDao.getByName(name);
        return temp;
    }

    @Override
    public List<Runner> getAllRunner() {
        return runnerDao.findAll();
    }

    @Override
    public Result<Runner> getRunnerByAccount(String account, String password) {
        Runner temp = runnerDao.getByAccount(account);
        if( temp == null)
            return Result.failed("账号密码错误");
        if (temp.getPassword().equals(password) ){
            String avatarUrl = minioUtil.preview( temp.getRunnerAvatarUrl() );
            if( avatarUrl.isEmpty() || avatarUrl.length() < 6){
                if( temp.getRunnerGender() == 0)
                    avatarUrl = minioUtil.preview("runner/man.png");
                else
                    avatarUrl = minioUtil.preview("runner/woman.png");
            }
            temp.setRunnerAvatarUrl(avatarUrl);
            return Result.ok(temp);
        }
        else
            return Result.failed("账号密码错误");
    }

    @Override
    public Result<Runner> getRunnerByPhone(String phone, String captcha) {
        Runner runner = runnerDao.getByPhone(phone);
        if( runner == null )
            return Result.failed("该电话未被注册");
        String cap = redisService.getVerificationCode(phone);
        if(!captcha.equalsIgnoreCase(cap))
            return Result.failed("验证码错误");
        else{
            String avatarUrl = minioUtil.preview(runner.getRunnerAvatarUrl());
            if( avatarUrl.isEmpty() || avatarUrl.length() < 6){
                if( runner.getRunnerGender() == 0)
                    avatarUrl = minioUtil.preview("runner/man.png");
                else
                    avatarUrl = minioUtil.preview("runner/woman.png");
            }
            runner.setRunnerAvatarUrl(avatarUrl);
            return Result.ok(runner);
        }
    }

    @Override
    public Result<Runner> insertRunner(Runner runner) {
        // 根据电话号码是否存在判断是否注册
        Runner temp = runnerDao.getByAccount( runner.getAccount() );
        if( temp != null)
            return Result.failed("该账号已被注册。");
        // 拿到照片，存储minioUtil
        runnerDao.insertRunner(runner);
        return Result.ok(runner);
    }

    @Override
    public Result<Runner> insertRunnerByPhone(Runner runner) {
        Runner temp = runnerDao.getByPhone(runner.getRunnerPhone());
        if( temp != null)
            return Result.failed("该电话已被注册");
        String cap = redisService.getVerificationCode(runner.getRunnerPhone());
        if( !runner.getCaptcha().equalsIgnoreCase(cap) ){
            return Result.failed("验证码输入错误");
        }
        temp = new Runner();
        temp.setRunnerPhone(runner.getRunnerPhone());
        // 查看随机生成的账号是否重复
        String account = myRandom.randomString(8);
        Runner test = runnerDao.getByAccount(account);
        while(true){
            if( test == null)
                break;
            else
                account = myRandom.randomString(8);
            test = runnerDao.getByAccount(account);
        }
        // 添加账号
        temp.setAccount(account);
        temp.setPassword(runner.getPassword());
        runnerDao.insertRunner(temp);
        // 返回rid
        temp = runnerDao.getByAccount(temp.getAccount());
        return Result.ok(temp);
    }

    @Override
    public Result<Runner> updateRunner(Runner runner) {
        Runner temp = runnerDao.getById(runner.getRid());
        if( temp == null)
            return  Result.failed("查无此人，无法修改。");
        runnerDao.updateRunner(runner);
        return Result.ok(runner);
    }

    @Override
    public Result<Object> deleteRunner(Runner runner) {
        runnerDao.deleteRunner(runner.getRid());
        return Result.ok("删除成功。");
    }

    @Override
    public Result<Object> deleteRunner(int id) {
        runnerDao.deleteRunner(id);
        return Result.ok("删除成功。");
    }

    @Override
    public PageInfo<Runner> getRunnersBySearch(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(runnerDao.getRunnersBySearch(search))
                .orElse(Collections.emptyList()));
    }
}
