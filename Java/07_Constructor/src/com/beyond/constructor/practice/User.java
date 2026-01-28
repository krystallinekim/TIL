package com.beyond.constructor.practice;

public class User {

    // 필드 선언
    private String id;
    private String password;
    private String name;
    private int age;
    private char gender;

    // 생성자
    // 기본 생성자(선언 안할때만 만들어줌)
    public User() {};

    // 매개변수가 있는 생성자(객체 생성과 동시에 필드를 초기화하기 위한 목적
    public User(String id, String password, String name) {
        this.id = id;
        this.password = password;
        this.name = name;
        // 나머지 필드는 기본 초기값
    }

    // 메소드
    public String information() {
        return String.format(
                "id: %s\npassword: %s\nname: %s\nage: %d\ngender: %c\n",
                id, password, name, age, gender
        );
    }
}
