package com.beyond.exception.practice;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RuntimeExceptionError {

    public void method1() {

        // Runtime Exception들은 코드 수정으로 문제가 해결되는 예외들

        int size;
        int[] numbers;
        Scanner scanner = new Scanner(System.in);

        System.out.print("배열 길이 > ");

        // 그냥 try-catch 쓸 수 있다
        try {
            size = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("정수값을 입력해 주세요");
            return;
        }

        // 1. size가 음수일 경우 - NegativeArraySizeException
        // 조건문으로 해결
        if (size < 0) {
            System.out.println("0보다 큰 값을 입력해 주세요");
            return;
        }

        numbers = new int[size];

        // 2. i = numbers.length에 도달할 경우 - ArrayIndexOutOfBoundsException
        // 코드 수정으로 해결
        // for (int i = 0; i <= numbers.length; i++) {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i;
            System.out.println(numbers[i]);
        }


    }
}


