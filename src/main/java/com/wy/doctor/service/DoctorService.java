package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Doctor;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;

public interface DoctorService {
    List<Doctor> getAllDoctor(int hid);
    Doctor getDoctorById(int doctor_id);
    Result<Doctor> insertDoctor(int hid, Doctor doctor);
    Result<Doctor> getHouseCallInfo(int doctor_id);
    Result<Doctor> updateDoctorInfo(Doctor doctor);
    Result<Doctor> updateDoctorOutpatients(Doctor doctor);
    Result<Object> deleteDoctor(int doctor_id);
    PageInfo<Doctor> getDoctorBySearch(Search search);
    List<Doctor> getDoctorByHid(int hid);
}
