package com.beyond.aop.character;

import com.beyond.aop.weapon.Weapon;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
@Data
public class Character {

    @Value("홍길동")
    private String name;

    @Value("70")
    private int level;

    @Autowired
    @Qualifier("sword")
//    @Qualifier("bow")
    private Weapon weapon;

    public void quest(String questName) {
        System.out.printf("[%s] 퀘스트 진행 중\n", questName);
    }

}
