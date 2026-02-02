package com.beyond.exception.practice;

import java.io.IOException;

public class Throws {
    public void method() {
        System.out.println("method1 실행");

        method2();

        if (true) {
            throw new RuntimeException();
        }

        System.out.println("method1 종료");
    }

    private void method2() {
        System.out.println("method2 실행");

        try {
            method3();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        // 1. try-catch로 감싸기
        // 2. 호출한 데로 예외 던지기
        System.out.println("method2 종료");
    }

    // private void method3() throws Exception {  // 어떤 예외를 던지는지 명확하지 않아 이렇게는 잘 쓰지 않는다.
    private void method3() throws ClassNotFoundException, IOException {
        System.out.println("method3 실행");

        int random = (int) (Math.random() * 3 + 1);

        if (random == 1) {
            throw new ClassNotFoundException();  // 에러를 던지면 무조건 try-catch로 받아줘야 한다.
        } else if (random == 2) {
            throw new IOException();
        } else {
            System.out.println("정상 실행");
        }

        System.out.println("method3 종료");
    }

}
