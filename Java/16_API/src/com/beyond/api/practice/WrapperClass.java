package com.beyond.api.practice;

public class WrapperClass {
    // Wrapper 클래스
    public void method1() {
        // Boxing
        // 1. Wrapper 클래스 객체를 직접 생성

        Integer i1 = new Integer(10);
        Double d1 = new Double(3.14);
        Double d11 = new Double("3.14");
        Character c1 = new Character('A');

        System.out.println(i1);
        System.out.println(d1);
        System.out.println(d11);
        System.out.println(c1);


        // 2. Wrapper 클래스의 static 메소드를 통해 생성

        Integer i2 = Integer.valueOf(10);
        Double d2 = Double.valueOf(3.14);
        Double d21 = Double.valueOf("3.14");
        Character c2 = Character.valueOf('A');

        System.out.println(i2);
        System.out.println(d2);
        System.out.println(d21);
        System.out.println(c2);

        // 3. Auto Boxing
        Integer i3 = 10;
        Double d3 = 3.14;
        Character c3 = 'A';

        System.out.println(i3);
        System.out.println(d3);
        System.out.println(c3);

        // 4. 객체로 나와서 메소드 사용 가능
        System.out.println(d2 == d21);          // false
        System.out.println(d2.equals(d21));     // true
        System.out.println(d2.compareTo(d21));  // 0

        // Unboxing

        // 1. Wrapper 객체의 메소드 이용
        int num1 = i1.intValue();
        double dnum1 = d1.doubleValue();
        double dnum11 = d11.doubleValue();
        char ch1 = c1.charValue();

        System.out.println(num1);
        System.out.println(dnum1);
        System.out.println(dnum11);
        System.out.println(ch1);

        System.out.println(dnum11 == dnum1);

        // 2. Auto Unboxing
        int num2 = i2;
        double dnum2 = d2;
        double dnum21 = d21;
        char ch2 = c2;

        System.out.println(num2);
        System.out.println(dnum2);
        System.out.println(dnum21);
        System.out.println(ch2);

    }

    // String - Wrapper 클래스
    public void method2() {
        // 문자열 -> 기본 자료형
        // int number = "10";  // 변환이 안되서 대입이 안됨
        int inum = Integer.parseInt("10");
        double dnum = Double.parseDouble("3.14");

        // 기본 자료형 -> 문자열
        // String str1 = 10;  // 변환 불가
        String str1 = String.valueOf(10);
        String str2 = String.valueOf(3.14);

        // Wrapper에도 valueOf가 있다
        String str3 = Integer.valueOf(10).toString();
        String str4 = Double.valueOf(3.14).toString();
    }
}
