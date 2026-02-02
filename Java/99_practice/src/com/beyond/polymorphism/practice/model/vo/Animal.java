package com.beyond.polymorphism.practice.model.vo;

public abstract class Animal {
    String name;
    String kinds;

    public Animal() {
    }

    public Animal(String name, String kinds) {
        this.name = name;
        this.kinds = kinds;
    }

    @Override
    public String toString() {
        return String.format("저의 이름은 %s이고, 종류는 %s입니다.", this.name, this.kinds);
    }

    public abstract void speak();
}
