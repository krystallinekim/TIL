package com.beyond.field.practice;

public class User {

    public String id = "Hong123";   // 어디서나
    protected String address;       // 같은 패키지 + 자식 클래스
    String name = "홍길동";          // 같은 패키지
    private String password;        // 선언된 클래스에서만
    
    // 메소드 선언
    public void test(int value) {
        /*private*/int number;  // 지역변수 -> 접근제한자 X

        number = 20;  // 사용 전 초기화해야함
        System.out.println(number);

        System.out.println(password);  // 초기화 안해도 사용 가능, 클래스 전역에서 사용 가능함

        password = "123456789";

        System.out.println(password);

        System.out.println(value);  // 매개변수는 항상 전달받아야 하기 때문에 초기화 없이도 사용 가능

    }

}
