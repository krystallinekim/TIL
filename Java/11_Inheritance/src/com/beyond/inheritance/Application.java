package com.beyond.inheritance;

import com.beyond.inheritance.practice.Book;
import com.beyond.inheritance.practice.Desktop;
import com.beyond.inheritance.practice.SmartPhone;
import com.beyond.inheritance.practice.Television;

public class Application {
    public static void main(String[] args) {

        // 기본 생성자 사용 - 부모 생성자 호출 > 자식 생성자 호출 순서
        Desktop desktop1 = new Desktop();

        desktop1.setCode("p0001");
        desktop1.setName("아이맥 24인치");
        desktop1.setBrand("애플");
        desktop1.setPrice(2000000);
        desktop1.setAllInOne(true);

        // 매개변수가 있는 생성자 사용
        Desktop desktop2 = new Desktop("p0002", "삼성 데스크탑", "삼성", 1600000, false);

        // Smartphone 객체 생성
        SmartPhone smartPhone1 = new SmartPhone(
                "p0003", "아이폰 15 프로", "애플", 1600000, "KT"
        );
        SmartPhone smartPhone2 = new SmartPhone(
                "p0004", "갤럭시 S25", "삼성", 1000000, "SKT"
        );

        // Television 객체 생성
        Television television1 = new Television(
                "p0005", "벽걸이 TV", "LG", 700000, 50
        );

        System.out.println(desktop1.information());
        System.out.println(desktop2.information());
        System.out.println(smartPhone1.information());
        System.out.println(smartPhone2.information());
        System.out.println(television1.information());


        // Object 클래스의 메소드 오버라이딩
        Book book1 = new Book("자바의 정석", "남궁성", 36000);
        Book book2 = new Book("자바의 정석", "남궁성", 36000);
        Book book3 = new Book("혼자 공부하는 자바", "신용권", 28000);

        System.out.println(book1.information());
        System.out.println(book2.information());
        System.out.println(book3.information());


        // 1, toString()
        System.out.println(book1);
        System.out.println(book2);
        System.out.println(book3);
        System.out.println();

        // 2. equals()
        System.out.println(book1 == book2);
        System.out.println(book1.equals(book2));
        System.out.println(book1.equals(book3));
        System.out.println();

        System.out.println(book1.hashCode());
        System.out.println(book2.hashCode());
        System.out.println(book3.hashCode());
    }
}
