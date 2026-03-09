package com.beyond.di.owner;

import com.beyond.di.pet.Pet;

public class Owner {
    private String name;
    private int age;
    // private Dog dog;
    // private Cat cat;
    private Pet pet;

    public Owner() {
    }

    // public Owner(String name, int age, Dog dog) {
    // public Owner(String name, int age, Cat cat) {
    public Owner(String name, int age, Pet pet) {
        this.name = name;
        this.age = age;
        // this.dog = dog;
        // this.cat = cat;
        this.pet = pet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    // public Dog getDog() {
    //     return dog;
    // }
    // public Cat getCat() {
    //     return cat;
    // }
    public Pet getPet() {
        return pet;
    }

    // public void setDog(Dog dog) {
    //     this.dog = dog;
    // }
    // public void setCat(Cat cat) {
    //     this.cat = cat;
    // }
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", age=" + age +
                // ", dog=" + dog +
                // ", cat=" + cat +
                ", pet=" + pet +
                '}';
    }
}
