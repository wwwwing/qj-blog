package xmcn.life.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Service;
import xmcn.life.service.CaptchaService;

import java.awt.image.BufferedImage;

@Service("CaptchaService")
public class CaptchaServiceImpl implements CaptchaService {
    private final Producer producer;

    public CaptchaServiceImpl(Producer producer) {
        this.producer = producer;
    }

    @Override
    public BufferedImage getCaptcha(String uuid) {
        if(StringUtils.isBlank(uuid)){
            return null;
        }
        String code = producer.createText();

        return producer.createImage(code);
    }
}
