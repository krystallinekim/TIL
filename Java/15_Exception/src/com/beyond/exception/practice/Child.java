package com.beyond.exception.practice;

import java.io.IOException;

public class Child extends Parent{

    // 오버라이딩 시 꼭 예외를 throw 하지 않아도 된다.
    // @Override
    // public void method() {}

    // 부모와 동일하게 throw하는건 괜찮다
    // @Override
    // public void method() throws IOException {}


    // 부모보다 하위 클래스의 예외를 throw하는건 가능
    // @Override
    // public void method() throws FileNotFoundException, EOFException, SocketException {}

    // 부모 예외의 상위 클래스 예외 throw는 불가
    // @Override
    // public void method() throws Exception {}
}
