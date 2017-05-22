package com.springboot.config.annotation;

import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.springboot.config.exception.SevenException;
import com.springboot.po.User;
import com.springboot.service.assist.RedisService;

/**
 * @author seven sins
 * @date 2017年5月8日 下午10:57:29
 */
public final class SessionMethodArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		// 让方法和参数，两种target通过
		if (parameter.hasParameterAnnotation(Session.class))
			return true;
		else if (parameter.getMethodAnnotation(Session.class) != null)
			return true;
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		String annoVal = null;

		if (parameter.getParameterAnnotation(Session.class) != null) {
			annoVal = parameter.getParameterAnnotation(Session.class).value();
		} else if (parameter.getMethodAnnotation(Session.class) != null) {
			annoVal = parameter.getMethodAnnotation(Session.class) != null
					? StringUtils.defaultString(parameter.getMethodAnnotation(Session.class).value())
					: StringUtils.EMPTY;
		}
		/**
		 * 如value == user， 从redis缓存中获取
		 */
		if ("user".equals(annoVal)) {
			HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
			ServletContext servletContext = request.getServletContext();
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(servletContext);
			RedisService redisService = (RedisService) ctx.getBean("redisServiceImpl");

			String token = request.getHeader("token");
			if (token == null) {
				token = (String) webRequest.getAttribute("token", RequestAttributes.SCOPE_SESSION);
				if (token == null) {
					Cookie[] cookies = request.getCookies();
					if (null != cookies) {
						for (Cookie cookie : cookies) {
							if ("token".equals(cookie.getName())) {
								token = cookie.getValue();
							}
						}
					}
				}
			}
			Object object = redisService.get("user-" + token);
			if (object == null) { // 缓存中没有用户信息
				return null;
			}
			User user = new User();
			try {
				user = (User) object;
			} catch (RuntimeException e) {
				throw new SevenException(401, "解析缓存用户信息出错");
			}

			return user;
		}

		if (webRequest.getAttribute(annoVal, RequestAttributes.SCOPE_SESSION) != null) {
			return webRequest.getAttribute(annoVal, RequestAttributes.SCOPE_SESSION);
		} else {
			return null;
		}
	}
}
