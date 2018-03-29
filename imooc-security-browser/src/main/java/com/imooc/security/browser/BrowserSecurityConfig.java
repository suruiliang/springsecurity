package com.imooc.security.browser;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author suruiliang
 * @version 创建时间：2018年3月29日 下午1:33:19
 * @ClassName 类名称
 * @Description 类描述
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()
//		http.httpBasic()
		.and()
		.authorizeRequests()
		.anyRequest()
		.authenticated();
	}
}
