package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class SaleChanceRecord implements Serializable {
    private Integer id;

    /**
     * 销售机会ID
     */
    private Integer saleId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 跟进内容
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(Integer saleId) {
        this.saleId = saleId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}