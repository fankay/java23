package com.kaishengit.crm.service;

import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceRecord;

import java.util.List;
import java.util.Map;

public interface SaleChanceService {

    List<String> findAllProgressList();

    void saveNewChance(SaleChance saleChance);

    PageInfo<SaleChance> findByParam(Map<String, Object> queryParam);

    SaleChance findSaleChanceById(Integer id);

    List<SaleChanceRecord> findChanceRecordByChanceId(Integer id);

    void delSaleChanceById(Integer id);

    void saveNewChanceRecord(SaleChanceRecord record);

    void updateSaleChanceProgress(Integer id, String progress);

    List<SaleChance> findSaleChanceByCustId(Integer id);
}
