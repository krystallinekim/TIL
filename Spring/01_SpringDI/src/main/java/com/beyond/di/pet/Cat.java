package com.beyond.di.pet;

public class Cat extends Pet{

    public Cat() {
    }

    public Cat(String name) {
        super(name);
    }

    @Override
    public String bark() {
        return  "야옹~";
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                '}';
    }
}
