package com.beyond.aop.character;

import com.beyond.aop.config.RootConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RootConfig.class)
class CharacterTest {

    @Autowired
    private Character character;

    @Test
    void nothing() {
    }

    @Test
    @DisplayName("Character 객체 생성 테스트")
    void create() {
        // System.out.println(character);

        assertThat(character).isNotNull();
        assertThat(character.getWeapon()).isNotNull();
    }

    @Test
    @DisplayName("quest 메소드 테스트")
    void questTest() {
        // String quest = character.quest("스트라솔름 정화하기");
        // System.out.println(quest);
        // character는 프록시 객체가 되고, 현재 around 객체가 아무것도 리턴하지 않으므로 null
        assertThat(character.quest("스트라솔름 정화하기")).isNotNull().contains("스트라솔름 정화하기");



    }


    @Test
    @DisplayName("attack 메소드 테스트")
    void attackTest() {
        assertThat(character.getWeapon()).isNotNull();
        assertThat(character.getWeapon().attack()).isNotNull();
    }
}
