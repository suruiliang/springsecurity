package com.imooc.security.core.validate.code.image;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.validate.code.ValidateCodeGenerator;

public class ImageCodeGenerator implements ValidateCodeGenerator {
	private SecurityProperties securityProperties;

	public final SecurityProperties getSecurityProperties() {
		return securityProperties;
	}
	public final void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width = ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getImage().getWidth()); 
		int height = ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getImage().getHeight()); 
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);                
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandomColor(180, 250));
		g.fillRect(0, 0, width, height);
		g.setFont(new Font("Times New Roman", Font.ITALIC, height));
		g.setColor(getRandomColor(120, 180));

		for(int i=0;i<155;i++){
			int x=random.nextInt(width);
			int y=random.nextInt(height);
			int x1=random.nextInt(width);
			int y1=random.nextInt(height);
			g.drawLine(x, y, x+x1, y+y1);
		}

		String sRand="";
		for (int i = 0; i < securityProperties.getCode().getImage().getLength(); i++) {
			String rand=String.valueOf(random.nextInt(10));
			sRand+=rand;
			g.setColor(getRandomColor(10, 100));
			g.drawString(rand, 13 * i + 6, height - random.nextInt(6));
		}
		g.dispose();
		return new ImageCode(image, sRand, securityProperties.getCode().getImage().getExpireIn());
	}
	private Color getRandomColor(int minColor, int maxColor) {

		Random random = new Random();
		if (minColor > 255)
			minColor = 255;
		if (maxColor > 255)
			maxColor = 255;
		int red = minColor + random.nextInt(maxColor - minColor);
		int green = minColor + random.nextInt(maxColor - minColor);
		int blue = minColor + random.nextInt(maxColor - minColor);
		return new Color(red, green, blue);

	}

}
