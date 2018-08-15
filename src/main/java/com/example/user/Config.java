package com.example.user;

import com.captcha.botdetect.web.servlet.CaptchaServlet;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import java.util.Arrays;

@Configuration
public class Config implements WebMvcConfigurer {


    @Value("${mailHost}")
    private String mailHost;
    @Value("${mailUser}")
    private String mailUser;
    @Value("${mailPassword}")
    private String mailPassword;

    @Bean
    public UrlBasedViewResolver urlBasedViewResolver() {
        UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
        urlBasedViewResolver.setViewClass(JstlView.class);
        urlBasedViewResolver.setPrefix("/WEB-INF/pages/");
        urlBasedViewResolver.setSuffix(".jsp");
        urlBasedViewResolver.setOrder(1);
        return urlBasedViewResolver;
    }

    @Bean
    public ServletRegistrationBean dispatcheServletRegistratior() {
        ServletRegistrationBean registrationBean = new ServletRegistrationBean(new CaptchaServlet());
        registrationBean.setUrlMappings(Arrays.asList("/botdetectcaptcha"));
        return registrationBean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/WEB-INF/pages/css/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/WEB-INF/pages/js/");
    }

    @Bean
    public JavaMailSenderImpl mailSender() {

        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailHost);
        javaMailSender.setUsername(mailUser);
        javaMailSender.setPassword(mailPassword);
        return javaMailSender;
    }

    @Bean
    public ModelMapper modelMapper() {

        return new ModelMapper();
    }


}
