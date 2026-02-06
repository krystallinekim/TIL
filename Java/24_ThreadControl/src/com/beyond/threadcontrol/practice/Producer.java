package com.beyond.threadcontrol.practice;

public class Producer extends Thread {
    private final Storage storage;

    public Producer(Storage storage) {
        this.storage = storage;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10 ; i++) {
            try {
                storage.setProduct(i);
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }
}
