package com.beyond.inheritance.practice;

// 공통으로 사용하던 필드, 메소드를 추출
public class Product {
    protected String code;        // 제품코드
    protected String name;        // 제품명
    protected String brand;       // 브랜드
    protected int price;          // 가격

    public Product() {
        // super();  // 기본적으로 Object 클래스 상속
        // System.out.println("부모 클래스의 기본생성자 호출");
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

    public String information() {
        return String.format("=== Information - Product ===\n- 제품코드 \t%s\n- 제품명 \t%s\n- 브랜드 \t%s\n- 가격 \t\t%d원\n",
                this.code, this.name, this.brand, this.price);
    }
}
