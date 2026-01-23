package com.beyond.operator.practice;

public class Assignment {
    public void method1() {
        System.out.println("복합 대입 연산자");
        int num = 10;
        System.out.println("num = " + num);

        num += 3;  // num = num + 3;
        System.out.println("num += 3 -> " + num);  // 13

        num -= 5;  // num = num - 5;
        System.out.println("num -= 5 -> " + num);  // 8

        num *= 6;  // num = num * 6;
        System.out.println("num *= 6 -> " + num);  // 48

        num /= 3;  // num = num / 3;
        System.out.println("num /= 3 -> " + num);  // 16

        num %= 7;  // num = num % 7;
        System.out.println("num %= 7 -> " + num);  // 2

        // String에서도 문자열 연결 연산자로 이용 가능
        System.out.println();
        String str = "Hello";

        str += "World";

        System.out.println(str);







    }
}
