package xmcn.life;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan("xcmn.life.config")
public class BlogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }

}
