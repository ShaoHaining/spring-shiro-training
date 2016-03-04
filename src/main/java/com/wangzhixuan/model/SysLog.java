package com.wangzhixuan.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * 日志表
 *
 */
@TableName(value = "sys_log")
public class SysLog implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId
	private Long id;

	/** 登录名称 */
	private String loginName;

	/** 角色名称 */
	private String roleName;

	/** 内容 */
	private String content;

	/** IP地址 */
	private String ip;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crTime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getCrTime() {
		return this.crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

}
