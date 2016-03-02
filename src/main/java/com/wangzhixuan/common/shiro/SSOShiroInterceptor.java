package com.wangzhixuan.common.shiro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;

/**
 * kisso_shiro 权限拦截器
 */
public class SSOShiroInterceptor implements HandlerInterceptor {

	protected static Logger logger = LoggerFactory.getLogger(SSOShiroInterceptor.class);

	private SecurityManager manager;


	@Override
	public boolean preHandle( HttpServletRequest request, HttpServletResponse response, Object handler )
		throws Exception {
		if ( handler instanceof HandlerMethod ) {
			SSOToken token = SSOHelper.attrToken(request);
			if ( token == null ) {
				return true;
			}

			Subject subject = new Subject.Builder().buildSubject();
			logger.debug("是否验证登录:{}", subject.isAuthenticated());
			Session session = subject.getSession(false);
			if ( session != null ) {
				session.touch();
			}
			if ( !subject.isAuthenticated() ) {
				subject.login(new SSOAuthToken(Long.valueOf(token.getUid()), token));
				logger.debug("登录成功");
			}

			// 记住登录信息
			// WebSubject subject = new WebSubject.Builder(manager, request,
			// response).buildWebSubject();
			// 每次需要获取用户id

			//TODO 待完善....
			String url = request.getRequestURI();
			if ( url == null || subject.isPermitted(url) ) {
				return true;
			}

			logger.info("验证....");
			response.sendError(403, "Forbidden");
			return false;
		}
		return true;
	}


	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView ) throws Exception {
		//to do noting
	}


	@Override
	public void afterCompletion( HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex ) throws Exception {
		//to do noting
	}


	public SecurityManager getManager() {
		return manager;
	}


	public void setManager( SecurityManager manager ) {
		SecurityUtils.setSecurityManager(manager);
		this.manager = manager;
	}

}
