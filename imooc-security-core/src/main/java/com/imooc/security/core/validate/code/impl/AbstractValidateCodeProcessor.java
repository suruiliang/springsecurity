package com.imooc.security.core.validate.code.impl;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.ValidateCode;
import com.imooc.security.core.validate.code.ValidateCodeException;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.ValidateCodeProcessor;
import com.imooc.security.core.validate.code.ValidateCodeRepository;
import com.imooc.security.core.validate.code.ValidateCodeType;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	@Autowired
	private ValidateCodeRepository validateCodeRepository;
	
	
	@Override
	public void create(ServletWebRequest request) throws Exception {
		C validateCode=generate(request);
		save(request,validateCode);
		send(request,validateCode);
		
	}
	
	@SuppressWarnings("unchecked")
	private C generate(ServletWebRequest request){
		String type=getProcessorType(request);
		ValidateCodeGenerator validateCodeGenerator=validateCodeGenerators.get(type+"CodeGenerator");
		return (C) validateCodeGenerator.generate(request);
	}
	private String getProcessorType(ServletWebRequest request) {
		return StringUtils.substringAfter(request.getRequest().getRequestURI(), "/code/");
	}
	private void save(ServletWebRequest request, C validateCode){
		ValidateCode code=new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType(request));
	}
	
	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeType codeType = getValidateCodeType(request);

		@SuppressWarnings("unchecked")
		C codeInSession = (C) validateCodeRepository.get(request, codeType);

		String codeInRequest;
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					codeType.getParamNameOnValidate());
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(codeType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(codeType + "验证码不存在");
		}

		if (codeInSession.isExpired()) {
			validateCodeRepository.remove(request, codeType);
			throw new ValidateCodeException(codeType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(codeType + "验证码不匹配");
		}

		validateCodeRepository.remove(request, codeType);
	}
	private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	}
	private String getSessionKey(ServletWebRequest request) {
		return SESSION_KEY_PREFIX + getValidateCodeType(request).toString().toUpperCase();
	}
	public abstract void send(ServletWebRequest request, C validateCode) throws Exception;
	
}
