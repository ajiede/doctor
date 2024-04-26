package com.wy.doctor.dao;

import com.wy.doctor.entity.User;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserDao {
    @Select("SELECT * FROM graduation.user WHERE uid=#{uid} ")
    User getById(int uid);

    @Select("SELECT * FROM graduation.user WHERE userName=#{userName}")
    User getByName(String userName);

    // 根据电话
    @Select("SELECT * FROM graduation.user WHERE userPhone=#{phone}")
    User getByPhone(String phone);

    // 根据账号
    @Select("SELECT * FROM graduation.user WHERE account=#{account}")
    User getByAccount(String account);

    @Select("Select * from graduation.user")
    List<User> findAll();

    @Insert("INSERT INTO graduation.user(userName, account,password, userAge, userGender, userPhone, " +
            "userAvatarUrl, userHome, userIDCard, userRemark) " +
            "VALUES(#{userName}, #{account},#{password},#{userAge},#{userGender},#{userPhone},#{userAvatarUrl}," +
            "#{userHome}, #{userIDCard}, #{userRemark})")
    void insertUser(User user);

    // 实名认证
    @Insert("UPDATE graduation.user SET userIDCard=#{userIDCard}) WHERE uid=#{uid}")
    void identityVerification(@Param("uid") int uid, @Param("userIDCard") String userIDCard);

    @Update("UPDATE graduation.user " +
            "SET userName=#{userName}," +
            "password=#{password}, " +
            "userAge=#{userAge}, " +
            "userGender=#{userGender}, " +
            "userPhone=#{userPhone}, " +
            "userAvatarUrl=#{userAvatarUrl}, " +
            "userHome=#{userHome}, " +
            "userIDCard=#{userIDCard}, " +
            "userRemark=#{userRemark} " +
            "WHERE uid=#{uid}")
    void updateUser(User user);

    @Delete("DELETE from graduation.user WHERE uid=#{uid}")
    void delete(int uid);

    @Select("<script>"
            + "select * from graduation.user "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " and (userName like '%${keyword}%' "
            + " or userPhone like '%${keyword}%' "
            + " or userHome like '%${keyword}%' "
            + " or userRemark like '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " order by ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by uid desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<User> getUsersBySearch(Search search);

}
