package com.beyond.control.practice;

public class For {
    public void method1() {
        System.out.println("1부터 10까지의 정수의 합계");

        int sum = 0;

        for (int i = 1; i <= 10; i += 1) {
            sum += i;
        }

        System.out.printf("합계 = %d", sum);
    }

    public void method2() {
        System.out.println("1부터 랜덤 값(1~10 사이)까지의 합계를 출력");

        int sum = 0;
        // Math는 java의 기본 메소드로, import 없이 쓸 수 있음
        // System.out.println((int) (Math.random() * 10 + 1));  // 1 ~ 10까지의 랜덤 정수
        int random = (int) (Math.random() * 10 + 1);  // random: 0.0 <= 랜덤값 < 1.0 인 실수를 반환

        for (int i = 1; i <= random ; i++) {
            sum += i;
        }

        System.out.printf("1 ~ %d까지의 합 : %d", random, sum);
    }
    public void practice1() {
        System.out.println("구구단(2~9)");

        for (int i = 2; i < 10; i++) {
            System.out.printf("\n=== %d단 ===\n", i);
            for (int j = 1; j < 10 ; j++) {
                System.out.printf("%d X %d = %d\n", i, j, i*j);
            }
        }
    }
}

