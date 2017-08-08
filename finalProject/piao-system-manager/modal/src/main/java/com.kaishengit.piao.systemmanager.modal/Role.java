package com.kaishengit.piao.systemmanager.modal;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Role implements Serializable {
    private String id;

    /**
     * 权限名称
     */
    private String roleName;

    private Date createTime;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}