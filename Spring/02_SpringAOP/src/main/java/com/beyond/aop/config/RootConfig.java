package com.beyond.aop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.beyond.aop")
@EnableAspectJAutoProxy
public class RootConfig {
}
