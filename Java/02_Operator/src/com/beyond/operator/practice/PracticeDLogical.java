package com.beyond.operator.practice;

import java.util.Scanner;

public class PracticeDLogical {
    public void method1() {
        System.out.println("정수값을 입력받아 1부터 100 사이의 정수인지 확인");
        int num;
        boolean result;

        Scanner scanner = new Scanner(System.in);

        System.out.print("숫자 입력: ");

        num = scanner.nextInt();

        result = (1 <= num && num <= 100);

        System.out.println(result);
    }
    public void method2(){
        System.out.println("단락 회로 평가, 단축평가");
        int num = 10;
        boolean res;

        // AND
        System.out.printf("&& 연산 전: %d\n", num);

        res = (num < 5) && (++num > 0);  // 뒤쪽 계산은 안하고 지나감
        // 10 < 5 == false / 11 > 0 == true -> false && true = false
        System.out.println("result: " + res);
        System.out.printf("&& 연산 후: %d\n", num);  // 10, ++num이 실행이 안됨
        System.out.println();

        // OR
        System.out.printf("|| 연산 전: %d\n", num);

        res = (num > 5) || (++num > 0);

        System.out.println("result: " + res);
        System.out.printf("&& 연산 후: %d\n", num);
    }
}
