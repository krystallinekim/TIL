package com.beyond.di.config;

import com.beyond.di.pet.Cat;
import com.beyond.di.pet.Dog;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class PetConfig {

    @Bean
    public Dog dog() {
        Dog dog = new Dog();
        dog.setName("댕댕이");
        return dog;
    }

    // pet이 여러개 있다면 기본으로 사용됨
    @Primary
    @Bean
    public Cat cat() {
        return new Cat("야옹이");
    }

}
