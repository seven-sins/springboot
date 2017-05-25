package com.springboot.config.security;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenApprovalStore;
import org.springframework.security.oauth2.provider.approval.TokenStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import com.springboot.config.exception.SevenException;
import com.springboot.config.security.enhancer.CustomTokenEnhancer;
import com.springboot.config.security.exception.AuthException;
import com.springboot.config.security.provider.CustomAuthencticationProvider;

/**
 * @author Tan Ling
 * @date 2017年5月5日 下午4:39:26
 */
@Configuration
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	DataSource dataSource;
	@Autowired
	ClientDetailsService clientDetailsService;
	@Autowired
	RedisConnectionFactory redisConnection;

	@Bean
	public TokenStore tokenStore() {
		return new RedisTokenStore(redisConnection);
	}

	@Bean
	@Autowired
	public TokenStoreUserApprovalHandler userApprovalHandler(TokenStore tokenStore) {
		TokenStoreUserApprovalHandler handler = new TokenStoreUserApprovalHandler();
		handler.setTokenStore(tokenStore);
		handler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
		handler.setClientDetailsService(clientDetailsService);
		return handler;
	}

	@Bean
	public CustomAuthencticationProvider customAuthencticationProvider() {
		CustomAuthencticationProvider customAuthProvider = new CustomAuthencticationProvider();
		return customAuthProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		List<AuthenticationProvider> providers = new ArrayList<AuthenticationProvider>();
		providers.add(customAuthencticationProvider());
		ProviderManager providerManager = new ProviderManager(providers);
		return providerManager;
	}

	@Bean
	@Autowired
	public ApprovalStore approvalStore(TokenStore tokenStore) throws Exception {
		TokenApprovalStore store = new TokenApprovalStore();
		store.setTokenStore(tokenStore);
		return store;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.jdbc(dataSource);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore());// 存储位置
		endpoints.userApprovalHandler(userApprovalHandler(tokenStore()));//
		endpoints.authenticationManager(authenticationManager());// 认证管理器
		endpoints.tokenEnhancer(customerEnhancer()); // 自定义token生成
		endpoints.exceptionTranslator(e -> {
			if(e instanceof SevenException){
				SevenException sevenEx = (SevenException) e;
				return ResponseEntity
                        .status(200)
                        .body(new AuthException(sevenEx.getCode(), sevenEx.getMessage()));
			}else if(e instanceof RedisConnectionFailureException){
				return ResponseEntity
                        .status(200)
                        .body(new AuthException(401, "redis连接失败"));
			}else{
				throw e;
			}
		});

		// 配置TokenServices参数
		DefaultTokenServices tokenServices = (DefaultTokenServices) endpoints
				.getDefaultAuthorizationServerTokenServices();
		tokenServices.setTokenStore(endpoints.getTokenStore());
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
		tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
		tokenServices.setAccessTokenValiditySeconds(60 * 60 * 24);
		endpoints.tokenServices(tokenServices);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")//
				.allowFormAuthenticationForClients(); // 表示允许在认证的时候把参数放到url之中传过去
	}

	@Bean
	public TokenEnhancer customerEnhancer() {
		return new CustomTokenEnhancer();
	}
}
