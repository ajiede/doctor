package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.User;
import com.wy.doctor.service.UserService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired

    private UserService userService;


    // getId

    /**
     * 127.0.0.1/user/1 ---- get
     */
    @PostMapping("/get")
    public User getUserById(@RequestBody int id) {
        return userService.getUserById(id);
    }

    // login_1  通过账号，密码登录
    /**
     *  127.0.0.1/user/login ---- post
     *  {"account":"630101", "password":"630101"}
     */
    // login_2

    /**
     * {"userPhone":"13547933839","captcha":"657933"}
     */
    @PostMapping("/login")
    public Result<User> loginByAccount(@RequestBody User user) {
        System.out.println(user);
        if (user.getUserPhone().length() == 11) {
            System.out.println("phone");
            return userService.getUserByPhone(user.getUserPhone(), user.getCaptcha());
        } else if(user.getAccount() != null && user.getPassword() != null) {
            System.out.println("account");
            return userService.getUserByAccount(user.getAccount(), user.getPassword());
        }
        return Result.failed("请输入账号密码，或者选择手机登录");
    }

    // add/insert   针对账号注册

    /**
     * 127.0.0.1/user/add  ---- post
     * {"userPhone":"13547933839","password":"679901"}
     */
    @PostMapping("/add")
    public Result<User> addUser(@RequestBody User user) {
        System.out.println(user);
        return userService.insertUserWithPhone(user);
    }
    // update

    /**
     * 127.0.0.1/user/update ---- put
     * {"uid":2,"userName":"uu","password":"123456","userAge":18,"userGender":0,"userHome":"四川省成都市","userIDCard":"510121200101010101"}
     */
    @PutMapping("/update")
    public Result<User> updateUser(@RequestBody User user) {
        System.out.println(user);
        return userService.updateUser(user);
    }
    // delete

    /**
     * 127.0.0.1/user/delete ---- delete
     */
    @DeleteMapping("/delete")
    public Result<Object> deleteUser(@RequestBody int id) {
        System.out.println(id);
        return userService.deleteUser(id);
    }
    // pageInfo

    /**
     * 127.0.0.1/user/list ---- post
     * {"currentPage":1, "pageSize":5, "sort":"uid", "direction":"desc", "keyword":""}
     */
    @PostMapping(value = "/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<User> getUsersBySearch(@RequestBody Search search) {
        System.out.println(search);
        return userService.getUsersBySearch(search);
    }
}
