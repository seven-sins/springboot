package com.springboot.config.conf;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.springboot.vo.OauthUser;

/**
 * 控制器形参上如果有OauthUser，则自动将当前登录用户信息注入
 * @author seven sins
 * @date 2017年5月23日 下午6:54:20
 */
public class UserArgumentResolver implements HandlerMethodArgumentResolver{

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(OauthUser.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		OauthUser oauthUser = (OauthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    return oauthUser;
	}

}
