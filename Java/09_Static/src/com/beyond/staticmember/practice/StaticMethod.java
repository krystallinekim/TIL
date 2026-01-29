package com.beyond.staticmember.practice;

import java.util.Arrays;

public class StaticMethod {
    private static int num1 = 10;
    private static int num2 = 20;
    private int num3 = 30;

    // 1. 매개변수(X), 반환값(X)
    public static void method1() {
        System.out.println(num1 + num2++);
        // System.out.println(num1 + num3);  // static 메소드에서 일반 필드에 접근하면 에러 - 일반 필드는 객체가 생성되는 시점에 생성되기 때문
    };

    // 2. 매개변수(X), 반환값(O)
    public static int method2() {
        int num1 = 15;
        int num2 = 25;  // 일반 메소드와 똑같이 지역변수는 사용 가능
        return num1 + StaticMethod.num2 - num2;  // static 필드와 지역변수 이름이 겹치면 클래스명.필드명으로 구분해줌
        // return this.num1 + this.num2  // static 멤버에서는 this 사용 불가
    };

    // 3. 매개변수(O), 반환값(X)
    public static void method3(int i) {
        System.out.println(method2() + i);  // static 메소드 안에서 다른 static 메소드를 호출하는 것도 가능
        // System.out.println(method2() + i + sum(1));  // static 메소드 안에서 일반 메소드는 호출 불가
    };

    // 4. 매개변수(O), 반환값(O)
    public static int method4(int... numbers) {
        return Arrays.stream(numbers).sum();  // for문 안돌리고 쓰는법
    };

    private int sum(int a) {
        return num3 + a;
    };
}
