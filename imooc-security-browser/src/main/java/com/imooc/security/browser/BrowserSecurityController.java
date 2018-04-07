package com.imooc.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.support.SimpleResponse;

/**
* @author suruiliang
* @version 创建时间：2018年3月31日 上午10:18:58
* @ClassName 类名称
* @Description 类描述
*/
@RestController
public class BrowserSecurityController {
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	private RequestCache requestCache=new HttpSessionRequestCache();
	private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
	@Autowired
	private SecurityProperties securityProperties;
	
	@RequestMapping("/authentication/require")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException {
		SavedRequest savedRequest=requestCache.getRequest(request, response);
		if (savedRequest!=null) {
			String targetUrl=savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是："+targetUrl);
			if (StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}

}
