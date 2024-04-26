package com.wy.doctor.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wy.doctor.dao.DoctorDao;
import com.wy.doctor.entity.Doctor;
import com.wy.doctor.service.DoctorService;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorDao doctorDao;

    @Override
    public List<Doctor> getAllDoctor(int hid) {
        return doctorDao.getByHid(hid);
    }

    @Override
    public Doctor getDoctorById(int doctor_id) {
        Doctor temp = doctorDao.getByDid(doctor_id);
//        return Result.ok(temp);
        return temp;
    }

    @Override
    public Result<Doctor> insertDoctor(int hid, Doctor doctor) {
        Doctor temp = doctorDao.getDoctorInfo(hid, doctor.getDoctorName());
        if( temp != null){
            // 遇到有重复的内容，需要更新原来的内容
            temp.setDoctorName(temp.getDoctorName() + "（"+ temp.getDoctor_id()+"）");
            doctorDao.updateDoctorInfo(temp);
        }
        doctorDao.insertDoctor(hid, doctor);
        return Result.ok(doctor);
    }


    @Override
    public Result<Doctor> getHouseCallInfo(int doctor_id) {
        Doctor temp = doctorDao.getHouseCallInfo(doctor_id);
        if( temp == null)
            return Result.failed("未查询到此人");
        return Result.ok(temp);
    }

    @Override
    public Result<Doctor> updateDoctorInfo(Doctor doctor) {
        doctorDao.updateDoctorInfo(doctor);
        return Result.ok(doctor);
    }

    @Override
    public Result<Doctor> updateDoctorOutpatients(Doctor doctor) {
        // 预约非今天的时间
        updateDoctorInfo(doctor);
        Doctor temp = doctorDao.getHouseCallInfo(doctor.getDoctor_id());
        return Result.ok(temp);
    }

    @Override
    public Result<Object> deleteDoctor(int doctor_id) {
        doctorDao.deleteDoctor(doctor_id);
        return Result.ok();
    }

    @Override
    public PageInfo<Doctor> getDoctorBySearch(Search search) {
        search.initSearch();
        PageHelper.startPage(search.getCurrentPage(), search.getPageSize());
        return new PageInfo<>(Optional
                .ofNullable(doctorDao.getByHid( Integer.parseInt(search.getKeyword()) ))
                .orElse(Collections.emptyList()));
    }

    @Override
    public List<Doctor> getDoctorByHid(int hid) {
        return doctorDao.getDoctorByHid(hid);
    }
}
