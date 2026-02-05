package com.beyond.thread.practice;

public class LoopThread extends Thread{

    @Override
    public void run() {
        // 작업 스레드에서 실행할 코드
        for (int i = 1; i <= 100; i++) {
            System.out.printf("%s[%d]\n", Thread.currentThread().getName(), i);
        }
        System.out.println("============== A 스레드 종료 ==============");
    }
}
