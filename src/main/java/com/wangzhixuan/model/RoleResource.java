package com.wangzhixuan.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 角色资源表
 *
 */
@TableName(value = "role_resource")
public class RoleResource implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 角色ID */
	private Long roleId;

	/** 资源ID */
	private Long resourceId;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

}
