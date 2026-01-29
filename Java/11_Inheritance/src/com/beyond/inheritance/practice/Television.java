package com.beyond.inheritance.practice;

public class Television{
    private String code;    // 제품코드
    private String name;    // 제품명
    private String brand;   // 브랜드
    private int price;      // 가격
    private int size;       // 크기(인치)

    public Television() {
    }

    public Television(String code, String name, String brand, int price, int size) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.size = size;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String information() {
        return String.format("code: %s\nname: %s\nbrand: %s\nprice: %d\nsize(inch): %d\n",
                this.code, this.name, this.brand, this.price, this.size);
    }

}
