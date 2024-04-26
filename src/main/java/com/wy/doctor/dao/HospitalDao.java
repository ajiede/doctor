package com.wy.doctor.dao;

import com.wy.doctor.entity.Hospital;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface HospitalDao {

    @Select("SELECT * FROM graduation.hospital")
    List<Hospital> findAll();

    @Select("SELECT * from graduation.hospital WHERE hid=#{hid}")
    Hospital getById(int hid);
    @Select("SELECT * FROM graduation.hospital WHERE hospitalName=#{hospitalName}")
    Hospital getByName(String hospitalName);
    @Select("SELECT * FROM graduation.hospital WHERE account=#{account}")
    Hospital getByAccount(String account);

    @Insert("INSERT INTO graduation.hospital " +
            "(hospitalName, account, password, hospitalIDCard, hospitalCreateTime, hospitalAddress, " +
            "hospitalPhone, hospitalEmail, hospitalURL, zipCode, hospitalRemark, hospitalOverview) " +
            "VALUES " +
            "(#{hospitalName}, #{account}, #{password}, #{hospitalIDCard}, #{hospitalCreateTime}, " +
            "#{hospitalAddress}, #{hospitalPhone}, #{hospitalEmail}, #{hospitalURL}, #{zipCode}, " +
            "#{hospitalRemark}, #{hospitalOverview})")
    void insertHospital(Hospital hospital);

    @Update("UPDATE graduation.hospital " +
            "SET hospitalName = #{hospitalName}, " +
            "password = #{password}, " +
            "hospitalIDCard = #{hospitalIDCard}, " +
            "hospitalCreateTime = #{hospitalCreateTime}, " +
            "hospitalAddress = #{hospitalAddress}, " +
            "hospitalPhone = #{hospitalPhone}, " +
            "hospitalEmail = #{hospitalEmail}, " +
            "hospitalURL = #{hospitalURL}, " +
            "zipCode = #{zipCode}, " +
            "hospitalRemark = #{hospitalRemark}, " +
            "hospitalOverview = #{hospitalOverview} " +
            "WHERE hid = #{hid}")
    void updateHospital(Hospital hospital);

    @Delete("DELETE from graduation.hospital WHERE hid=#{hid}")
    void deleteHospital(int hid);

    @Select("<script>"
            + "select * from graduation.hospital "
            + "<where>"
            + "<if test='keyword != \"\" and keyword != null'>"
            + " and (hospitalName like '%${keyword}%' "
            + " or hospitalAddress like '%${keyword}%' "
            + " or hospitalRemark like '%${keyword}%' "
            + " or hospitalOverview like '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " order by ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by hid desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Hospital> getHospitalBySearch(Search search);

    @Select("SELECT * FROM graduation.hospital WHERE hospitalAddress like '%${keyword}%'")
    List<Hospital> getHospitalByAddress(String keyword);
}
