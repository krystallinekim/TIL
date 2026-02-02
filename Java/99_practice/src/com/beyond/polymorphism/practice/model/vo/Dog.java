package com.beyond.polymorphism.practice.model.vo;

public class Dog extends Animal{
    private final String PLACE = "애견카페";
    private int weight;

    public Dog() {
    }

    public Dog(String name, String kinds, int weight) {
        super(name, kinds);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void speak() {
        System.out.printf("%s 몸무게는 %dkg입니다.\n", super.toString(), this.weight);
    }
}
