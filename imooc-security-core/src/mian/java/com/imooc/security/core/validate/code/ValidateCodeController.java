package com.imooc.security.core.validate.code;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * @author suruiliang
 * @version 创建时间：2018年3月31日 下午2:21:24
 * @ClassName 类名称
 * @Description 类描述
 */
@RestController
public class ValidateCodeController {
	static final String SESSION_KEY="SESSION_KEY_IMAGE_CODE";
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();

	@GetMapping(value="/code/image")
	public void createCode(HttpServletRequest request,HttpServletResponse response) throws IOException {
		ImageCode imageCode=createImageCode(request);
		sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
		ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
	}

	private ImageCode createImageCode(HttpServletRequest request) {
		int width = 70; 
		int height = 30;
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
		for (int i = 0; i < 4; i++) {
			String rand=String.valueOf(random.nextInt(10));
			sRand+=rand;
			g.setColor(getRandomColor(10, 100));
			g.drawString(rand, 16 * i + random.nextInt(7), height - random.nextInt(6));
		}
		g.dispose();
		return new ImageCode(image, sRand, 60);
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
