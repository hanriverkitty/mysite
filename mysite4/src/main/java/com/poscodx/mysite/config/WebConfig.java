package com.poscodx.mysite.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

import com.poscodx.mysite.config.web.MvcConfig;

@Configuration
@EnableAspectJAutoProxy
@Import({MvcConfig.class})
public class WebConfig {

}
