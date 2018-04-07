package com.imooc.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
@EnableResourceServer
public class ImoocResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	private AuthenticationSuccessHandler imoocAuthenticationSuccessHandler;
	@Autowired
	private AuthenticationFailureHandler imoocAuthenticationFailureHandler;
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	@Autowired
	private SecurityProperties securityProperties;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.formLogin()
		.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
		.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
		.successHandler(imoocAuthenticationSuccessHandler)
		.failureHandler(imoocAuthenticationFailureHandler);

		http
		.apply(validateCodeSecurityConfig)
		.and()
		.apply(smsCodeAuthenticationSecurityConfig)
		.and()
		.formLogin()
		.and()
		.authorizeRequests()
		.antMatchers("/authentication/require","/authentication/form",
				securityProperties.getBrowser().getLoginPage(),
				"/code/*")
		.permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.csrf()
		.disable();
	}

}
