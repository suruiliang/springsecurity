package com.imooc.security.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

@Configuration
@EnableAuthorizationServer
public class ImoocAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired(required=false)
	private JwtAccessTokenConverter jwtAccessTokenConverter;
	@Autowired(required=false)
	private TokenEnhancer jwtTokenEnhancer;
	
	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints
		.tokenStore(tokenStore)
		.authenticationManager(authenticationManager)
		.userDetailsService(userDetailsService);
		
		if (jwtAccessTokenConverter!=null&&jwtTokenEnhancer!=null) {
			TokenEnhancerChain enhancerChain=new TokenEnhancerChain();
			List<TokenEnhancer> enhancers=new ArrayList<>();
			enhancers.add(jwtTokenEnhancer);
			enhancers.add(jwtAccessTokenConverter);
			enhancerChain.setTokenEnhancers(enhancers);
			
			endpoints
			.tokenEnhancer(enhancerChain)
			.accessTokenConverter(jwtAccessTokenConverter);
		}
	}
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory()
		.withClient("imooc")
		.secret("imoocsecret")
		.accessTokenValiditySeconds(7200)
		.authorizedGrantTypes("refresh_token","password")
		.scopes("all","read","write");
	}

}
