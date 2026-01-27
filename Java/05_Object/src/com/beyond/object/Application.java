package com.beyond.object;

import com.beyond.object.practice.Person;

public class Application {
    public static void main(String[] args) {
        System.out.println("Hello World");
        // Hong이라는 객체를 생성(생성자를 호출한다)
        Person Hong = new Person();  // 클래스명 변수명 = new 생성자(없으면 자동 생성됨)

        Hong.setName("홍길동"); // 매개값을 전달
        Hong.setAge(34);
        Hong.whoAreYou();

        // Lee라는 객체를 생성
        Person Lee = new Person();
        Lee.setName("이몽룡");
        Lee.setAge(24);
        Lee.whoAreYou();

        // 하나의 클래스로 사람을 둘 만들었음
        // 캡슐화되어 다른 객체에 다른 이름/나이로 존재함
    }
}
