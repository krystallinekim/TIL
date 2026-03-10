package com.beyond.di.character;

import com.beyond.di.weapon.Weapon;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/*
    # character.properties 파일을 읽어오기

    1. 설정 파일에 character.properties 등록
        - XML -> <context:property-placeholder location="경로"/>

    2. 스프링 property placeholder를 이용해 character.properties 파일의 값을 읽어옴

    3. 프로퍼티 파일이 여러개일 경우
*/


@Component
@Getter
@ToString
@RequiredArgsConstructor
public class Character {

    @Value("${character.name:홍길동}")
    private String name;

    @Value("${character.level:00}")
    private int level;




    // 1. 필드에 직접 빈을 주입
    // @Qualifier("shortbow")
    // @Autowired
    // private Weapon weapon;

    // 2. Setter 메소드로 빈 주입
    // 2-1) 직접 Setter 메소드 생성 - 어노테이션을 메소드 전체에 줘야 함
    // private Weapon weapon;

    // @Autowired
    // public void setWeapon(Weapon weapon) {
    //     this.weapon = weapon;
    // }

    // 2-2) lombok 사용
    // @Setter(onMethod_ = @Autowired)
    // private Weapon weapon;

    // 3. 생성자 활용
    // 3-1) 직접 생성자 생성
    // private Weapon weapon;

    // public Character(Weapon weapon) {
    //     this.weapon = weapon;
    // }

    // 3-2) lombok 사용 - final로 선언 후 @RequiredConstructor -> 자동으로 주입됨
    private final Weapon weapon;


}
