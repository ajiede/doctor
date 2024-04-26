package com.wy.doctor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.UserDao;
import com.wy.doctor.entity.User;
import com.wy.doctor.service.UserService;
import com.wy.doctor.utils.MinioUtil;
import com.wy.doctor.utils.MyRandomUtil;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private RedisServiceImpl redisService;

    // 存储图片服务器
    @Autowired
    private MinioUtil minioUtil;
    private MyRandomUtil myRandom = new MyRandomUtil();


    @Override
    public User getUserById(int uid) {
        User temp = userDao.getById(uid);
        return temp;
    }

    @Override
    public User getUserByPhone(String phone) {
        User temp = userDao.getByPhone(phone);
        return temp;
    }

    @Override
    public List<User> getAllUser() {
        return userDao.findAll();
    }

    @Override
    public User getUserByName(String name) {
        User temp = userDao.getByName(name);
        return temp;
    }

    @Override
    public Result<User> getUserByAccount(String account, String password) {
        User temp = userDao.getByAccount(account);
        if( temp == null )
            return Result.failed("账号密码错误");
        if( temp.getPassword().equals(password) ) {
            String urlPath = minioUtil.preview(temp.getUserAvatarUrl());
            //System.out.println("头像url"+urlPath);
            temp.setUserAvatarUrl(urlPath);
            System.out.println(temp);
            return Result.ok(temp);
        }
        else
            return Result.failed("账号密码错误");
    }

    @Override
    public Result<User> getUserByPhone(String phone, String captcha)
    {
        User temp = userDao.getByPhone(phone);
        // 在minIO中找到url

        // 账号存在 且密码正确
        if( temp == null)
            return Result.failed("该账号未被注册");
        String cap = redisService.getVerificationCode(phone);
        if( !captcha.equalsIgnoreCase(cap))
            return Result.failed("验证码错误");
        else{
            //登录成功，删除验证码
            redisService.deleteVerificationCode(phone);
            return Result.ok(temp);
        }
    }

    @Override
    public Result<User> insertUser(User user) {
        User temp = userDao.getByAccount(user.getAccount());
        // 判断是否已经注册过账号
        if(temp != null)
            return Result.failed("该账号已被注册，请重新输入账号。");
        userDao.insertUser(user);
        // 拿到数据库中对应uid
        temp = userDao.getByAccount(user.getAccount());
        return Result.ok(temp);
    }

    @Override
    public Result<User> insertUserWithPhone(User user) {
        // 比较验证码
        User temp = userDao.getByPhone(user.getUserPhone());
        if( temp != null)
            return Result.failed("该电话已被注册");
        String cap = redisService.getVerificationCode(user.getUserPhone());
        if( !user.getCaptcha().equalsIgnoreCase(cap) )
            return Result.failed("验证码输入错误");
        // 验证成功后，删除验证码
        redisService.deleteVerificationCode(user.getUserPhone());
        temp = new User();
        // 为空，补充电话信息，获得uid
        temp.setUserPhone(user.getUserPhone());
        // 随机生成账号,并将生成的内容与数据库已存在的账号做比较，查看是否重复
        String account = myRandom.randomString(8);

        User test = userDao.getByAccount(account);
        while(true){
            if( test == null)
                break;
            else
                account = myRandom.randomString(8);
            test = userDao.getByAccount(account);
        }

        temp.setAccount(account);
        temp.setPassword(user.getPassword());
        // 将生成的账号添加到数据库中
        userDao.insertUser(temp);
        // 刷新temp 补充uid信息
        temp = userDao.getByPhone(user.getUserPhone());
        return Result.ok(temp);
    }

    @Override
    public Result<User> updateUser(User user) {
        userDao.updateUser(user);
        user = userDao.getByAccount(user.getAccount());
        return Result.ok(user);
    }

    @Override
    public Result<Object> deleteUser(int uid) {
        // 删除对应user
        userDao.delete(uid);
        return Result.ok("删除成功。");
    }

    @Override
    public PageInfo<User> getUsersBySearch(Search search) {
        // 初始化search对象
        search.initSearch();
        // 开启分页
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        // 返回 pageinfo 对象
        return new PageInfo<>(Optional
                .ofNullable(userDao.getUsersBySearch(search))
                .orElse(Collections.emptyList()));
    }
}
