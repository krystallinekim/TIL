package com.beyond.constructor;

import com.beyond.constructor.practice.User;

public class Application {
    public static void main(String[] args) {
        System.out.println("생성자\n");

        // 기본 생성자로 생성한 객체
        User user1 = new User();  // 기본 생성자(안만들어도 자동으로 생성 / 다른 생성자를 선언하면 자동생성 안함)
        // System.out.println(user1);
        System.out.println(user1.information());

        // 매개변수 3개로 생성한 객체
        User user2 = new User("Hong123", "password1234", "홍길동");
        // System.out.println(user2);
        System.out.println(user2.information());

        // 매개변수 5개로 생성한 객체
        User user3 = new User("Lee321", "password1111", "이몽룡", 24, 'M');
        // System.out.println(user3);
        System.out.println(user3.information());
    }
}
