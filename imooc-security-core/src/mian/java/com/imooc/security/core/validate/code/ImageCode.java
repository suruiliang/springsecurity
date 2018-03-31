package com.imooc.security.core.validate.code;
/**
* @author suruiliang
* @version 创建时间：2018年3月31日 下午2:17:39
* @ClassName 类名称
* @Description 类描述
*/

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import lombok.Data;
@Data
public class ImageCode {
	private BufferedImage image;
	private String code;
	private LocalDateTime expireTime;
	public ImageCode(BufferedImage image, String code, int expireIn) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super();
		this.image = image;
		this.code = code;
		this.expireTime = expireTime;
	}
	public boolean isExpired() {
		return LocalDateTime.now().isAfter(expireTime);
	}
}
