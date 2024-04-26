package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Affairs;
import com.wy.doctor.service.AffairsService;
import com.wy.doctor.service.URHistoryService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/affairs")
public class AffairsController {

    @Autowired
    private AffairsService affairsService;

    // 增加
    @PostMapping("/add")
    public Result<Affairs> insertAffairs(@RequestBody Affairs affairs){
        System.out.println(affairs);
        return affairsService.insertAffairs(affairs);
    }

    // 修改
    @PostMapping("/update")
    public Result<Affairs> updateAffairs(@RequestBody Affairs affairs){
        System.out.println(affairs);
        return affairsService.updateAffairs(affairs);
    }
    /// 接收事务
    @PostMapping("/accept")
    public Result<Affairs> acceptAffairs(@RequestBody Affairs affairs){
        System.out.println("接收事务：\n" + affairs);
        return affairsService.acceptAffairs(affairs);
    }
    /// 开始事务
    @PostMapping("/start")
    public Result<Affairs> startAffairs(@RequestBody Affairs affairs){
        System.out.println("开始事务:\n" + affairs);
        return affairsService.startAffairs(affairs);
    }
    /// 结束事务
    @PostMapping("/end")
    public Result<Affairs> endAffairs(@RequestBody Affairs affairs){
        System.out.println("结束事务:\n" + affairs);
        return affairsService.endAffairs(affairs);
    }
    // 删除
    @DeleteMapping("/delete")
    public Result<Object> deleteAffair(@RequestBody Affairs affairs){
        System.out.println(affairs);
        return affairsService.deleteAffairs(affairs);
    }
    // 查询
    /// 一条
    @PostMapping("/get")
    public Result<Affairs> getAffairsById(@RequestBody Affairs affairs){
        System.out.println(affairs);
        return affairsService.getAffairsById(affairs.getAffairs_id());
    }
    @PostMapping("/getId")
    public Result<Affairs> getAffairsById(@RequestBody int id){
        System.out.println(id);
        return affairsService.getAffairsById(id);
    }
    /// 分页
    @GetMapping("/listA")
    public PageInfo<Affairs> getAffairsBySearchA(@RequestBody Search search){
        System.out.println("listA"+search);
        return affairsService.getAffairsBySearchA(search);
    }
    @PostMapping("/listB")
    public PageInfo<Affairs> getAffairsBySearchB(@RequestBody Search search){
        System.out.println("listB"+search);
        return affairsService.getAffairsBySearchB(search);
    }

    @PostMapping("/listById")
    public PageInfo<Affairs> getAffairsById(@RequestBody Search search){
        System.out.println("listId"+search);
        return affairsService.getAffairsBySearchUid(search);
    }
}
