package com.wy.doctor.dao;

import com.wy.doctor.entity.Affairs;
import com.wy.doctor.vo.Search;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@Mapper
public interface AffairsDao {
    // 查看事务详情
    @Select("SELECT affairs.*, u.userName, h.hospitalName " +
            "FROM graduation.affairs AS affairs " +
            "LEFT JOIN user u ON affairs.uid = u.uid " +
            "LEFT JOIN hospital h ON affairs.hid = h.hid " +
            "WHERE affairs.affairs_id = #{affairs_id}")
    Affairs getOneAffairs(int affairs_id);

    // 获取所有的事务
    @Select("SELECT * FROM graduation.affairs")
    List<Affairs> getAllAffairs();

    @Select("select * from graduation.affairs WHERE uid=#{uid} AND createTime=#{createTime}")
    Affairs getAffairsByUid(int uid, int hid, Date createTime);
    // 发布事务
    @Insert("INSERT INTO affairs (uid, hid, type_medical, doctor_id, user_amount, " +
            "userAddress, hospitalAddress, createTime, startTime, endTime, state, remark ) " +
            "VALUES (#{uid}, #{hid}, #{type_medical}, #{doctor_id}, #{user_amount}, " +
            "#{userAddress}, #{hospitalAddress}, #{createTime}, #{startTime}, #{endTime}, #{state}, #{remark})")
    void insertAffairs(Affairs affairs);
    // 修改事务内容
    @Update("UPDATE affairs " +
            "SET hid = #{hid}, type_medical = #{type_medical}, " +
            "doctor_id = #{doctor_id}, user_amount = #{user_amount}, " +
            "userAddress = #{userAddress}, createTime = #{createTime}, " +
            "hospitalAddress = #{hospitalAddress}, " +
            "startTime = #{startTime}, state = 1, " +
            "remark = #{remark} " +
            "WHERE affairs_id = #{affairs_id}")
    void updateAffairs(Affairs affairs);
    // 接收事务
    @Update("UPDATE graduation.affairs SET state=#{state} WHERE affairs_id=#{affairs_id}")
    void acceptAffairs(int affairs_id, int state);
    // 开始事务
    @Update("UPDATE affairs " +
            "SET startTime=#{startTime}, state=#{state} " +
            "WHERE affairs_id=#{affairs_id}")
    void startAffairs(int affairs_id, Date startTime, int state);
    // 结束事务
    @Update("UPDATE affairs " +
            "SET endTime=#{endTime}, state=#{state} " +
            "WHERE affairs_id=#{affairs_id}")
    void finishAffairs(int affairs_id, Date endTime, int state);
    // 取消事务
    @Delete("DELETE FROM graduation.affairs WHERE affairs_id=#{affairs_id}")
    void deleteAffairs(int affairs_id);
    // 获取所有的事务
    @Select("<script>"
            + "SELECT a.* FROM affairs a "
            + "LEFT JOIN hospital h ON a.hid = h.hid "
            + "<where> "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " AND (h.hospitalName LIKE '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " ORDER BY ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " ORDER BY a.affairs_id DESC"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Affairs> getAffairsBySearchA(Search search);

    @Select("<script>"
            + "SELECT a.*, u.userName, h.hospitalName, d.doctorName "
            + "FROM affairs a "
            + "LEFT JOIN user u ON a.uid = u.uid "
            + "LEFT JOIN hospital h ON a.hid = h.hid "
            + "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id "
            + "<where> "
            + "AND a.uid = #{keyword} " // 修改为uid=keyword条件
            + "AND a.state != 4 "       // 添加状态不为4的条件
            + "</where>"
            + " ORDER BY a.startTime ASC" // 默认排序方式
            + "</script>")
    List<Affairs> getAffairsBySearchUid(Search search);

    @Select("<script>"
            + "SELECT a.*, u.userName, h.hospitalName, d.doctorName "
            + "FROM affairs a "
            + "LEFT JOIN user u ON a.uid = u.uid "
            + "LEFT JOIN hospital h ON a.hid = h.hid "
            + "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id "
            + "<where> "
            + "AND a.hid = #{keyword} "
            + "AND a.state != 4 "
            + "</where>"
            + " ORDER BY a.startTime ASC" // 默认排序方式
            + "</script>")
    List<Affairs> getAffairsBySearchHid(Search search);

    @Select("<script>"
            + "SELECT a.*, u.userName,u.userAvatarUrl, h.hospitalName, d.doctorName "
            + "FROM affairs a "
            + "LEFT JOIN user u ON a.uid = u.uid "
            + "LEFT JOIN hospital h ON a.hid = h.hid "
            + "LEFT JOIN doctor d ON a.doctor_id = d.doctor_id "
            + "<where> "
            + "AND state != 4 "
            + "<if test='keyword != \"\" and keyword != null'>"
            + " AND (remark LIKE '%${keyword}%') "
            + "</if>"
            + "</where>"
            + "<choose>"
            + "<when test='sort != \"\" and sort != null'>"
            + " ORDER BY ${sort} ${direction}"
            + "</when>"
            + "<otherwise>"
            + " ORDER BY a.affairs_id DESC"
            + "</otherwise>"
            + "</choose>"
            + "</script>")
    List<Affairs> getAffairsBySearchC(Search search);


}
