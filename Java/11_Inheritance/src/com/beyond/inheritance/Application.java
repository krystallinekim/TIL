package com.beyond.inheritance;

import com.beyond.inheritance.practice.Desktop;

public class Application {
    public static void main(String[] args) {

        // 기본 생성자 사용 - 부모 생성자 호출 > 자식 생성자 호출 순서
        Desktop desktop1 = new Desktop();

        desktop1.setCode("A1111");
        desktop1.setName("아이맥 24인치");
        desktop1.setBrand("애플");
        desktop1.setPrice(2000000);
        desktop1.setAllInOne(true);

        System.out.print(desktop1.information());
        System.out.println(desktop1.isAllInOne());
        System.out.println();

        // 매개변수가 있는 생성자 사용
        Desktop desktop2 = new Desktop("S0001", "삼성 데스크탑", "삼성", 1600000, false);

        System.out.print(desktop2.information());
        System.out.println(desktop2.isAllInOne());
    }
}
