package com.beyond.inheritance.practice;

public class Desktop extends Product {

    private boolean allInOne;   // 일체형 여부

    public Desktop() {
        // super();  // 자동으로 실행
        // System.out.println("자식 클래스의 기본생성자 호출");  // super()보다 나중에 와야 한다
    }

    public Desktop(String code, String name, String brand, int price, boolean allInOne) {
        // super();  // 없으면 자동으로 실행, 부모클래스의 기본생성자가 알아서 실행됨
        super(code, name, brand, price);  // 부모 클래스에 필요한 초기값을 자식 클래스에서 받아온다
        this.allInOne = allInOne;
    }

    public boolean isAllInOne() {
        return allInOne;
    }

    public void setAllInOne(boolean allInOne) {
        this.allInOne = allInOne;
    }

}
