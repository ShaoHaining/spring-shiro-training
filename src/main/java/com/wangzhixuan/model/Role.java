package com.wangzhixuan.model;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotations.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

/**
 *
 * 角色表
 *
 */
@TableName(value = "role")
public class Role implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主键ID */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 角色名称 */
	private String name;

	/** 排序 */
	private Integer seq;

	/** 状态 */
	private Integer status;

	/** 描述 */
	private String description;

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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
