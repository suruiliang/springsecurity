package com.imooc.security.core.validate.code;
/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 下午2:17:39
 * @ClassName 类名称
 * @Description 类描述
 */

import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ValidateCode implements Serializable{
	
	private String code;
	private LocalDateTime expireTime;
	public ValidateCode(String code, int expireIn) {
		super();
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	public ValidateCode(String code, LocalDateTime expireTime) {
		super();
		this.code = code;
		this.expireTime = expireTime;
	}
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}
}
