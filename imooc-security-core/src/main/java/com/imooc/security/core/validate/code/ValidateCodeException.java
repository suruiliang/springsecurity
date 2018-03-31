package com.imooc.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
* @author suruiliang
* @version 创建时间：2018年3月31日 下午2:53:17
* @ClassName 类名称
* @Description 类描述
*/
public class ValidateCodeException extends AuthenticationException {

	private static final long serialVersionUID = -7285211528095468156L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
