package com.beyond.interf.practice;

// Basic을 구현하면, 인터페이스에 있는 추상 메소드들을 전부 구현해야 함
public abstract class Product implements Basic, Basic2 {  // 다중 구현 가능 - 
    protected String code;        // 제품코드
    protected String name;        // 제품명
    protected String brand;       // 브랜드
    protected int price;          // 가격

    public Product() {
    }

    public Product(String code, String name, String brand, int price) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
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


}
