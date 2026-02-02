package com.beyond.polymorphism;

import com.beyond.polymorphism.practice.model.vo.Animal;
import com.beyond.polymorphism.practice.model.vo.Cat;
import com.beyond.polymorphism.practice.model.vo.Dog;

public class Application {
    public static void main(String[] args) {
        Animal[] animals = new Animal[5];
        for (Animal animal : animals) {
            if ((int) (Math.random() * 2) == 0) {
                animal = new Dog("멍멍이", "잡종", 20);
            } else {
                animal = new Cat("야옹이", "잡종", "길거리", "혼합");
            }
            animal.speak();
        }
    }
}
