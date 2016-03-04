package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.model.UserRole;

public interface UserRoleMapper extends AutoMapper<UserRole> {

	List<UserRole> findUserRoleByUserId( Long userId );


	List<Long> findRoleIdListByUserId( Long userId );
}