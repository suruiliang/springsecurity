package com.imooc.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

/**
* @author suruiliang
* @version 创建时间：2018年3月31日 上午10:41:41
* @ClassName 类名称
* @Description 类描述
*/
@ConfigurationProperties(prefix="imooc.security")
@Data
public class SecurityProperties {

	private BrowserProperties browser=new BrowserProperties();
	
	private ValidateCodeProperties code=new ValidateCodeProperties();

}
