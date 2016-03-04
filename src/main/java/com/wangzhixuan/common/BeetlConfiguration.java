package com.wangzhixuan.common;

import org.beetl.ext.spring.BeetlGroupUtilConfiguration;


public class BeetlConfiguration extends BeetlGroupUtilConfiguration {

	@Override
	public void init() {
		super.init();
		//注入shiro
		groupTemplate.registerFunctionPackage("shiro", new BeetlShiro());
	}
}
