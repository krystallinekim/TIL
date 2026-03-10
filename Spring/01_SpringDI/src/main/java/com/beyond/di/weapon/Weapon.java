package com.beyond.di.weapon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class Weapon {
    protected String name;

    public abstract String attack();
}
