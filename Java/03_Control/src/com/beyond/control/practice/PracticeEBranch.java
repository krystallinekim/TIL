package com.beyond.control.practice;

public class PracticeEBranch {
    public void method1() {
        System.out.println("1부터 랜덤 값(1~10 사이)까지의 합계를 반복문을 통해 출력, 단 랜덤값이 5가 나오면 종료");

        int cnt = 0;
        int sum;
        int random;

        while (true) {
            cnt += 1;
            sum = 0;
            random = (int) (Math.random() * 10 + 1);

            for (int i = 0; i <= random; i++) {
                sum += i;
            }
            System.out.printf("%d - 랜덤값: %d, 합 : %d\n", cnt, random, sum);
            if (random == 5) {
                break;
            }
        }
    }

    public void method2() {
        System.out.println("구구단 출력 - 단, 홀수단은 빼고");

        for (int i = 2; i < 10; i++) {
            if (i % 2 == 1) {
                continue;
            }

            System.out.printf("\n=== %d단 ===\n", i);

            for (int j = 1; j < 10 ; j++) {
                System.out.printf("%d X %d = %d\n", i, j, i*j);
            }
        }
    }
}
