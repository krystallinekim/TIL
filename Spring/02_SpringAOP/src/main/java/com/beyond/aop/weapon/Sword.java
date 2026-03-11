package com.beyond.aop.weapon;

import com.beyond.aop.annotation.Repeat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("sword")
public class Sword extends Weapon {

    public Sword(@Value("플람베르주") String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Sword{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    // @Nologging
    @Repeat(value = "반복 횟수 지정", count = 3)
    public String attack() {


        return "검을 휘두르다";
    }
}
