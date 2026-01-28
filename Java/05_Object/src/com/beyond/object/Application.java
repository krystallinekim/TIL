package com.beyond.object;

import com.beyond.object.practice.Person;

public class Application {
    public static void main(String[] args) {
        System.out.println("=== Object ===");

        // Hong이라는 객체를 생성(생성자를 호출한다)
        Person Hong = new Person();  // 클래스명 변수명 = new 생성자(없으면 자동 생성됨)

        Hong.setName("홍길동");  // 매개값을 전달
        Hong.setAge(34);
        Hong.setPhone("갤럭시 S25+", "네이비", "삼성");  // 매개변수가 있다면, 무조건 전달을 해줘야 함
        Hong.whoAreYou();


        // Lee라는 객체를 생성
        Person Lee = new Person();
        Lee.setName("이몽룡");
        Lee.setAge(24);
        Lee.setPhone("아이폰 15 프로", "화이트", "애플");
        Lee.whoAreYou();
        // 하나의 클래스로 사람을 둘 만들었음
        // 캡슐화되어 다른 객체에 다른 이름/나이로 존재함

        // new Phone();
        // Phone 클래스는 default - 패키지 외부에서 접근 불가

    }
}
