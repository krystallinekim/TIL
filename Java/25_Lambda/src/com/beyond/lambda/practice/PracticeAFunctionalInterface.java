package com.beyond.lambda.practice;

import com.beyond.lambda.practice.functional.FunctionalA;
import com.beyond.lambda.practice.functional.FunctionalB;
import com.beyond.lambda.practice.functional.FunctionalC;

public class PracticeAFunctionalInterface {
    public void method1() {
        FunctionalA fi = () -> System.out.println("매개변수도, 반환값도 없는 람다식");   // 이건 메소드를 재정의하는 코드
        fi.execute();  // 구현객체를 만들기만 했으므로 execute를 해줘서 코드 실행
    }

    public void method2() {
        FunctionalB fi = a -> System.out.printf("매개변수(%d)는 있고, 반환값은 없는 람다식\n", a);
        fi.execute(10);
    }

    public void method3() {
        FunctionalC fi = (a, b) -> {
            System.out.printf("매개변수가 (%d, %d)로 2개 있고, 반환값도 있는 람다식\n", a, b);
            return a + b;
        };
        System.out.println(fi.execute(2, 3));;
    }

    private int number = 100;

    public void method4(int arg) {
        int number = 200;
        FunctionalA fi;
        fi = () -> {
            // arg = 600;      // 변수값을 읽어오는 건 가능하다.
            // number = 500;   // 람다식 안에서든 밖에서든 지역변수의 값이 바뀌면 안된다
            // this.number = 400;

            System.out.println(arg);            // 300, 매개변수
            System.out.println(number);         // 200, 지역변수
            System.out.println(this.number);    // 100, this는 람다식이 실행하는 객체(pfi)의 참조 -> 필드값
        };
        fi.execute();
        System.out.println();

        fi = new FunctionalA() {
            int number = 400;

            @Override
            public void execute() {

                System.out.println(arg);            // 300, 매개변수
                System.out.println(number);         // 200, 지역변수
                System.out.println(this.number);    // 400, 익명구현객체에서 this는 객체 fi의 참조
            }
        };
        fi.execute();


    }

}
