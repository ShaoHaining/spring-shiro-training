package com.wangzhixuan.service.impl;

import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wangzhixuan.common.utils.PageInfo;
import com.wangzhixuan.mapper.UserMapper;
import com.wangzhixuan.mapper.UserRoleMapper;
import com.wangzhixuan.model.User;
import com.wangzhixuan.model.UserRole;
import com.wangzhixuan.model.vo.UserVo;
import com.wangzhixuan.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserRoleMapper userRoleMapper;


	@Override
	public User findUserByLoginName( String username ) {
		User user = new User();
		user.setLoginName(username);
		return userMapper.selectOne(user);
	}


	@Override
	public User findUserById( Long id ) {
		return userMapper.selectById(id);
	}


	@Override
	public void findDataGrid( PageInfo pageInfo ) {
		pageInfo.setRows(userMapper.findUserPageCondition(pageInfo));
		pageInfo.setTotal(userMapper.findUserPageCount(pageInfo));
	}


	@Override
	public void addUser( UserVo userVo ) {
		User user = new User();
		try {
			PropertyUtils.copyProperties(user, userVo);
		} catch ( Exception e ) {
			LOGGER.error("类转换异常：{}", e);
			throw new RuntimeException("类型转换异常：{}", e);
		}
		userMapper.insert(user);

		Long id = user.getId();
		String[] roles = userVo.getRoleIds().split(",");
		UserRole userRole = new UserRole();

		for ( String string : roles ) {
			userRole.setUserId(id);
			userRole.setRoleId(Long.valueOf(string));
			userRoleMapper.insert(userRole);
		}
	}


	@Override
	public void updateUserPwdById( Long userId, String pwd ) {
		User user = new User();
		user.setId(userId);
		user.setPassword(pwd);
		userMapper.updateById(user);
	}


	@Override
	public UserVo findUserVoById( Long id ) {
		return userMapper.findUserVoById(id);
	}


	@Override
	public void updateUser( UserVo userVo ) {
		User user = new User();
		try {
			PropertyUtils.copyProperties(user, userVo);
		} catch ( Exception e ) {
			LOGGER.error("类转换异常：{}", e);
			throw new RuntimeException("类型转换异常：{}", e);
		}
		userMapper.updateById(user);
		Long id = userVo.getId();
		List<UserRole> userRoles = findUserRoleByUserId(id);
		if ( userRoles != null && (!userRoles.isEmpty()) ) {
			for ( UserRole userRole : userRoles ) {
				userRoleMapper.deleteById(userRole.getId());
			}
		}

		String[] roles = userVo.getRoleIds().split(",");
		UserRole userRole = new UserRole();
		for ( String string : roles ) {
			userRole.setUserId(id);
			userRole.setRoleId(Long.valueOf(string));
			userRoleMapper.insert(userRole);
		}

	}


	@Override
	public void deleteUserById( Long id ) {
		userMapper.deleteById(id);
		List<UserRole> userRoles = findUserRoleByUserId(id);
		if ( userRoles != null && (!userRoles.isEmpty()) ) {
			for ( UserRole userRole : userRoles ) {
				userRoleMapper.deleteById(userRole.getId());
			}
		}
	}


	public List<UserRole> findUserRoleByUserId( Long userId ) {
		UserRole ur = new UserRole();
		ur.setUserId(userId);
		return userRoleMapper.selectList(RowBounds.DEFAULT, ur);
	}

}
