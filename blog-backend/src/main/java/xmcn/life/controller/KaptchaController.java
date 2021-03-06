package xmcn.life.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xcmn.life.config.RedisService;
import xmcn.life.service.CaptchaService;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
public class KaptchaController {
    private final CaptchaService captchaService;
    private final RedisService redisService;

    public KaptchaController(CaptchaService captchaService, RedisService redisService) {
        this.captchaService = captchaService;
        this.redisService = redisService;
    }


    @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response){
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        try(ServletOutputStream outputStream = response.getOutputStream()){
            BufferedImage bufferedImage = captchaService.getCaptcha("33333");
            ImageIO.write(bufferedImage,"jpg",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
    }

}
