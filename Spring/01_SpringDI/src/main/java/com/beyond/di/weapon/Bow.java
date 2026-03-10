package com.beyond.di.weapon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("shortbow")
public class Bow extends Weapon {

    public Bow(@Value("숏보우") String name) {
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
