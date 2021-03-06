package com.springboot.config.security.enhancer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.springboot.service.assist.RedisService;
import com.springboot.vo.OauthUser;

/**
 * @author Tan Ling
 * @date 2017年5月5日 下午4:39:08
 */
public class CustomTokenEnhancer implements TokenEnhancer {

	@Autowired
	RedisService redisService;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		OauthUser oauthUser = (OauthUser) authentication.getUserAuthentication().getPrincipal();
		String key = accessToken.getValue(); // token
		Object object = redisService.get(key);
		if (object != null) {
			redisService.delete(key);
		}
		redisService.add(key, oauthUser);

		return accessToken;
	}
}
