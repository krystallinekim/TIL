package com.beyond.aop.weapon;

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
    public String attack() {
        return "검을 휘두르다";
    }
}
