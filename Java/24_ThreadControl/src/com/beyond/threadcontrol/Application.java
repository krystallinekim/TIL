package com.beyond.threadcontrol;

import com.beyond.threadcontrol.practice.Consumer;
import com.beyond.threadcontrol.practice.Producer;
import com.beyond.threadcontrol.practice.Storage;


public class Application {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("============== 메인 스레드 시작 ==============");
        Storage storage = new Storage();

        Producer producer = new Producer(storage);
        Thread consumer = new Thread(new Consumer(storage));

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        System.out.println("============== 메인 스레드 종료 ==============");
    }
}
