package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Runner;
import com.wy.doctor.service.RunnerService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/runner")
public class RunnerController {

    @Autowired
    private RunnerService runnerService;

    // getId
    /**
     *  127.0.0.1/runner/get/1 ---- get
     */
    @PostMapping("/get")
    public Runner getRunnerById(@RequestBody int id){
        System.out.println(id);
        return runnerService.getRunnerById(id);
    }
    @PostMapping("/getRunner")
    public Runner getRunnerByNamePhone(@RequestBody Runner runner){
        System.out.println(runner);
        if( runner.getRunnerPhone()!=null)
            return runnerService.getRunnerByPhone(runner.getRunnerPhone());
        else if( runner.getRunnerName()!=null)
            return runnerService.getRunnerByName(runner.getRunnerName());
        else
            return null;
    }

    // login_1  通过账号，密码登录
    /**
     *  127.0.0.1/runner/login ---- post
     *  {"account":"673011", "password":"673011"}
     */
    // login_2
    /**
     *  {"account":"13547123456", "password":"673011"}
     * */
    @PostMapping("/login")
    public Result<Runner> loginByAccount(@RequestBody Runner runner){
        System.out.println(runner);
        if( runner.getAccount().length() == 11)
            return runnerService.getRunnerByPhone(runner.getRunnerPhone(), runner.getCaptcha());
        else if( runner.getAccount() != null && runner.getPassword() != null){
            return runnerService.getRunnerByAccount(runner.getAccount(), runner.getPassword());
        }
        return Result.failed("请输入账号密码，或者选择手机登录");
    }

    // add/insert   针对账号注册
    /**
     *  127.0.0.1/runner/add  ---- post
     *  {"runnerPhone":"13541123456", "password":"673011"}
     */
    @PostMapping("/add")
    public Result<Runner> addRunner(@RequestBody Runner runner){
        System.out.println(runner);
        return runnerService.insertRunnerByPhone(runner);
    }
    // update
    /**
     *  127.0.0.1/runner/update ---- put
     *
     */
    @PutMapping("/update")
    public Result<Runner> updateRunner(@RequestBody Runner runner){
        System.out.println(runner);
        return runnerService.updateRunner(runner);
    }
    // delete
    /**
     *  127.0.0.1/runner/delete/2 ---- delete
     */
    @DeleteMapping("/delete")
    public Result<Object> deleteRunner(@RequestBody int id){
        System.out.println(id);
        return runnerService.deleteRunner(id);
    }
    // pageInfo
    /**
     * 127.0.0.1/runner/list ---- post
     * {"currentPage":1, "pageSize":5, "sort":"rid", "direction":"desc", "keyword":""}
     */
    @PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<Runner> getRunnersBySearch(@RequestBody Search search) {
        System.out.println(search);
        return runnerService.getRunnersBySearch(search);
    }
}
