package com.beyond.interf;

import com.beyond.interf.practice.Basic;
import com.beyond.interf.practice.Desktop;
import com.beyond.interf.practice.Product;
import com.beyond.interf.practice.SmartPhone;
import com.beyond.interf.practice.Television;

public class Application {
    public static void main(String[] args) {

        // new Basic();  // 직접 객체로 만들 수 없다.

        // 참조변수로는 사용이 가능
        Basic phone = new SmartPhone(
                "p0003", "아이폰 15 프로", "애플", 1600000, "KT"
        );

        phone.turnOn();
        phone.turnOff();
        System.out.println();

        Product phone1 = new SmartPhone(
                "p0004", "갤럭시 S25", "삼성", 1000000, "SKT"
        );

        phone1.turnOn();
        phone1.turnOff();
        System.out.println();

        // 다형성 적용도 가능하다
        Basic[] products = {
                new Desktop(),
                new SmartPhone(),
                new Television()
        };

        for (Basic product : products) {
            product.turnOn();
            System.out.println(product);
            product.turnOff();
            System.out.println();
        }
    }
}
