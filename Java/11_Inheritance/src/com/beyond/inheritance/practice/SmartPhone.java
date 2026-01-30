package com.beyond.inheritance.practice;

public class SmartPhone extends Product {
    private String code;         // 제품코드
    private String name;         // 제품명
    private String brand;        // 브랜드
    private int price;           // 가격
    private String mobileAgency; // 통신사

    public SmartPhone() {
    }

    public SmartPhone(String code, String name, String brand, int price, String mobileAgency) {
        super(code, name, brand, price);
        this.mobileAgency = mobileAgency;
    }

    public String getMobileAgency() {
        return mobileAgency;
    }

    public void setMobileAgency(String mobileAgency) {
        this.mobileAgency = mobileAgency;
    }

    @Override
    public String information() {
        return String.format("%s- 통신사 \t%s\n", super.information(), this.mobileAgency);
    }
}
