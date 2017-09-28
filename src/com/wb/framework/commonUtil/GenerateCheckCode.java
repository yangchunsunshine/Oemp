package com.wb.framework.commonUtil;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 
 * 验证码生成工具类
 * 
 * @author 郝洋
 * @version [版本号, 2016-4-8]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class GenerateCheckCode extends HttpServlet
{
    
    private static final long serialVersionUID = -1017341430367649593L;
    
    private final Font mFont = new Font("Arial", Font.BOLD, 16);
    
    @Override
    public void destroy()
    {
        super.destroy();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        int width = 50, height = 18;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(new Color(25, 25, 25));
        g.fillRect(1, 1, width - 1, height - 1);
        g.setColor(new Color(25, 25, 25));
        g.drawRect(0, 0, width - 1, height - 1);
        g.setFont(mFont);
        String sRand = "";
        for (int i = 0; i < 4; i++)
        {
            String tmp = getRandomChar();
            sRand += tmp;
            // g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.setColor(new Color(235, 47, 0));
            g.drawString(tmp, 13 * i + 1, 16);
        }
        HttpSession session = request.getSession(true);
        session.setAttribute("verifyCode", sRand);
        g.dispose();
        ImageIO.write(image, "JPEG", response.getOutputStream());
        image.flush();
    }
    
    /**
     * 
     * 重载方法
     * 
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        doGet(request, response);
    }
    
    /**
     * 
     * 重载方法
     * 
     * @throws ServletException
     */
    @Override
    public void init()
        throws ServletException
    {
        // Put your code here
    }
    
    /**
     * 
     * 获取随机数
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getRandomChar()
    {
        Random rd = new Random();
        return String.valueOf(rd.nextInt(10));
    }
}
