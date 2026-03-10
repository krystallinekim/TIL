package com.beyond.di.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component("longsword")
public class Sword extends Weapon {

    public Sword(@Value("${character.weapon.name:바스타드 소드}") String name) {
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
