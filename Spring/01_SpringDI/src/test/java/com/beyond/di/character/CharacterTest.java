package com.beyond.di.character;

import com.beyond.di.config.RootConfig;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = "classpath:spring/root-context.xml")
@ContextConfiguration(classes = RootConfig.class)
@DisplayName("캐릭터 클래스 테스트")
class CharacterTest {

    // 기본: Autowired로 받을 빈이 없으면 에러
    // required 속성은 빈 주입이 필수로 되어야 하는지 설정하는 속성
    // 기본값은 true이고, false일 경우 예외가 아니라 그 자리에 null을 주입한다.
    @Autowired(required = false)
    private Character character;

    @Value("${db.driver:not found}")
    private String driver;

    @Value("${db.url:not found}")
    private String url;



    @Test
    @Disabled
    void nothing() {
    }

    @Test
    @DisplayName("Character 객체 생성 테스트")
    void create() {
        System.out.println(character);

        assertThat(character).isNotNull();
        assertThat(character.getWeapon()).isNotNull();
    }

    @Test
    @DisplayName("driver.properties 파일 테스트")
    void propertyTest() {
        System.out.println(driver);
        System.out.println(url);

        assertThat(driver).isNotNull().isEqualTo("org.mariadb.jdbc.Driver");
        assertThat(url).isNotNull().isEqualTo("jdbc:mariadb://localhost:3306/web");
    }
}
