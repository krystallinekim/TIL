package com.beyond.object.practice;

class Phone {
    // 필드
    private String name;

    private String color;

    private String brand;

    // 생성자
    // 생략 시 default에 빈 메소드
    // 에디터에 Alt+Ins 시 마법사로 만들어줌
    public Phone() {
    }

    // 메소드
    // Alt+Ins로 getter, setter 메소드를 한번에 만들기도 가능
    // getter와 setter는 외부에서 접근 불가능한 필드에 간접적으로 접근하게 해주는 메소드
    // getter, setter는 필수가 아님
    public String getName() {
        // getter는 외부에서 필드 값을 가져오는 메소드
        return name;
    }

    public void setName(String name) {
        this.name = name;
        // 매개변수의 이름과 필드의 이름이 같아 에러가 생긴다
        // this를 붙여서 객체의 필드와 매개변수의 차이를 줌
        // this.name은 현재 객체의 필드, 그냥 name은 매개변수
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
