package com.beyond.interf.practice;

public class SmartPhone extends Product {

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
    public void turnOn() {
        System.out.println("스마트폰을 켭니다");
    }

    @Override
    public void turnOff() {
        System.out.println("스마트폰을 끕니다");
    }

    @Override
    public String toString() {
        return "SmartPhone{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", mobileAgency='" + mobileAgency + '\'' +
                '}';
    }
}
