package com.kaishengit.crm.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kaishengit.crm.entity.Account;
import com.kaishengit.crm.entity.AccountExample;
import com.kaishengit.crm.entity.Customer;
import com.kaishengit.crm.entity.CustomerExample;
import com.kaishengit.crm.mapper.CustomerMapper;
import com.kaishengit.crm.service.CustomerService;
import com.kaishengit.exception.ServiceException;
import com.kaishengit.weixin.WeiXinUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Value("#{'${customer.trade}'.split(',')}") //SpringEL
    private List<String> tradeList;
    @Value("#{'${customer.source}'.split(',')}")
    private List<String> sourceList;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private WeiXinUtil weiXinUtil;

    /**
     * 获取所有行业数据
     * @return
     */
    @Override
    public List<String> findAllTrade() {
        return tradeList;
    }

    /**
     * 获取所有客户来源数据
     * @return
     */
    @Override
    public List<String> findAllSource() {
        return sourceList;
    }

    /**
     * 新增客户
     * @param customer 客户对象
     * @param account 当前登录系统的用户
     */
    @Override
    @Transactional
    public void saveNewCustomer(Customer customer, Account account) {
        customer.setAccountId(account.getId());
        customer.setCreateTime(new Date());
        customerMapper.insert(customer);

        weiXinUtil.sendTextMessageToUser("新增客户[ "+ customer.getCustName() +" ]","fankay");
    }

    /**
     * 查询当前登录对象的客户
     * @param queryParam 查询条件集合，包括分页当前页码
     * @return
     */
    @Override
    public PageInfo<Customer> findMyCustomer(Map<String,Object> queryParam) {
        Integer pageNum = (Integer) queryParam.get("pageNum");
        PageHelper.startPage(pageNum,10);
        List<Customer> customerList = customerMapper.findByQueryParam(queryParam);
        return new PageInfo<>(customerList);
    }

    /**
     * 根据ID查找客户对象
     * @param id
     * @return
     */
    @Override
    public Customer findById(Integer id) {
        return customerMapper.selectByPrimaryKey(id);
    }

    /**
     * 编辑客户资料
     * @param customer
     */
    @Override
    public void editCustomer(Customer customer) {
        //添加最后修改时间
        customer.setUpdateTime(new Date());
        customerMapper.updateByPrimaryKeySelective(customer);
    }

    /**
     * 删除客户
     * @param customer
     */
    @Override
    public void delCustomer(Customer customer) {
        //TODO 删除跟进记录
        //TODO 删除日程安排
        //TODO 删除相关资料
        //删除客户
        customerMapper.deleteByPrimaryKey(customer.getId());
    }

    /**
     * 将客户放入公海
     * @param customer
     */
    @Override
    public void shareCustomerToPublic(Customer customer,Account account) {
        customer.setAccountId(null);
        customer.setReminder(account.getUserName() + "将客户放入公海");
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 将客户转移给其他账号
     * @param customer
     * @param accountId
     */
    @Override
    public void transferCustomerToAccount(Customer customer, Integer accountId,Account account) {
        customer.setAccountId(accountId);
        customer.setReminder("从"+account.getUserName()+"转移过来");
        customerMapper.updateByPrimaryKey(customer);
    }

    /**
     * 跟进账号ID查找所有的客户
     * @param accountId
     * @return
     */
    @Override
    public List<Customer> findByAccountId(Integer accountId) {
        CustomerExample customerExample = new CustomerExample();
        customerExample.createCriteria().andAccountIdEqualTo(accountId);
        return customerMapper.selectByExample(customerExample);
    }

    /**
     * 将客户导出为excel
     * @param account
     * @param outputStream
     */
    @Override
    public void exportAccountCustomerToExcel(Account account, OutputStream outputStream) {
        List<Customer> customerList = findByAccountId(account.getId());

        //创建工作表
        Workbook workbook = new HSSFWorkbook();
        //创建sheet页签
        Sheet sheet = workbook.createSheet("客户资料");
        //创建数据
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("客户名称");
        row.createCell(1).setCellValue("职位");
        row.createCell(2).setCellValue("级别");
        row.createCell(3).setCellValue("联系电话");

        for (int i = 0; i < customerList.size(); i++) {
            Customer customer = customerList.get(i);
            Row dataRow = sheet.createRow(i+1);
            dataRow.createCell(0).setCellValue(customer.getCustName());
            dataRow.createCell(1).setCellValue(customer.getJobTitle());
            dataRow.createCell(2).setCellValue(customer.getLevel());
            dataRow.createCell(3).setCellValue(customer.getMobile());
        }


        //将工作表写到磁盘中
        try {
            workbook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (Exception ex) {
            throw new ServiceException("导出excel异常",ex);
        }
    }

    @Override
    public List<Map<String,Object>> findCustomerLevelCount() {
        return customerMapper.findCostomerLevelCount();
    }
}
