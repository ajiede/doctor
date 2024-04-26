package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.URHistory;
import com.wy.doctor.service.URHistoryService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class URHistoryController {

    @Autowired
    URHistoryService urHistoryService;
    // 增加

    @PostMapping("/add")
    public Result<URHistory> insertURHistory(@RequestBody URHistory urHistory){
        System.out.println(urHistory);
        return urHistoryService.insertURHistory(urHistory);
    }

    // 修改 -金额，url，评分
    @PutMapping("/update")
    public  Result<URHistory> updateURHistory(@RequestBody URHistory urHistory){
        System.out.println(urHistory);
        return urHistoryService.updateURHistory(urHistory);
    }

    // 查询
    // affairs_id
    @PostMapping("/get")
    public Result<URHistory> hetURHistoryById(@RequestBody URHistory urHistory){
        System.out.println(urHistory);
        return urHistoryService.getOneURHistory(urHistory.getAffairs_id());
    }
    // 个人历史记录查询
    @PostMapping("/getHistory")
    public List<URHistory> getHistoryWithOne(@RequestBody URHistory urHistory){
        System.out.println(urHistory);
        return urHistoryService.getAllURHistory(urHistory);
    }
    // 双人
    @PostMapping("/getDouble")
    public List<URHistory> getURHistoryWithTwo(@RequestBody URHistory urHistory) {
        System.out.println(urHistory);
        List<URHistory> urHistories = urHistoryService.getPartURHistory(urHistory);
        System.out.println(urHistories);
        return urHistories;
    }
    // 分页
    @PostMapping("/list")
    PageInfo<URHistory> getURHistoryBySearch(@RequestBody Search search){
        System.out.println(search);
        return urHistoryService.getURHistoryBySearch(search);
    }
}
