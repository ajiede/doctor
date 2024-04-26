package com.wy.doctor.dao;

import com.wy.doctor.entity.Admin;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AdminDao {
    //全查
    @Select("SELECT * from graduation.admin")
    List<Admin> findAll();

    //id
    @Select("Select * from graduation.admin WHERE aid=#{aid}")
    Admin getById(int aid);

    //account
    @Select("SELECT * FROM graduation.admin WHERE account=#{account}")
    Admin getByAccount(String account);

    // 插入
    @Insert("INSERT INTO graduation.admin " +
            "(account, password, adminName, department, power) " +
            "VALUES " +
            "(#{account}, #{password}, #{adminName}, #{department}, #{power})")
    void insertAdmin(Admin admin);

    // 更新
    @Update("UPDATE graduation.admin " +
            "SET password = #{password}, " +
            "adminName = #{adminName}, " +
            "department = #{department}, " +
            "power = #{power} " +
            "WHERE aid = #{aid}")
    void updateAdmin(Admin admin);

    //删除
    @Delete("DELETE FROM graduation.admin WHERE aid=#{aid}")
    void deleteAdmin(int aid);

    @Select("<script>"
            + "select * from graduation.admin "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " and (adminName like '%${keyword}%' or department like '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " order by ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by aid desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Admin> getAdminBySearch(Search search);
}
