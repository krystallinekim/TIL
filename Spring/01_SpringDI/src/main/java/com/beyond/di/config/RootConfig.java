package com.beyond.di.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

// 해당 클래스가 자바 설정 파일임을 선언
@Configuration
// 다른 설정 클래스를 가져와서 선언할 수 있음
@Import(value = {OwnerConfig.class, PetConfig.class})
@ComponentScan("com.beyond.di")
public class RootConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer configurer
                = new PropertySourcesPlaceholderConfigurer();

        configurer.setLocations(
                new ClassPathResource("character.properties"),
                new ClassPathResource("driver.properties"));

        return configurer;
    }


}
