package com.imooc.code;


import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.validate.code.ValidateCodeGenerator;
import com.imooc.security.core.validate.code.image.ImageCode;
import com.imooc.security.core.validate.code.image.ImageCodeGenerator;

//@Component("imageCodeGenerator")
public class DemoImageCodeGenerator extends ImageCodeGenerator {

	@Override
	public ImageCode generate(ServletWebRequest request) {
		System.out.println("更高级的图形验证码生成代码");
		return null;
	}

}
