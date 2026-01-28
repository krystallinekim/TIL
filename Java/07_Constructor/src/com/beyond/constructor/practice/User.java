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

    // 서로 다른 생성자를 구분하는 기준은 매개변수의 타입과 개수
    public User(String id, String password, String name, int age, char gender) {
        this(id, password, name);  // 생성자 내부에서 다른 생성자 호출
        // this();  // 매개변수 없는 생성자가 있어서 이거도 에러는 안남.
        // this.id = id;
        // this.password = password;
        // this.name = name;  // id, pw, name은 위의 생성자와 중복된다
        this.age = age;
        this.gender = gender;
    }

    // 메소드
    public String information() {
        // System.out.println(this);  // this = 객체 자기 자신 참조하는 변수
        // this("Seong12", "password1111", "성춘향")  // 메소드 내부에서는 호출 불가
        return String.format(
                "id: %s\npassword: %s\nname: %s\nage: %d\ngender: %c\n",
                id, password, name, age, gender
        );
    }
}
