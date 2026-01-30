package com.beyond.inheritance.practice;

public class Desktop extends Product {

    private boolean allInOne;   // 일체형 여부

    public Desktop() {
        // super();  // 자동으로 실행
        // System.out.println("자식 클래스의 기본생성자 호출");  // super()보다 나중에 와야 한다
    }

    public Desktop(String code, String name, String brand, int price, boolean allInOne) {
        // super();  // 없으면 자동으로 실행, 부모클래스의 기본생성자가 알아서 실행됨
        // 1. 부모의 생성자를 호출해서 초기화
        // super(code, name, brand, price);  // 부모 클래스에 필요한 초기값을 자식 클래스에서 받아온다

        // 2. 부모의 setter 메소드를 이용해서 초기화 - 부모 객체를 super()로 생성하고, 그 객체에 setter로 초기화
        super.setCode(code);
        this.setName(name);
        setBrand(brand);
        super.setPrice(price);

        // 3. 부모의 필드에 직접 접근해서 초기화(부모 필드가 protected일 경우)
        this.code = code;
        this.name = name;
        super.brand = brand;
        super.price = price;

        this.allInOne = allInOne;
    }

    public boolean isAllInOne() {
        return allInOne;
    }

    public void setAllInOne(boolean allInOne) {
        this.allInOne = allInOne;
    }

    @Override
    public String information() {
        // 오버라이딩
        // return String.format("=== Information ===\ncode: %s\nname: %s\nbrand: %s\nprice: %d\nall in one: %s\n",
        //         super.code, super.name, super.brand, super.price, this.isAllInOne);

        // 부모 메소드를 super를 통해 호출 가능(코드 중복을 줄인다)
        return String.format("%s- 일체형 \t%s\n", super.information(), this.allInOne);
    }

}
