package com.wangzhixuan.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 * @description：角色资源关联
 * @author：zhixuan.wang
 * @date：2015/10/1 14:51
 */
@TableName(value = "role_resource")
public class RoleResource implements Serializable {

	@TableField(exist = false)
    private static final long serialVersionUID = -7250242744961556986L;

	@TableId
    private Long id;

    @TableField(value = "role_id")
    private Long roleId;

    @TableField(value = "resource_id")
    private Long resourceId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public String toString() {
        return "RoleResource{" +
                "id=" + id +
                ", roleId=" + roleId +
                ", resourceId=" + resourceId +
                '}';
    }
}