package com.beyond.di.owner;

import com.beyond.di.pet.Cat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Owner 클래스 테스트")
class OwnerTest {

    @Test
    @Disabled
    @DisplayName("실행환경 테스트")
    void nothing() {
    }

    @Test
    @Disabled
    @DisplayName("Owner 객체 생성 테스트")
    void createOwner() {

        // 생성자를 통한 의존성 주입
        // Owner owner = new Owner("홍길동", 34, new Dog("멍멍이"));
        // Owner owner = new Owner("홍길동", 34, new Cat("야옹이"));

        // 메소드를 통한 의존성 주입
        Owner owner = new Owner();

        owner.setName("이몽룡");
        owner.setAge(24);
        // owner.setDog(new Dog("개"));
        owner.setPet(new Cat("고양이"));

        // System.out.println(owner);

        assertThat(owner).isNotNull();
        assertThat(owner.getPet()).isNotNull();
    }

    @Test
    @DisplayName("XML 어플리케이션 컨텍스트 테스트")
    void genericXmlApplicationContextTest() {
        ApplicationContext context =
                // 경로 기준은 /target(클래스 패스) 기준의 위치
                new GenericXmlApplicationContext("spring/root-context.xml");
                // 따로 classpath 기준이라고 명시할 수 있다.
                // new GenericXmlApplicationContext("classpath:spring/root-context.xml");
                // 파일 기준으로 주면 /(루트) 기준 위치 -> src부터 찾아야 함
                // new GenericXmlApplicationContext("file:src/main/resources/spring/root-context.xml");

        // Owner owner = (Owner) context.getBean("hong");
        // Owner owner = context.getBean("hong", Owner.class);
        Owner owner = context.getBean("lee", Owner.class);

        System.out.println(owner);

        assertThat(context).isNotNull();
        assertThat(owner).isNotNull();
        assertThat(owner.getPet()).isNotNull();
    }
}
