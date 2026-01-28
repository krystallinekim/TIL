package com.beyond.method.practice;

public class Overloading {
    public void test() {
        System.out.println("빈 값");
    }
    public void test(int a) {
        System.out.println(a * a);
    }
    public void test(String s) {
        System.out.println(s + ": String");
    }
    public void test(int a, String s) {
        System.out.println(a + s);
    }
    public void test(int a, int b) {
        System.out.println(a * b);
    }
    public void test(String s, int a) {
        System.out.println(s.repeat(a));
    }
    // public void test(int c, int d) {}  // 매개변수의 변수명만 다른 것은 호출 시 어떤 메소드를 선택해야하는지 구분이 안된다.
    // private void test(int a) {}; // 외부에선 어차피 접근 못해서 상관없지만, 내부에서는 구분이 안된다
    // public String test(String s) {return ""}  // 반환형이 다른 것도 구분 불가능
}
