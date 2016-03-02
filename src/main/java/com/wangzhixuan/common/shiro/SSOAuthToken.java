package com.wangzhixuan.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

import com.baomidou.kisso.Token;

public class SSOAuthToken implements AuthenticationToken {

	private static final long serialVersionUID = 1L;

	/**
	 * userId
	 */
	private final Long uid;

	/**
	 * Kisso_Token
	 */
	private final Token token;


	public SSOAuthToken( Long uid, Token token ) {
		this.uid = uid;
		this.token = token;
	}


	@Override
	public Object getPrincipal() {
		return uid;
	}


	@Override
	public Object getCredentials() {
		return token;
	}

}
