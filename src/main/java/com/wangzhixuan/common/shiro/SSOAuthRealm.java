package com.wangzhixuan.common.shiro;

import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wangzhixuan.service.ResourceService;

public class SSOAuthRealm extends AuthorizingRealm {

	private static Logger LOGGER = LoggerFactory.getLogger(SSOAuthRealm.class);

	@Autowired
	private ResourceService resourceService;


	/**
	 * 根据用户身份获取授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo( PrincipalCollection principals ) {
		LOGGER.info("Shiro开始登录认证");
		if ( principals == null ) {
			throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
		}
		Long userId = (Long) getAvailablePrincipal(principals);
		//根据用ID 获取权限列表
		List<String> permissions = resourceService.getUrlListByUserId(userId);
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		if ( permissions != null ) {
			authorizationInfo.addStringPermissions(permissions);
		}
		return authorizationInfo;
	}

	@Override
	public boolean supports( AuthenticationToken token ) {
		return token != null && SSOAuthToken.class.isAssignableFrom(token.getClass());
	}


	@Override
	public Class<?> getAuthenticationTokenClass() {
		return SSOAuthToken.class;
	}

	/**
	 * 获取身份验证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo( AuthenticationToken token ) throws AuthenticationException {
		return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), getName());
	}

}
