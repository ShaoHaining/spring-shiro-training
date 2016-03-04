package com.wangzhixuan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.kisso.SSOConfig;
import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;
import com.baomidou.kisso.Token;
import com.baomidou.kisso.annotation.Action;
import com.baomidou.kisso.annotation.Login;
import com.baomidou.kisso.annotation.Permission;
import com.baomidou.kisso.common.encrypt.MD5;
import com.baomidou.kisso.common.util.HttpUtil;
import com.wangzhixuan.common.Result;
import com.wangzhixuan.model.User;
import com.wangzhixuan.service.UserService;

/**
 * @description：登录退出
 * @Author：zhixuan.wang hubin
 * @Date 2016-02-27
 */
@Controller
public class LoginController extends BaseController {

    @Autowired
    private UserService userService;

	/**
	 * 首页
	 */
    @Permission(action = Action.Skip)
	@RequestMapping(value = "/index")
	public String index(Model model) {
    	model.addAttribute("userName", getSSOToken().getData());
		return "/index";
	}
	
	/**
	 * 测试登录成功是否会重定向该页面
	 */
    @Permission(action = Action.Skip)
	@RequestMapping(value = "/demo")
	public String demo(Model model) {
		return "/demo";
	}

	/**
	 * GET 登录
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		Token token = SSOHelper.getToken(request);
		if (token != null) {
			return "redirect:/index";
		}
		model.addAttribute("ReturnURL", request.getParameter("ReturnURL"));
		return "/login";
	}

	/**
	 * POST 登录 shiro 写法
	 *
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 * @param request
	 * @param model
	 * @return
	 */
	@Login(action = Action.Skip)
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result loginPost(String username, String password, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		if ( StringUtils.isBlank(username) ) {
			return retResult("用户名不能为空", false);
		}
		if ( StringUtils.isBlank(password) ) {
			return retResult("密码不能为空", false);
		}

		/**
		 * KISSO 登录授权
		 * 此处为验证登录身份
		 */
		User userInfo = userService.findUserByLoginName(username);
		if ( userInfo != null && userInfo.getPassword().equals(MD5.toMD5(password)) ) {
			SSOToken st = new SSOToken(request);
			st.setId(userInfo.getId());
			st.setType(userInfo.getType());
			st.setData(userInfo.getLoginName());
			//记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
			if ( "on".equals(request.getParameter("rememberMe")) ) {
				request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 604800); 
			}
			SSOHelper.setSSOCookie(request, response, st, false);
			
			//处理 ReturnURL 地址
			String returnURL = request.getParameter("ReturnURL");
			if ( StringUtils.isNoneBlank(returnURL) ) {
				returnURL = HttpUtil.decodeURL(returnURL);
			} else {
				returnURL = "/index";
			}
			return retResult(returnURL);
		}
		
		return retResult("用户名密码错误", false);
	}

	/**
	 * 退出
	 *
	 * @param request
	 * @return
	 */
	@Login(action = Action.Skip)
	@ResponseBody
	@RequestMapping(value = "/logout")
	public Result logout(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * KISSO 退出登录
		 */
		SSOHelper.clearLogin(request, response);
		return retResult(true);
	}
}
