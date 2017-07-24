package com.kaishengit.crm.mapper;

import com.kaishengit.crm.entity.SaleChance;
import com.kaishengit.crm.entity.SaleChanceExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;

public interface SaleChanceMapper {
    long countByExample(SaleChanceExample example);

    int deleteByExample(SaleChanceExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SaleChance record);

    int insertSelective(SaleChance record);

    List<SaleChance> selectByExampleWithBLOBs(SaleChanceExample example);

    List<SaleChance> selectByExample(SaleChanceExample example);

    SaleChance selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") SaleChance record, @Param("example") SaleChanceExample example);

    int updateByExampleWithBLOBs(@Param("record") SaleChance record, @Param("example") SaleChanceExample example);

    int updateByExample(@Param("record") SaleChance record, @Param("example") SaleChanceExample example);

    int updateByPrimaryKeySelective(SaleChance record);

    int updateByPrimaryKeyWithBLOBs(SaleChance record);

    int updateByPrimaryKey(SaleChance record);

    List<SaleChance> findByQueryParam(Map<String, Object> queryParam);

    SaleChance findById(Integer id);
}