package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Hospital;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;

public interface HospitalService {
    Hospital getHospitalById(int hid);
    Hospital getHospitalByName(String name);
    List<Hospital> getAllHospital();
    List<Hospital> getHospitalByAddress(String address);
    Result<Hospital> getHospitalByAccount(String account, String password);
    // 医院只能账号注册
    Result<Hospital> insertHospital(Hospital hospital);
    Result<Hospital> updateHospital(Hospital hospital);
    Result<Object> deleteHospital(int hid);
    PageInfo<Hospital> getHospitalBySearch(Search search);
}
