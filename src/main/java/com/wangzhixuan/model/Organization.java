package com.wangzhixuan.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * 组织机构表
 *
 */
@TableName(value = "organization")
public class Organization implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId
	private Long id;

	/** 组织名称 */
	private String name;

	/** 地址 */
	private String address;

	/** 编号 */
	private String code;

	/** 图标 */
	private String icon;

	/** 父ID */
	private Long pid;

	/** 排序 */
	private Integer seq;

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

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public Date getCrTime() {
		return this.crTime;
	}

	public void setCrTime(Date crTime) {
		this.crTime = crTime;
	}

}
