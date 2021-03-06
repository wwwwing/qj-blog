package xmcn.life.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        Properties properties = new Properties();
        properties.put("kaptcha.border","no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.string", "1234567890");
        properties.put("kaptcha.textproducer.char.space", "5");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.textproducer.font.names", "Arial,Courier,cmr10,宋体,楷体,微软雅黑");
        Config config = new Config(properties);
        DefaultKaptcha kaptcha = new DefaultKaptcha();
        kaptcha.setConfig(config);
        return kaptcha;
    }
}
