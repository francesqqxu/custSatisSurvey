package com.chinasofti.custSatisSurvey.config;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/custSatisSurvey").setViewName("loginCust");
        registry.addViewController("/loginCust").setViewName("loginCust");
    }

    @Bean
    public LocaleResolver localeResolver(){
        return new NativeLocaleResolver();
    }

    protected static class NativeLocaleResolver implements LocaleResolver{

        @Override
        public Locale resolveLocale(HttpServletRequest request) {
            String language = request.getParameter("language");
            Locale locale = Locale.getDefault();
            if(!StringUtils.isEmpty(language)){
                String[] split = language.split("_");
                locale = new Locale(split[0],split[1]);
            }
            return locale;
        }

        @Override
        public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {

        }
    }
}

