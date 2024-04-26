package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Hospital;
import com.wy.doctor.service.HospitalService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hospital")
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    // id
    /**
     *  127.0.0.1/hospital/1 ---- get
     * */
    @PostMapping("/get")
    public Hospital getHospitalById(@RequestBody int id){
        return hospitalService.getHospitalById(id);
    }
    // login
    /**
     *  127.0.0.1/hospital/login ---- post
     *  {"account":"790364","password":"790364"}
     * */
    @PostMapping("/login")
    public Result<Hospital> loginByAccount(@RequestBody Hospital hospital){
        return hospitalService.getHospitalByAccount(hospital.getAccount(), hospital.getPassword());
    }
    // insert
    /**
     *  127.0.0.1/hospital/insert ---- post
     * */
    @PostMapping("/insert")
    public Result<Hospital> addHospital(@RequestBody Hospital hospital){
        System.out.println(hospital);
        return hospitalService.insertHospital(hospital);
    }
    // update
    /**
     *  127.0.0.1/hospital/update ---- put
     * */
    @PutMapping("/update")
    public Result<Hospital> updateHospital(@RequestBody Hospital hospital){
        System.out.println(hospital);
        return hospitalService.updateHospital(hospital);
    }
    // delete
    /**
     * 127.0.0.1/hospital/delete ---- delete
     */
    @DeleteMapping("/delete")
    public Result<Object> deleteHospital(@RequestBody int id){
        System.out.println(id);
        return hospitalService.deleteHospital(id);
    }
    @GetMapping("/getAll")
    public List<Hospital> getAllHospitalNameList(@RequestParam("addressKey")String addressKey){
        // 找到“市”字符的索引
        int index = addressKey.indexOf("市");
        if (index != -1) {
            addressKey = addressKey.substring(0, index+1);
        }
        System.out.println("模糊："+addressKey);
        return hospitalService.getHospitalByAddress(addressKey);
    }
    // page
    /**
     * 127.0.0.1/hospital/list ---- post
     * {"currentPage":1, "pageSize":5, "sort":"hid", "direction":"desc", "keyword":""}
     */
    @PostMapping(value="/list", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PageInfo<Hospital> getUsersBySearch(@RequestBody Search search) {
        System.out.println(search);
        return hospitalService.getHospitalBySearch(search);
    }
}