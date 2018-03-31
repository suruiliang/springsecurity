package com.imooc.security.core.properties;

import lombok.Data;

/**
* @author suruiliang
* @version 创建时间：2018年3月31日 上午10:42:15
* @ClassName 类名称
* @Description 类描述
*/
@Data
public class BrowserProperties {
	private String loginPage="/imooc-signIn.html";
	private LoginType loginType=LoginType.JSON;
}
