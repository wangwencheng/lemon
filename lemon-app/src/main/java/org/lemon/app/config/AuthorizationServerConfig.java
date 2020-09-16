//package org.elib.auth.config;
//
//import cn.hutool.core.util.StrUtil;
//import lombok.AllArgsConstructor;
//import lombok.SneakyThrows;
//import org.elib.common.core.constant.SecurityConstant;
//import org.elib.common.data.tenant.TenantContextHolder;
//import org.elib.common.security.component.GbAuthenticationKeyGenerator;
//import org.elib.common.security.component.GbWebResponseExceptionTranslator;
//import org.elib.common.security.service.GbUserDetailsService;
//import org.elib.common.security.service.impl.GbClientDetailsService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.OAuth2Authentication;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//import javax.sql.DataSource;
//import java.util.List;
//
///**
// * 认证服务器配置
// *
// * @author wwc
// */
//@Configuration
//@AllArgsConstructor
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//	private final DataSource dataSource;
//	private final TokenEnhancer gbTokenEnhancer;
//	private final GbUserDetailsService gbUserDetailsService;
//	private final RedisConnectionFactory redisConnectionFactory;
//	private final AuthenticationManager authenticationManagerBean;
//
//	@Override
//	@SneakyThrows
//	public void configure(ClientDetailsServiceConfigurer clients) {
//		clients.withClientDetails(clientDetailsService());
//	}
//
//	@Override
//	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
//		oauthServer
//				.allowFormAuthenticationForClients()
//				.checkTokenAccess("isAuthenticated()");
//	}
//
//	@Override
//	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
//		endpoints
//				.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
//				.tokenStore(tokenStore())
//				.tokenEnhancer(gbTokenEnhancer)
//				.userDetailsService(gbUserDetailsService)
//				.authenticationManager(authenticationManagerBean)
//				.reuseRefreshTokens(false)
//				.pathMapping("/oauth/confirm_access", "/token/confirm_access")
//				.exceptionTranslator(new GbWebResponseExceptionTranslator());
//	}
//
//	@Bean
//	public TokenStore tokenStore() {
//		RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
//		tokenStore.setPrefix(SecurityConstant.ELIB_PREFIX + SecurityConstant.OAUTH_PREFIX);
//		tokenStore.setAuthenticationKeyGenerator(new GbAuthenticationKeyGenerator() {
//			@Override
//			public String extractKey(OAuth2Authentication authentication) {
//				String clientId = authentication.getOAuth2Request().getClientId();
//				List<String> clientOnlyList = StrUtil.splitTrim(SecurityConstant.DEVICE_ONLY_CLIENT, StrUtil.COMMA);
//				if (clientOnlyList.contains(clientId)) {
//					String deviceId = authentication.getOAuth2Request().getRequestParameters().get(SecurityConstant.DEVICE_ID_PARAMETER);
//					if (StrUtil.isNotBlank(deviceId)) {
//						return TenantContextHolder.getTenantId() + StrUtil.COLON + super.extractKey(authentication, clientId) + StrUtil.COLON + deviceId;
//					}
//				}
//				return TenantContextHolder.getTenantId() + StrUtil.COLON + super.extractKey(authentication, clientId);
//			}
//		});
//		return tokenStore;
//	}
//
//	public GbClientDetailsService clientDetailsService() {
//		GbClientDetailsService clientDetailsService = new GbClientDetailsService(dataSource);
//		clientDetailsService.setSelectClientDetailsSql(SecurityConstant.DEFAULT_SELECT_STATEMENT);
//		clientDetailsService.setFindClientDetailsSql(SecurityConstant.DEFAULT_FIND_STATEMENT);
//		return clientDetailsService;
//	}
//}
