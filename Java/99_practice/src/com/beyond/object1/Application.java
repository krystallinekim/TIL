package com.beyond.object1;

import com.beyond.object1.practice.Product;

public class Application {
    public static void main(String[] args) {

        Product product1 = new Product("ssgnote0", "갤럭시노트9", "경기도 수원", 960000, 10.0);
        Product product2 = new Product("lgxnote5", "LG스마트폰5", "경기도 평택", 780000, 0.7);
        Product product3 = new Product("ktsnote3", "KT스마트폰3", "서울시 강남", 250000, 0.3);

        product1.information();
        product2.information();
        product3.information();

        System.out.println("=========================================================");

        product1.setPrice(1200000);
        product2.setPrice(1200000);
        product3.setPrice(1200000);

        product1.setTax(0.05);
        product2.setTax(0.05);
        product3.setTax(0.05);

        product1.information();
        product2.information();
        product3.information();

        System.out.println("=========================================================");

        System.out.println("상품명 = " + product1.getName());
        System.out.println("부가세 포함 가격 = " + (int) (product1.getPrice() * (1 + product1.getTax())));

        System.out.println("상품명 = " + product2.getName());
        System.out.println("부가세 포함 가격 = " + (int) (product2.getPrice() * (1 + product2.getTax())));

        System.out.println("상품명 = " + product3.getName());
        System.out.println("부가세 포함 가격 = " + (int) (product3.getPrice() * (1 + product3.getTax())));
    }
}
