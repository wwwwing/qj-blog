package xmcn.life.service;

import java.awt.image.BufferedImage;

/**
 * 验证码
 * @author wing
 */
public interface CaptchaService {

    BufferedImage getCaptcha(String uuid);
}
