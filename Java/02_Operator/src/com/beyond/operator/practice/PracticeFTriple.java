package com.beyond.operator.practice;

import java.util.Scanner;

public class PracticeFTriple {
    public void method1() {
        // 삼항연산자
        System.out.println("사용자에게 입력받은 정수가 양수인지 음수인지 판단하기");
        int num;
        String res;

        Scanner scanner = new Scanner(System.in);

        System.out.print("0을 제외한 정수: ");
        num = scanner.nextInt();

        res = (num > 0) ? ("양수") : ("음수");  // `?` 앞이 true일 경우 res = `:` 앞의 값, false일 경우 res = `:` 뒤의 값

        System.out.printf("%d은(는) %s입니다.\n", num, res);  // 0일경우 문제


        // 삼항연산자의 중첩
        System.out.print("정수: ");
        num = scanner.nextInt();

        res = (num == 0) ? ("0") : ((num > 0) ? ("양수") : ("음수"));  // num == 0이면 0, num != 0이면 뒤의 삼항연산자의 결과값
        System.out.printf("%d은(는) %s입니다.\n", num, res);

        // 복잡하게 생겨서 두번 이상 중첩하지 않고 그냥 if문 씀

    }
}
