package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Customer implements Serializable {
    private Integer id;

    /**
     * 客户名称
     */
    private String custName;

    /**
     * 职位
     */
    private String jobTitle;

    /**
     * 联系电话
     */
    private String mobile;

    /**
     * 地址
     */
    private String address;

    /**
     * 所属行业
     */
    private String trade;

    /**
     * 来源
     */
    private String source;

    /**
     * 级别
     */
    private String level;

    /**
     * 备注
     */
    private String mark;

    /**
     * 客户所属账号，如果未空则是公海客户
     */
    private Integer accountId;

    /**
     * 最后跟进时间
     */
    private Date lastContactTime;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 最后修改时间
     */
    private Date updateTime;

    /**
     * 提示,例如客户的来源（公海导入，他人转交）
     */
    private String reminder;

    /**
     * 性别
     */
    private String sex;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public Date getLastContactTime() {
        return lastContactTime;
    }

    public void setLastContactTime(Date lastContactTime) {
        this.lastContactTime = lastContactTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}