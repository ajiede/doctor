package com.wy.doctor.dao;

import com.wy.doctor.entity.URHistory;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface URHistoryDao {
    // 查询 一条 记录
    @Select("SELECT * FROM graduation.URHistory WHERE affairs_id=#{affairs_id}")
    URHistory getOneHistory(int affairs_id);
    // 单方面 查询 所有 记录
    @Select({"<script>",
            "SELECT * FROM graduation.urhistory",
            "WHERE",
            "<choose>",
            "<when test='type == \"uid\"'>",
            "uid = #{id}",
            "</when>",
            "<when test='type == \"rid\"'>",
            "rid = #{id}",
            "</when>",
            "<when test='type == \"hid\"'>",
            "hid = #{id}",
            "</when>",
            "</choose>",
            "</script>"})
    List<URHistory> getAllHistory(@Param("type") String type, @Param("id") int id);

    // 添加历史记录
    @Insert("INSERT INTO graduation.urhistory" +
            "(affairs_id, uid, rid, hid, user_amount, runner_amount, basisUrl, u_score, pay_state) " +
            "VALUES " +
            "(#{affairs_id}, #{uid}, #{rid}, #{hid}, #{user_amount}, " +
            "#{runner_amount}, #{basisUrl}, #{u_score}, #{pay_state})")
    void insertHistory(URHistory urHistory);
    // 更新历史记录
    @Update("UPDATE graduation.urhistory " +
            "SET user_amount = #{user_amount}, " +
            "runner_amount = #{runner_amount}, " +
            "basisUrl = #{basisUrl}, " +
            "u_score = #{u_score} " +
            "WHERE affairs_id = #{affairs_id}")
    void updateHistory(URHistory urHistory);

    @Update("UPDATE graduation.urhistory SET pay_state=#{pay_state} WHERE affairs_id=#{affairs_id}")
    void updateState(int affairs_id, int pay_state);

    // 查询两个对象之间的记录
    @Select({"<script>",
            "SELECT * FROM graduation.urHistory",
            "WHERE",
            "<choose>",
            "<when test='f_type == \"uid\" and s_type == \"rid\"'>",
            "uid = #{fid} AND rid = #{sid}",
            "</when>",
            "<when test='f_type == \"uid\" and s_type == \"hid\"'>",
            "uid = #{fid} AND hid = #{sid}",
            "</when>",
            "<when test='f_type == \"rid\" and s_type == \"hid\"'>",
            "rid = #{fid} AND hid = #{sid}",
            "</when>",
            "</choose>",
            "</script>"})
    List<URHistory> getPartHistoryWithTwo(@Param("f_type") String f_type,
                                   @Param("fid") Integer fid,
                                   @Param("s_type") String s_type,
                                   @Param("sid") Integer sid);

    // 同时传入三个对象的 全查
    @Select("SELECT * FROM graduation.urHistory WHERE uid=#{uid} AND rid=#{rid} AND hid=#{hid}")
    List<URHistory> getPartHistory(int uid, int rid, int hid);

    // 笼统全查，含模糊查询
    @Select("<script>"
            + "SELECT ur.* FROM graduation.urHistory ur "
            + "LEFT JOIN user ON ur.uid = user.uid "
            + "LEFT JOIN runner ON ur.rid = runner.rid "
            + "LEFT JOIN hospital ON ur.hid = hospital.hid "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " AND (user.userName LIKE CONCAT('%', #{keyword}, '%') "
            + " OR runner.runnerName LIKE CONCAT('%', #{keyword}, '%') "
            + " OR hospital.hospitalName LIKE CONCAT('%', #{keyword}, '%') "
            + " OR ur.userRemark LIKE CONCAT('%', #{keyword}, '%')) "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " ORDER BY ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " ORDER BY ur.affairs_id DESC"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<URHistory> getURHistoryBySearch(Search search);
}
