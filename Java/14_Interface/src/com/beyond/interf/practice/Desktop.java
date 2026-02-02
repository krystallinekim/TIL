package com.beyond.interf.practice;

public class Desktop extends Product {

    private boolean allInOne;   // 일체형 여부

    public Desktop() {
    }

    public Desktop(String code, String name, String brand, int price, boolean allInOne) {
        super(code, name, brand, price);
        this.allInOne = allInOne;
    }

    public boolean isAllInOne() {
        return allInOne;
    }

    public void setAllInOne(boolean allInOne) {
        this.allInOne = allInOne;
    }

    @Override
    public void turnOn() {
        System.out.println("데스크탑을 켭니다");
    }

    @Override
    public void turnOff() {
        System.out.println("데스크탑을 끕니다");
    }  // 더이상 추상메소드가 아니기 때문에 에러 안남

    @Override
    public String toString() {
        return "Desktop{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", price=" + price +
                ", allInOne=" + allInOne +
                '}';
    }
}
