package com.beyond.threadcontrol.practice;

public class Storage {
    private int product;
    private boolean isEmpty = true;

    public Storage() {
    }

    public synchronized void getProduct() throws InterruptedException {
        // isEmpty = false(창고가 차있음)까지 기다림
        while (isEmpty) {
            wait();
        }

        System.out.printf("● 소비자가 %d번 상품을 소비함\n", this.product);
        // 소비했으니 isEmpty = true
        this.isEmpty = true;
        notify();
    }

    public synchronized void setProduct(int product) throws InterruptedException {
        // isEmpty = true(창고가 비어있음)일 때까지 기다림
        while (!isEmpty) {
            wait();
        }

        this.product = product;
        System.out.printf("○ 생산자가 %d번 상품을 생산함\n", this.product);
        // 생산했으니 isEmpty = false
        this.isEmpty = false;
        notify();  // wait에 의해 이릿정지된 스레드 중 하나를 실행 대기 상태로 만든다.
    }


}
