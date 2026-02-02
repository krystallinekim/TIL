package com.beyond.exception.practice;

public class TryCatchFinally {
    public void method1() {

        try {
            // 예외 발생 가능성이 있는 코드
            System.out.println("\n- 에러 발생 가능성이 있는 코드");
            int i = 10 / 0;
            System.out.println(i);
            // throw new ArithmeticException();
            // throw new ClassCastException();
        } catch (ArithmeticException | ClassCastException e) {
            System.out.println("Arithmetic");
        } catch (Exception e) {  // e에서 참조변수로 에러 객체를 받아옴. Exception은 예외 클래스 중 최상위
            // 예외 발생 시 처리 코드
            System.out.println("\n- 에러 발생 시 처리할 코드");
            System.out.println(e);
            // .getMessage(): 예외 내용

        } finally {
            // 예외 발생 여부와 상관없이 실행해야 하는 코드(생략 가능)
            System.out.println("\n- 예외 발생 여부와 상관없이 실행해야 하는 코드");

        }

    }
}
