package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.Affairs;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;

public interface AffairsService {
    Result<Affairs> getAffairsById(int affairs_id);
    List<Affairs> getAllAffairs();
    Result<Affairs> insertAffairs(Affairs affairs);
    Result<Affairs> updateAffairs(Affairs affairs);
    Result<Affairs> acceptAffairs(Affairs affairs);
    Result<Affairs> startAffairs(Affairs affairs);
    Result<Affairs> endAffairs(Affairs affairs);
    Result<Object> deleteAffairs(Affairs affairs);
    PageInfo<Affairs> getAffairsBySearchA(Search search);
    PageInfo<Affairs> getAffairsBySearchUid(Search search);
    PageInfo<Affairs> getAffairsBySearchB(Search search);
}
