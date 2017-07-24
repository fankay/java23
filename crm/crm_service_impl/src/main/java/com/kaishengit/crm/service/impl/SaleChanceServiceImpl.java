package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.*;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.mapper.SaleChanceRecordMapper;
import com.kaishengit.crm.service.SaleChanceService;
import com.kaishengit.crm.mapper.SaleChanceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Service
public class SaleChanceServiceImpl implements SaleChanceService {

    @Autowired
    private SaleChanceMapper saleChanceMapper;
    @Autowired
    private SaleChanceRecordMapper saleChanceRecordMapper;
    @Autowired
    private CustomerMapper customerMapper;

    @Value("#{'${sales.progress}'.split(',')}")
    private List<String> progressList;

    /**
     * 加载所有进度列表
     * @return
     */
    @Override
    public List<String> findAllProgressList() {
        return progressList;
    }

    /**
     * 保存新的机会
     * @param saleChance
     */
    @Override
    @Transactional
    public void saveNewChance(SaleChance saleChance) {
        //新增机会的最后跟进时间就是当前时间
        saleChance.setLastTime(new Date());
        saleChance.setCreateTime(new Date());
        saleChanceMapper.insert(saleChance);

        //如果内容不为空则设置为一次跟进记录
        if(StringUtils.isNotEmpty(saleChance.getContent())) {
            SaleChanceRecord record = new SaleChanceRecord();
            record.setContent(saleChance.getContent());
            record.setSaleId(saleChance.getId());
            record.setCreateTime(new Date());
            saleChanceRecordMapper.insert(record);
        }
    }

    /**
     * 查找当前用户的机会（我的销售机会首页使用）
     * @param queryParam
     * @return
     */
    @Override
    public PageInfo<SaleChance> findByParam(Map<String, Object> queryParam) {
        Integer pageNo = (Integer) queryParam.get("pageNo");
        PageHelper.startPage(pageNo,10);
        List<SaleChance> saleChanceList = saleChanceMapper.findByQueryParam(queryParam);
        return new PageInfo<>(saleChanceList);
    }

    /**
     * 根据Id查找销售机会并加载对应的客户对象
     * @param id
     * @return
     */
    @Override
    public SaleChance findSaleChanceById(Integer id) {
        return saleChanceMapper.findById(id);
    }

    /**
     * 获取当前机会的所有跟进记录
     * @param id
     * @return
     */
    @Override
    public List<SaleChanceRecord> findChanceRecordByChanceId(Integer id) {
        SaleChanceRecordExample example = new SaleChanceRecordExample();
        example.createCriteria().andSaleIdEqualTo(id);
        return saleChanceRecordMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 根据ID删除销售机会
     * @param id
     */
    @Override
    @Transactional
    public void delSaleChanceById(Integer id) {
        //删除跟进记录
        SaleChanceRecordExample recordExample = new SaleChanceRecordExample();
        recordExample.createCriteria().andSaleIdEqualTo(id);
        saleChanceRecordMapper.deleteByExample(recordExample);
        //TODO 删除关联文档
        //TODO 删除关联任务
        //删除机会
        saleChanceMapper.deleteByPrimaryKey(id);
    }

    /**
     * 给销售机会添加新的跟进记录
     * @param record
     */
    @Override
    @Transactional
    public void saveNewChanceRecord(SaleChanceRecord record) {
        //添加跟进记录
        record.setCreateTime(new Date());
        saleChanceRecordMapper.insert(record);
        //修改销售机会的最后跟进时间
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(record.getSaleId());
        saleChance.setLastTime(new Date());
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);
        //修改客户的最后跟进时间
        Customer customer = customerMapper.selectByPrimaryKey(saleChance.getCustId());
        customer.setLastContactTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 更新销售机会的进度
     * @param id
     * @param progress
     */
    @Override
    @Transactional
    public void updateSaleChanceProgress(Integer id, String progress) {
        //修改进度
        SaleChance saleChance = saleChanceMapper.selectByPrimaryKey(id);
        saleChance.setProgress(progress);
        saleChanceMapper.updateByPrimaryKeySelective(saleChance);
        //添加跟进记录
        SaleChanceRecord record = new SaleChanceRecord();
        record.setCreateTime(new Date());
        record.setSaleId(id);
        record.setContent("将当前进度修改为：[ " + progress + " ]");
        saveNewChanceRecord(record);
    }

    /**
     * 根据客户ID查找销售机会列表
     * @param id
     * @return
     */
    @Override
    public List<SaleChance> findSaleChanceByCustId(Integer id) {
        SaleChanceExample example = new SaleChanceExample();
        example.createCriteria().andCustIdEqualTo(id);
        return saleChanceMapper.selectByExample(example);
    }
}
