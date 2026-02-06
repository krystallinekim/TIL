package com.beyond.lambda.practice.functional;

// 어노테이션이 있으면 확실하게 선언하는 용도
// 추상 메소드가 2개부터는 에러를 띄워준다.
@FunctionalInterface
public interface FunctionalA {
    void execute();  // 추상 메소드가 하나만 있는 메소드 = 함수적 인터페이스

    // void otherMethod();  // 2개가 붙으면 그냥 인터페이스임
}
