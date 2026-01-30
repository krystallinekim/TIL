package com.beyond.polymorphism.practice;

public class Television extends Product {

    private int size;       // 크기(인치)

    public Television() {
    }

    public Television(String code, String name, String brand, int price, int size) {
        super(code, name, brand, price);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Television{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", size=" + size +
                '}';
    }
}
