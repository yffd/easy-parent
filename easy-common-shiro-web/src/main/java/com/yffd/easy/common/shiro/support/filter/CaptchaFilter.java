package com.yffd.easy.common.shiro.support.filter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @Description  验证码拦截器 生成验证码方式.
 * @Date		 2017年9月19日 上午11:49:13 <br/>
 * @author       zhangST
 * @version		 1.0
 * @since		 JDK 1.7+
 * @see 	 
 */
public class CaptchaFilter extends OncePerRequestFilter {

	private static final Random RANDOM = new Random();
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		int width = 57;// 图像宽度
		int height = 21;// 图像高度
		// 定义输出格式
		response.setContentType("image/jpeg");
		ServletOutputStream out = response.getOutputStream();
		// 准备缓冲图像,不支持表单
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
		// 获取图形上下文环境
		Graphics gc = bimg.getGraphics();
		// 设定背景色并进行填充
		gc.setColor(getRandColor(200, 250));
		gc.fillRect(0, 0, width, height);
		// 设置图形上下文环境字体
		gc.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		// 随机产生200条干扰线条，使图像中的认证码不易被其他分析程序探测到
		gc.setColor(getRandColor(160, 200));
		for (int i = 0; i < 200; i++) {
			int x1 = RANDOM.nextInt(width);
			int y1 = RANDOM.nextInt(height);
			int x2 = RANDOM.nextInt(15);
			int y2 = RANDOM.nextInt(15);
			gc.drawLine(x1, y1, x1 + x2, y1 + y2);
		}
		// 随机产生100个干扰点，使图像中的验证码不易被其他分析程序探测到
		gc.setColor(getRandColor(120, 240));
		for (int i = 0; i < 100; i++) {
			int x = RANDOM.nextInt(width);
			int y = RANDOM.nextInt(height);
			gc.drawOval(x, y, 0, 0);
		}
		// 随机产生4个数字的验证码
		String rs = "";
		String rn = "";
		for (int i = 0; i < 4; i++) {
			rn = String.valueOf(RANDOM.nextInt(10));
			rs += rn;
			gc.setColor(new Color(20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110), 20 + RANDOM.nextInt(110)));
			gc.drawString(rn, 13 * i + 1, 16);
		}

		// 释放图形上下文环境
		gc.dispose();
		
		// 用sessionId 作为服务端session验证码的key
		HttpSession session = request.getSession();
		session.setAttribute(session.getId(), rs);
		
		ImageIO.write(bimg, "jpeg", out);
		try {
			out.flush();
		} finally {
			out.close();
		}
	}

	private Color getRandColor(int fc, int bc) {
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int red = fc + RANDOM.nextInt(bc - fc);// 红
		int green = fc + RANDOM.nextInt(bc - fc);// 绿
		int blue = fc + RANDOM.nextInt(bc - fc);// 蓝
		return new Color(red, green, blue);
	}
}

