package com.wangzhixuan;

import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.ConfigGenerator;

/**
 * 
 * 自动生成映射工具类测试
 * 
 * @author hubin
 * {@link AutoGenerator}
 * {@docRoot http://git.oschina.net/juapk/mybatis-plus}
 */
public class GeneratorMapper {

	/**
	 * 
	 * 测试 run 执行
	 * 
	 * <p>
	 * 配置方法查看 {@link ConfigGenerator}
	 * </p>
	 * 
	 */
	public static void main( String[] args ) {
		ConfigGenerator cg = new ConfigGenerator();
		cg.setEntityPackage("com.wangzhixuan.model");
		cg.setMapperPackage("com.wangzhixuan.mapper");
		cg.setSaveDir("C:/sst/");
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		cg.setDbUser("root");
		cg.setDbPassword("");
		cg.setDbUrl("jdbc:mysql://127.0.0.1:3306/shiro?characterEncoding=utf8");
		cg.setDbPrefix(false);
		AutoGenerator.run(cg);
	}
	
}
