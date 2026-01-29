package com.beyond.staticmember.practice;

/*
1. 일반 필드
    1) 생략 - 기본값으로 초기화
    2) 명시적 초기화
    3) 인스턴스 블록
    4) 생성자를 이용한 초기화

2. static 필드 (생성자 사용 불가)
    1) 생략 - 기본값으로 초기화
    2) 명시적 초기화
    3) static 블록

 */
public class InitBlock {
    // 1-1. 기본값 - null
    // private String name;

    // 1-2. 명시적 초기화
    private String name = "홍길동";

    // 1-3. 초기화 블록을 이용
    {
        name = "이몽룡";
    }
    // 1-4. 생성자를 이용한 초기화

    public InitBlock() {
        this.name = "임꺽정";
    }

    // 2-1. 기본값
    // private static int age; // 0

    // 2-2. 명시적
    private static int age = 10;

    // 2-3. static 블록
    static {
        age = 20;
    }


    // getter
    public String getName() {
        return name;
    }

    public static int getAge() {
        return age;
    }
}
