package com.imooc.security.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.SecurityProperties;


/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 下午2:21:24
 * @ClassName 类名称
 * @Description 类描述
 */
@RestController
public class ValidateCodeController {
	static final String SESSION_KEY="SESSION_KEY_IMAGE_CODE";
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	@Autowired
	private ValidateCodeGenerator imageCodeGenerator;
	

	@GetMapping(value="/code/image")
	public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ImageCode imageCode=imageCodeGenerator.generate(new ServletWebRequest(request));
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

	
}
