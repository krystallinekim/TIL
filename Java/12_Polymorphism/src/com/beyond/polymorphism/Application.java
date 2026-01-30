package com.beyond.polymorphism;

import com.beyond.polymorphism.practice.Desktop;
import com.beyond.polymorphism.practice.Product;
import com.beyond.polymorphism.practice.SmartPhone;
import com.beyond.polymorphism.practice.Television;

import java.util.Arrays;

public class Application {
    public static void main(String[] args) {

        // 1. 부모타입의 참조변수로 부모 객체를 다룸
        Product product = new Product();
        System.out.println(product);  // product 참조변수로 Product 클래스의 멤버만 접근 가능
        System.out.println();

        // 2. 자식 타입의 참조변수로 자식 객체를 다룸
        Desktop desktop = new Desktop("p0001", "삼성 데스크탑", "삼성", 1600000, false);
        // toString, isAllInOne, setAllInOne은 자식 클래스에서, 나머지는 부모 클래스(Product, Object)에서 온 메소드
        System.out.println(desktop);  // desktop 참조변수로 Desktop, Product 클래스의 멤버에 접근 가능
                                      // toString()은 Desktop의 toString
        System.out.println();

        // 3. 부모 타입의 참조변수로 자식 타입의 객체를 다루는 경우(업캐스팅)
        // 객체는 자식 타입인데, 그걸 확인할 참조변수는 부모 타입이기 때문임
        Product television = new Television("p0002", "벽걸이 TV", "LG", 700000, 50);
        System.out.println(television);  // television 참조변수로 Product 클래스의 멤버에만 접근 가능
                                         // Television의 메소드(setSize, getSize)는 접근 불가
                                         // 단, 실제 객체가 Television 타입이므로 오버라이드된 메소드는 자식 타입의 것을 사용
        System.out.println();

        // 다운캐스팅
        System.out.println(((Television) television).getSize()); // 다시 Television 클래스의 멤버에 접근하고 싶다면, 형변환이 필요
        System.out.println();

        // 4, 배열 - 다형성
        // 4-1. 다형성 이전
        Desktop[] desktops = new Desktop[2];
        desktops[0] = new Desktop("p0001", "삼성 데스크탑", "삼성", 1600000, false);
        desktops[1] = new Desktop("p0002", "아이맥 24인치", "애플", 2000000, true);

        SmartPhone[] smartPhones = {
                new SmartPhone("p0003", "아이폰 15 프로", "애플", 1600000, "KT"),
                new SmartPhone("p0004", "갤럭시 S25", "삼성", 1000000, "SKT")
        };

        System.out.println(Arrays.toString(desktops));
        System.out.println(Arrays.toString(smartPhones));
    }
}
