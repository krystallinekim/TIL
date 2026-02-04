package com.beyond.map.practice;

public class Snack {
    private String name;
    private String flavor;
    private int calories;

    public Snack(String name, String flavor, int calories) {
        this.name = name;
        this.flavor = flavor;
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Snack{" +
                "name='" + name + '\'' +
                ", flavor='" + flavor + '\'' +
                ", calories=" + calories +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getFlavor() {
        return flavor;
    }

    public int getCalories() {
        return calories;
    }

}
