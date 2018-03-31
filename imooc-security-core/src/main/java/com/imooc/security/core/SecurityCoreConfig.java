package com.imooc.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.imooc.security.core.properties.SecurityProperties;

/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 上午11:01:09
 * @ClassName 类名称
 * @Description 类描述
 */
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
