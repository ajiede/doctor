package com.wy.doctor.service;

import com.github.pagehelper.PageInfo;
import com.wy.doctor.entity.URHistory;
import com.wy.doctor.vo.Result;
import com.wy.doctor.vo.Search;

import java.util.List;

public interface URHistoryService {
    Result<URHistory> getOneURHistory(int affairs_id);
    URHistory getURHistoryById(int affairs_id);
    void updateURHistoryById(int affairs_id, int pay_state);
    List<URHistory> getAllURHistory(URHistory urHistory);
    Result<URHistory> insertURHistory(URHistory urHistory);
    Result<URHistory> updateURHistory(URHistory urHistory);
    List<URHistory> getPartURHistory(URHistory urHistory);
    PageInfo<URHistory> getURHistoryBySearch(Search search);
}
