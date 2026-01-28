package com.beyond.constructor;

import com.beyond.constructor.practice.User;

public class Application {
    public static void main(String[] args) {

        User user1 = new User();  // 기본 생성자(안만들어도 자동으로 생성 / 다른 생성자를 선언하면 자동생성 안함)
        System.out.println(user1.information());

        User user2 = new User("Hong123", "password1234", "홍길동");
        System.out.println(user2.information());
    }
}
