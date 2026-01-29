package com.beyond.inheritance.practice;

public class SmartPhone{
    private String code;         // 제품코드
    private String name;         // 제품명
    private String brand;        // 브랜드
    private int price;           // 가격
    private String mobileAgency; // 통신사

    public SmartPhone() {
    }

    public SmartPhone(String code, String name, String brand, int price, String mobileAgency) {
        this.code = code;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.mobileAgency = mobileAgency;
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

    public String getMobileAgency() {
        return mobileAgency;
    }

    public void setMobileAgency(String mobileAgency) {
        this.mobileAgency = mobileAgency;
    }

    public String information() {
        return String.format("code: %s\nname: %s\nbrand: %s\nprice: %d\nmobile agency: %s\n",
                this.code, this.name, this.brand, this.price, this.mobileAgency);
    }
}
