package com.wangzhixuan.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.AutoMapper;
import com.wangzhixuan.model.UserRole;

public interface UserRoleMapper extends AutoMapper<UserRole> {

    int insert(UserRole userRole);

    int updateByPrimaryKeySelective(UserRole userRole);

    List<UserRole> findUserRoleByUserId(Long userId);

    int deleteById(Long id);

    List<Long> findRoleIdListByUserId(Long userId);
}