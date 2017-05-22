package com.springboot.config.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.springboot.config.exception.SevenException;
import com.springboot.po.Privilege;
import com.springboot.po.User;
import com.springboot.service.assist.RedisService;
import com.springboot.service.sys.PrivilegeService;

/**
 * 停用此方式， 改用Oauth2
 * @author seven sins
 * @date 2017年5月8日 下午10:58:30
 */
@Deprecated
public class HandlerInterceptor extends HandlerInterceptorAdapter{

	/**
	 * 请求发生前执行
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler){
		long startTime = System.currentTimeMillis();
		req.setAttribute("startTime", startTime);
		
		/**
		 * 1. 获取当前访问url与method
		 */
		String url = req.getRequestURI();
		int index = url.indexOf("?");
		if(index != -1){
			url = url.substring(0, index);
		}
		String method = req.getMethod(); // eg: GET
		method = method.toLowerCase();
		/**
		 * 2. 获取redisService对象
		 */
		ServletContext servletContext = req.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);  
		RedisService redisService = (RedisService) ctx.getBean("redisServiceImpl");
		/**
		 * 3. 获取privilegeService对象
		 */
		PrivilegeService privilegeService = (PrivilegeService) ctx.getBean("privilegeServiceImpl");
		/**
		 * 4. 获取所有权限列表
		 */
		Privilege privilege = new Privilege();
		privilege.setStatus(0);
		List<Privilege> all = privilegeService.getAll(privilege);
		/**
		 * 5. 检查当前url是否需要权限管理, 不需要权限管理不拦截
		 */
		if(!this.isValid(url, method, all)){ // 不需要权限管理，不拦截
			return true;
		}
		/**
		 * 6. 从请求头获取token
		 */
		String token = req.getHeader("token");
		/**
		 * 7. 判断token, token为空时， 如url为空(即访问首页)、doLogin、login三种情况时， 不拦截
		 */
		if(token == null || "".equals(token) || "empty".equals(token)){
			if ("/".equals(url) || "/main".equals(url) || url.equals("/doLogin") || url.equals("/login")) {
				return true;
			}
			throw new SevenException(6, "未登录");
		}
		/**
		 * 8. 有token, 检查是否超时
		 */
		Object object = redisService.get("user-" + token);
		if(object == null){ // 缓存中没有用户信息
			throw new SevenException(6, "登录超时");
		}
		/**
		 * 9. 检查访问权限
		 */
		User user = new User();
		try {
			user = (User) object;
		} catch (RuntimeException e) {
			throw new SevenException(1, "解析缓存用户信息出错");
		}
		// 从缓存中获取权限
		Object rolePrivilege = redisService.get("privilege-roleId-" + user.getRoleId());
		/**
		 * 缓存中没有权限信息
		 * 原因：
		 * 		a. 超时; 
		 * 		b. 管理员重新设置过当前用户角色对应的权限
		 */
		if(rolePrivilege == null){
			throw new SevenException(6, "登录超时");
		}
		List<Privilege> privileges = new ArrayList<Privilege>();
		try{
			privileges = (List<Privilege>) rolePrivilege;
		}catch(RuntimeException e){
			throw new SevenException(1, "解析缓存用户权限信息出错");
		}
		// 验证权限
		if(!this.isValid(url, method, privileges)){
			throw new SevenException(1, "无权限访问");
		}
		
		return true;
	}
	
	/**
	 * 请求完成后执行
	 */
	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res, Object handler, ModelAndView modelAndView){
		long startTime = (long) req.getAttribute("startTime");
		req.removeAttribute("startTime");
		long endTime = System.currentTimeMillis();
		System.out.println("本次请求处理时间: " + new Long(endTime - startTime) + "ms");
	}
	
	private boolean isValid(String url, String method, List<Privilege> privileges){
		for(int i=0; i<privileges.size(); i++){
			Privilege privilege = privileges.get(i);
			String dbUrl = privilege.getUrl();
			dbUrl = dbUrl.replaceAll("\\{(\\w*|\\s*)\\}", "\\\\w*");
			
			if(url.matches(dbUrl) && method.equals(privilege.getMethod())){
				return true;
			}
		}
		return false;
	}
}
