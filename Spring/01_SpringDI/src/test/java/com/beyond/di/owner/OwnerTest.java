package com.beyond.di.owner;

import com.beyond.di.pet.Cat;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Owner 클래스 테스트")
class OwnerTest {

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
}
