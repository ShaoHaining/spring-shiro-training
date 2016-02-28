package com.wangzhixuan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
    @Autowired
    private UserService userService;

	/**
	 * 首页
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(Model model) {
		return "/index";
	}

	/**
	 * GET 登录
	 *
	 * @param model
	 * @param request
	 * @return
	 */
	@Login(action = Action.Skip)
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, HttpServletRequest request) {
		Token token = SSOHelper.getToken(request);
		if (token != null) {
			return "redirect:/index";
		}
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
		LOGGER.info("POST请求登录");
		if (StringUtils.isBlank(username)) {
			return retResult("用户名不能为空", false);
		}
		if (StringUtils.isBlank(password)) {
			return retResult("密码不能为空", false);
		}
		Subject user = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(username, DigestUtils.md5Hex(password).toCharArray());
		token.setRememberMe(true);
		try {
			user.login(token);
		} catch (UnknownAccountException e) {
			LOGGER.error("账号不存在：{}", e);
			return retResult("账号不存在", false);
		} catch (DisabledAccountException e) {
			LOGGER.error("账号未启用：{}", e);
			return retResult("账号未启用", false);
		} catch (IncorrectCredentialsException e) {
			LOGGER.error("密码错误：{}", e);
			return retResult("密码错误", false);
		} catch (RuntimeException e) {
			LOGGER.error("未知错误,请联系管理员：{}", e);
			return retResult("未知错误,请联系管理员", false);
		}
		
		/**
		 * KISSO 登录授权
		 */
		User userInfo = userService.findUserByLoginName(token.getUsername());
		if (userInfo != null) {
			SSOToken st = new SSOToken(request);
			st.setUid(String.valueOf(userInfo.getId()));
			st.setType(String.valueOf(userInfo.getUsertype()));
			st.setData(userInfo.getLoginname());
			//记住密码，设置 cookie 时长 1 周 = 604800 秒 【动态设置 maxAge 实现记住密码功能】
			if ( "on".equals(request.getParameter("rememberMe")) ) {
				request.setAttribute(SSOConfig.SSO_COOKIE_MAXAGE, 604800); 
			}
			SSOHelper.setSSOCookie(request, response, st, false);
		}
		
		return retResult(true);
	}

	/**
	 * 未授权
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/unauth")
	public String unauth(Model model) {
		if (SecurityUtils.getSubject().isAuthenticated() == false) {
			return "redirect:/login";
		}
		return "/unauth";
	}

	/**
	 * 退出
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout")
	@ResponseBody
	public Result logout(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("登出");
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		
		/**
		 * KISSO 退出登录
		 */
		SSOHelper.clearLogin(request, response);
		return retResult(true);
	}
}
