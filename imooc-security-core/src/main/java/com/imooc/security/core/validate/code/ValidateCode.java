package com.imooc.security.core.validate.code;

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
