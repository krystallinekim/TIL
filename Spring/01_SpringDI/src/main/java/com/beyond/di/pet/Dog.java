package com.beyond.di.pet;

public class Dog extends Pet{

    public Dog() {
    }

    public Dog(String name) {
        super(name);
    }

    @Override
    public String bark() {
        return  "멍멍!";
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                '}';
    }
}
