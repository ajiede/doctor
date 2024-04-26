package com.wy.doctor.controller;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Doctor;
import com.wy.doctor.service.DoctorService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // 根据医生id获取信息
    @PostMapping("/getDoctor")
    public Doctor getDoctorById(@RequestBody int doctor_id){
        System.out.println(doctor_id);
        return doctorService.getDoctorById(doctor_id);
    }
    // 医院通查(只能医院分页查询)
    @PostMapping("/getList")
    public PageInfo<Doctor> getDoctorWithSearch(@RequestBody Search search){
        System.out.println(search);
        return doctorService.getDoctorBySearch(search);
    }
    @GetMapping("/getListByHid")
    public List<Doctor> getDoctorByHid(@RequestParam("hid")int hid){
        System.out.println("hid:"+hid);
        return doctorService.getDoctorByHid(hid);
    }

    @PostMapping("/insert")
    public Result<Doctor> insertDoctor(@RequestBody Doctor doctor){
        System.out.println(doctor);
        return doctorService.insertDoctor(doctor.getHid(), doctor);
    }

    @PutMapping("/update")
    public Result<Doctor> updateDoctor(@RequestBody Doctor doctor){
        System.out.println(doctor);
        return doctorService.updateDoctorInfo(doctor);
    }

    @DeleteMapping("/delete")
    public Result<Object> deleteDoctor(@RequestBody int doctor_id){
        System.out.println(doctor_id);
        return doctorService.deleteDoctor(doctor_id);
    }
}
