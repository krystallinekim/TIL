package com.beyond.di.config;

import com.beyond.di.owner.Owner;
import com.beyond.di.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OwnerConfig {

    @Bean("hong")
    // @Autowired로 자동의로 의존성 주입이 가능함
    // Pet 타입의 객체가 있는지 보고, 있다면 알아서 주입한다.
    public Owner owner(@Autowired Pet pet) {
        // setter를 이용해 빈 선언
        Owner owner = new Owner();

        owner.setName("홍길동");
        owner.setAge(34);
        // 빈은 싱글톤 패턴이다 - 여러번 호출되어도 실제로 생성된 객체는 하나만
        // owner.setPet(dog());

        // 일반적으로 의존하는 객체를 매개값으로 전달받도록 해서 받는다.
        owner.setPet(pet);

        return owner;
    }


    // Bean에 이름 선언 안하면 그냥 클래스명으로 이름을 설정
    @Bean
    public Owner lee(/* @Autowired */ @Qualifier("dog") Pet pet) {
        // 생성자로 빈 선언
        // pet이 없다면 애초에 객체를 만들 수가 없다 - 애초에 빈을 만드려면 객체가 필요해 이 단계부터 pet을 찾아와 주입함
        return new Owner("이몽룡", 24, pet);
    }


}
