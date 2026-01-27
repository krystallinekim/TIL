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

    // 2. constructor(생성자)
    // 클래스의 이름과 생성자 이름은 같다
    // 생긴건 메소드처럼 생김
    public Person() {
        System.out.println("생성자 호출");
    }

    // 3. 메소드: 객체의 기능, 동작 -> 이름이 동사형
    public void whoAreYou() {
        System.out.printf("저는 %s이고, %d세입니다.\n", name, age);  // field에서 가져옴
    }

    // 여기서 String n, int a는 매개변수이다
    public void setName(String n) {
        name = n;
    }
    public void setAge(int a) {
        age = a;
    }
}
