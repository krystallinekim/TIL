package com.beyond.di.owner;

import com.beyond.di.config.RootConfig;
import com.beyond.di.pet.Cat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

// Junit에서 Spring을 사용할 수 있도록 자동으로 확장(SpringExtension.class 기준) -> 애플리케이션 컨텍스트를 직접 만들 필요가 없어진다.
@ExtendWith(SpringExtension.class)
// @ContextConfiguration에서 설정파일어 컨텍스트를 생성함
// locations: xml 설정 읽어옴
// @ContextConfiguration(locations = "classpath:spring/root-context.xml")
//
@ContextConfiguration(classes = RootConfig.class)
@DisplayName("Owner 클래스 테스트")
class OwnerTest {

    @Autowired
    @Qualifier("lee")
    private Owner owner;

    @Test
    void autowiredTest() {
        System.out.println(owner);

        assertThat(owner).isNotNull();
        assertThat(owner.getPet()).isNotNull();
    }


    @Test
    @Disabled
    @DisplayName("실행환경 테스트")
    void nothing() {
    }

    @Test
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

    @Test
    @DisplayName("Java 방식 어플리케이션 컨텍스트 테스트")
    void annotationConfigApplicationContextTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);

        // Owner owner = context.getBean("hong", Owner.class);
        Owner owner = context.getBean("lee", Owner.class);

        System.out.println(owner);

        assertThat(context).isNotNull();
        assertThat(owner).isNotNull();
        assertThat(owner.getPet()).isNotNull();
    }



}
