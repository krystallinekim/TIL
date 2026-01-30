package com.beyond.inheritance.practice;

public class Television extends Product {
    private String code;    // 제품코드
    private String name;    // 제품명
    private String brand;   // 브랜드
    private int price;      // 가격
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
    public String information() {
        return String.format("%s- 크기 \t\t%d인치\n", super.information(), this.size);
    }
}
