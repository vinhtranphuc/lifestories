package com.tranphucvinh.config;

import org.apache.tomcat.util.http.LegacyCookieProcessor;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tranphucvinh.security.capcha.CaptchaGenerator;

@Configuration
public class BeanCustomConfig {

    /**
     * SpringCustomConfigurator
     *
     * @return
     */
    @Bean
    public SpringCustomConfigurator springCustomConfigurator() {
        return new SpringCustomConfigurator(); // This is just to get context
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> cookieProcessorCustomizer() {
        return (serverFactory) -> serverFactory.addContextCustomizers(
                (context) -> context.setCookieProcessor(new LegacyCookieProcessor()));
    }

    @Bean
    public CaptchaGenerator getCaptchaGenerator() {
        return new CaptchaGenerator();
    }
}