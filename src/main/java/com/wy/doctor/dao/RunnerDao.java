package com.wy.doctor.dao;

import com.wy.doctor.entity.Runner;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface RunnerDao {

    @Select("SELECT * from graduation.runner")
    List<Runner> findAll();

    @Select("SELECT * from graduation.runner WHERE rid=#{rid}")
    Runner getById(int rid);

    @Select("SELECT * FROM graduation.runner WHERE runnerName=#{runnerName}")
    Runner getByName(String runnerName);

    @Select("SELECT * FROM graduation.runner WHERE runnerPhone=#{phone}")
    Runner getByPhone(String phone);

    @Select("SELECT * FROM graduation.runner WHERE account=#{account}")
    Runner getByAccount(String account);

    @Insert("INSERT INTO graduation.runner(runnerName, runnerAge, runnerGender, runnerPhone, account," +
            "password, runnerAvatarUrl, occupation, runnerRemark, runnerHome, runnerIDCardNumber, " +
            "runnerIDCardPictureUrl, score, balance) " +
            "VALUES (#{runnerName}, #{runnerAge}, #{runnerGender}, #{runnerPhone}, #{account}, #{password}, " +
            "#{runnerAvatarUrl}, #{occupation}, #{runnerRemark}, #{runnerHome}, #{runnerIDCardNumber}, " +
            "#{runnerIDCardPictureUrl}, 5.0, 0.00)")
    void insertRunner(Runner runner);

    @Update("UPDATE graduation.runner " +
            "SET runnerIDCardNumber = #{runnerIDCardNumber}, " +
            "runnerIDCardPictureUrl = #{runnerIDCardPictureUrl} " +
            "WHERE rid = #{rid}")
    void identityVerification(@Param("rid") int rid,
                              @Param("runnerIDCardNumber") String runnerIDCardNumber,
                              @Param("runnerIDCardPictureUrl") String runnerIDCardPictureUrl);

    @Update("UPDATE graduation.runner " +
            "SET runnerName = #{runnerName}, " +
            "runnerAge = #{runnerAge}, " +
            "runnerGender = #{runnerGender}, " +
            "runnerPhone = #{runnerPhone}, " +
            "password = #{password}, " +
            "runnerAvatarUrl = #{runnerAvatarUrl}, " +
            "occupation = #{occupation}, " +
            "runnerRemark = #{runnerRemark}, " +
            "runnerHome = #{runnerHome}, " +
            "runnerIDCardNumber = #{runnerIDCardNumber}, " +
            "runnerIDCardPictureUrl = #{runnerIDCardPictureUrl}, " +
            "score = #{score}, " +
            "balance = #{balance} " +
            "WHERE rid = #{rid}")
    void updateRunner(Runner runner);

    @Delete("DELETE FROM graduation.runner WHERE rid=#{rid}")
    void deleteRunner(int rid);

    @Select("<script>"
            + "select * from graduation.runner "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " and ("
            + "runnerName like '%${keyword}%' "
            + "or runnerPhone like '%${keyword}%' "
            + "or occupation like '%${keyword}%' "
            + "or runnerRemark like '%${keyword}%' "
            + "or runnerHome like '%${keyword}%' "
            + "or runnerIDCardNumber like '%${keyword}%' )"
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " order by ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " order by rid desc"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Runner> getRunnersBySearch(Search search);


}
