package com.wangzhixuan.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * 资源表
 *
 */
@TableName(value = "resource")
public class Resource implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 名称 */
	private String name;

	/** 地址 */
	private String url;

	/** 描述 */
	private String description;

	/** 图标 */
	private String icon;

	/** 父ID */
	private Long pid;

	/** 排序 */
	private Integer seq;

	/** 状态 */
	private Integer status;

	/** 类型 */
	private Integer resourcetype;

	/** 创建时间 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date crTime;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getResourcetype() {
		return this.resourcetype;
	}

	public void setResourcetype(Integer resourcetype) {
		this.resourcetype = resourcetype;
	}

	public Date getCrTime() {
		return this.crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

}
