package com.imooc.security.core.properties;

import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;

import lombok.Data;

@Data
public class Oauth2Properties {
	
	private String jwtSignKey="imooc";
	private OAuth2ClientProperties[] clients;

}
