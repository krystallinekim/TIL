package com.beyond.interf.practice;

public interface Basic {
    public static final int NUMBER = 10;  // 일반 필드를 생성하면, public static final이 생략된 상수 필드로 인식
    String NAME = "aaa";

    //  public Basic() {}  // 생성자 생성 불가

    // public abstract void method1();  // 일반 메소드도 생성 불가 - 추상 메소드로만 생성
    // public static void method2() {}
    // public default void method3() {}

    // 공통으로 구현해야 하는 기능은 인터페이스로 빼서 선언
    public abstract void turnOn();
    public abstract void turnOff(); 
}
