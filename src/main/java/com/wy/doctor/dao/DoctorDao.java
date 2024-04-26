package com.wy.doctor.dao;

import com.wy.doctor.entity.Doctor;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface DoctorDao {
    //根据医院id查
    @Select("SELECT * FROM graduation.doctor WHERE hid=#{hid}")
    List<Doctor> getByHid(int hid);
    // 根据医生id查询
    @Select("SELECT * FROM graduation.doctor WHERE doctor_id=#{doctor_id}")
    Doctor getByDid(int doctor_id);

    //添加
    @Insert("INSERT INTO graduation.doctor" +
            "(hid,doctorName,department,office_hours," +
            "monday,tuesday,wednesday,thursday,friday,saturday,sunday,remark)" +
            "values" +
            "(#{hid},#{doctorName},#{department},#{office_hours},#{monday},#{tuesday}," +
            "#{wednesday},#{thursday},#{friday},#{saturday},#{sunday},#{remark})")
    void insertDoctor(int hid, Doctor doctor);

    //获取出诊医生信息
    @Select("SELECT * FROM graduation.doctor WHERE doctor_id=#{doctor_id}")
    Doctor getHouseCallInfo(int doctor_id);
    @Select("SELECT * FROM graduation.doctor WHERE hid=#{hid} AND doctorName = #{doctorName}")
    Doctor getDoctorInfo(int hid, String doctorName);

    // 更新信息
    @Update("UPDATE graduation.doctor " +
            "SET doctorName = #{doctorName}, " +
            "department = #{department}, " +
            "office_hours = #{office_hours}, " +
            "monday = #{monday}, " +
            "tuesday = #{tuesday}, " +
            "wednesday = #{wednesday}, " +
            "thursday = #{thursday}, " +
            "friday = #{friday}, " +
            "saturday = #{saturday}, " +
            "sunday = #{sunday}, " +
            "remark = #{remark} " +
            "WHERE doctor_id = #{doctor_id}")
    void updateDoctorInfo(Doctor doctor);

    // 更新数量
    @Update({
            "<script>",
            "UPDATE graduation.doctor",
            "<set>",
            "<choose>",
            "<when test='day == \"Monday\"'>",
            "SET monday=#{number}",
            "</when>",
            "<when test='day == \"Tuesday\"'>",
            "SET tuesday=#{number}",
            "</when>",
            "<when test='day == \"Wednesday\"'>",
            "SET wednesday=#{number}",
            "</when>",
            "<when test='day == \"Thursday\"'>",
            "SET thursday=#{number}",
            "</when>",
            "<when test='day == \"Friday\"'>",
            "SET friday=#{number}",
            "</when>",
            "<when test='day == \"Saturday\"'>",
            "SET saturday=#{number}",
            "</when>",
            "<when test='day == \"Sunday\"'>",
            "SET sunday=#{number}",
            "</when>",
            "</choose>",
            "</set>",
            "WHERE doctor_id=#{doctor_id}",
            "</script>"
    })
    void updateDoctorOutpatients(@Param("number") int number, @Param("day") String day, @Param("doctor_id") int doctor_id);

    //删除
    @Delete("DELETE from graduation.doctir WHERE doctor_id=#{doctor_id}")
    void deleteDoctor(int doctor_id);

    @Select("<script>"
            + "select * from graduation.doctor "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " and (doctorName like '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " order by ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by doctor_id desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Doctor> getDoctorBySearch(Search search);

    @Select("SELECT * from graduation.doctor WHERE hid=#{hid}")
    List<Doctor> getDoctorByHid(int hid);
}
