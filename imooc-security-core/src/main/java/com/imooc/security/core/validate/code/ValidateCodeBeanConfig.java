package com.imooc.security.core.validate.code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.image.DefaultImageCodeGenerator;
import com.imooc.security.core.validate.code.image.ImageCodeGenerator;
import com.imooc.security.core.validate.code.impl.SessionValdateCodeRepository;
import com.imooc.security.core.validate.code.sms.DefaultSmsCodeGenerator;
import com.imooc.security.core.validate.code.sms.DefaultSmsCodeSender;
import com.imooc.security.core.validate.code.sms.SmsCodeGenerator;
import com.imooc.security.core.validate.code.sms.SmsCodeSender;

@Configuration
public class ValidateCodeBeanConfig {
	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(ImageCodeGenerator.class)
	public ValidateCodeGenerator imageCodeGenerator(){
		DefaultImageCodeGenerator codeGenerator=new DefaultImageCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(SmsCodeGenerator.class)
	public ValidateCodeGenerator smsCodeGenerator(){
		DefaultSmsCodeGenerator codeGenerator=new DefaultSmsCodeGenerator();
		codeGenerator.setSecurityProperties(securityProperties);
		return codeGenerator;
	}
	
	@Bean
	@ConditionalOnMissingBean(SmsCodeSender.class)
	public SmsCodeSender smsCodeSender(){
		return new DefaultSmsCodeSender();
	}
	
	@Bean
	@ConditionalOnMissingBean(ValidateCodeRepository.class)
	public ValidateCodeRepository validateCodeRepository(){
		return new SessionValdateCodeRepository();
	}

}
