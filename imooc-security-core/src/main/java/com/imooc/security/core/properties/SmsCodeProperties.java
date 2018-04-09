package com.imooc.security.core.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsCodeProperties {
	private int length=4;
	private int expireIn=60;
	private String url="";
}
