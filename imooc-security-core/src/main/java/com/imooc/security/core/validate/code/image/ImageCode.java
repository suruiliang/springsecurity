package com.imooc.security.core.validate.code.image;
/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 下午2:17:39
 * @ClassName 类名称
 * @Description 类描述
 */

import java.awt.image.BufferedImage;
import java.time.LocalDateTime;

import com.imooc.security.core.validate.code.ValidateCode;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ImageCode extends ValidateCode{
	private BufferedImage image;
	public ImageCode(BufferedImage image, String code, int expireIn) {
		super(code,expireIn);
		this.image = image;
	}
	public ImageCode(BufferedImage image, String code, LocalDateTime expireTime) {
		super(code,expireTime);
		this.image = image;
	}
}
