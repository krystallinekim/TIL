package com.beyond.thread;

import com.beyond.thread.practice.LoopRunnable;
import com.beyond.thread.practice.LoopThread;

public class Application {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("============== 메인 스레드 시작 ==============");

        System.out.printf("%s\n", Thread.currentThread().getName());

        // 스레드 생성
        // 1. Thread 클래스 상속
        LoopThread loopThread = new LoopThread();

        loopThread.setName("A Thread");
        loopThread.setPriority(1);
        // loopThread.run();  // 이러면 메인 스레드가 가서 작업 스레드를 실행하고 돌아옴

        // 2. Runnable 인터페이스 구현
        Thread runnableThread = new Thread(new LoopRunnable());  // Runnable을 구현한 객체를 전달하면 된다.

        runnableThread.setName("B Thread");
        runnableThread.setPriority(3);

        // 3. 익명 구현 개체로 Runnable 인터페이스를 구현
        Thread anonymousThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i < 100; i++) {
                    System.out.printf("%s[%d]\n", Thread.currentThread().getName(), i);
                }
                System.out.println("============== C 스레드 종료 ==============");
            }
        });

        anonymousThread.setName("C Thread");
        anonymousThread.setPriority(Thread.NORM_PRIORITY);

        // 4. 람다식으로 Runnable 인터페이스와 구현 객체를 생성
        Thread lambdaThread = new Thread(() -> {
            for (int i = 1; i <= 100; i++) {
                System.out.printf("%s[%d]\n", Thread.currentThread().getName(), i);
            }
            System.out.println("============== D 스레드 종료 ==============");
        });

        lambdaThread.setName("D Thread");
        lambdaThread.setDaemon(true);
        lambdaThread.setPriority(10);

        // for (int i = 1; i <= 100; i++) {
        //     System.out.printf("%s[%d]\n", Thread.currentThread().getName(), i);
        // }

        loopThread.start();
        anonymousThread.start();
        runnableThread.start();
        lambdaThread.start();

        loopThread.join();
        runnableThread.join();
        anonymousThread.join();
        // .join()은 메인이 나머지 스레드를 기다리도록 설정함
        // 모든 스레드가 끝나야 메인 스레드를 종료시킬수 있음

        System.out.println("============== 메인 스레드 종료 ==============");
    }
}
