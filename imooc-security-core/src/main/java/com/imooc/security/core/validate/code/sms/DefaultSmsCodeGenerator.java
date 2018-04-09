package com.imooc.security.core.validate.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;

//@Component("smsCodeGenerator")
public class DefaultSmsCodeGenerator extends SmsCodeGenerator {
	private SecurityProperties securityProperties;

	public final SecurityProperties getSecurityProperties() {
		return securityProperties;
	}


	public final void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}


	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code=RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	}

}
