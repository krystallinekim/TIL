package com.beyond.object.practice;

/*
클래스에서 사용 가능한 접근 제한자 (클래스는 대부분 public)
- public: 제약 없음. public 제한자는 한 클래스 파일에 하나만 선언 가능
- default: 이런 접근제한자는 없다. 지워놓으면 default
 */

public class Person {
    // 1. field: 객체의 속성, 데이터
    // 클래스 내에서는 어디에서나 접근 가능하다(생성자나 메소드에서도)
    private String name;
    private int age;
    // 변수랑 똑같이 만들어지고, 생긴거도 똑같지만 변수가 아니다
    // 주로 private를 걸고 만드는데, 이 클래스 내부에서만 접근 가능하도록

    private Phone phone = new Phone();
    // Phone 클래스는 Person 클래스와 같은 패키지 내에 있음 - default 클래스 사용 가능


    // 2. constructor(생성자)
    // 클래스의 이름과 생성자 이름은 같아야 하고, 한 클래스에 생성자는 하나는 무조건 존재해야 함
    // 메소드의 일종
    public Person() {
        // System.out.println("생성자 호출");
    }

    // 3. 메소드: 객체의 기능, 동작 -> 이름이 동사형
    public void whoAreYou() {
        System.out.printf("저는 %s이고, %d세입니다.\n", name, age);  // field에서 가져옴
        System.out.printf("휴대폰은 %s 컬러의 %s %s를 사용합니다.\n",
                phone.getColor(), phone.getBrand(), phone.getName());
    }

    // field에 외부에서 접근이 불가능하기 때문에, 메소드를 통해 간접적으로 접근
    // 여기서 String n, int a는 매개변수이다
    public void setName(String n) {
        name = n;
    }
    public void setAge(int a) {
        age = a;
    }

    public void setPhone(String name, String color, String brand) {
        phone.setName(name);
        phone.setColor(color);
        phone.setBrand(brand);
    }
}
