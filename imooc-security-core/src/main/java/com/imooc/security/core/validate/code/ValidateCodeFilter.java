package com.imooc.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.properties.SecurityProperties;

/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 下午2:47:38
 * @ClassName 类名称
 * @Description 类描述
 */
@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{

	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	private Map<String, ValidateCodeType> urlMap = new HashMap<>();
	private AntPathMatcher pathMatcher=new AntPathMatcher();

	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImage().getUrl(), ValidateCodeType.IMAGE);

		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getCode().getSms().getUrl(), ValidateCodeType.SMS);
	}
	protected void addUrlToMap(String urlString, ValidateCodeType type) {
		if (StringUtils.isNotBlank(urlString)) {
			String[] urls = StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for (String url : urls) {
				urlMap.put(url, type);
			}
		}
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type)
						.validate(new ServletWebRequest(request, response));
				logger.info("验证码校验通过");
			} catch (ValidateCodeException exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
				return;
			}
		}

		filterChain.doFilter(request, response);

	}
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}

}
