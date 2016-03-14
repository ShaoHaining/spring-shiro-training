package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.model.RoleResource;

public interface RoleResourceMapper extends AutoMapper<RoleResource> {

	/**
	 * 根据角色id查询角色资源关联列表
	 *
	 * @param id
	 * @return
	 */
	List<RoleResource> findRoleResourceIdListByRoleId( Long id );

}