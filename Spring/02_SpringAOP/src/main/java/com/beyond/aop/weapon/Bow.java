package com.beyond.aop.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("bow")
public class Bow extends Weapon {

    public Bow(@Value("롱보우") String name) {
        super(name);
    }

    @Override
    public String toString() {
        return "Bow{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public String attack() {
        return "활을 쏘다";
    }
}
