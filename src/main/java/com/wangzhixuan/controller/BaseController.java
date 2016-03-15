package com.wangzhixuan.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.baomidou.framework.controller.SuperController;
import com.wangzhixuan.common.Result;
import com.wangzhixuan.common.utils.StringEscapeEditor;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.UserService;

/**
 * @description：基础 controller
 * @author：zhixuan.wang @date：2015/10/1 14:51
 */
public class BaseController extends SuperController {

	@Autowired
	private UserService userService;


	@InitBinder
	public void initBinder( ServletRequestDataBinder binder ) {
		/**
		 * 自动转换日期类型的字段格式
		 */
		binder.registerCustomEditor(Date.class,
			new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));

		/**
		 * 防止XSS攻击
		 */
		binder.registerCustomEditor(String.class, new StringEscapeEditor(true, false));
	}


	protected Result retResult( boolean success ) {
		return retResult(null, null, success);
	}


	protected Result retResult( String msg, boolean success ) {
		return retResult(msg, null, success);
	}


	protected Result retResult( Object obj) {
		return retResult(null, obj, true);
	}


	protected Result retResult( String msg, Object obj, boolean success ) {
		Result rlt = new Result();
		if ( msg != null ) {
			rlt.setMsg(msg);
		}
		if ( obj != null ) {
			rlt.setObj(obj);
		}
		rlt.setSuccess(success);
		return rlt;
	}


	/**
	 * 获取当前登录用户对象
	 */
	public User getCurrentUser() {
		return userService.findUserById(getCurrentUserId());
	}

}
