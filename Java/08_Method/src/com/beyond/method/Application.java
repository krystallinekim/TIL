package com.beyond.method;

import com.beyond.method.practice.Method;
import com.beyond.method.practice.User;

public class Application {
    public static void main(String[] args) {
        Method method = new Method();

        // 1. 매개변수(X), 반환값(X)
        method.method1();
        System.out.println();

        // 2. 매개변수(X), 반환값(O)
        String msg = method.method2();  // 반환값을 변수에 담아서 이용 가능함
        System.out.println(msg);
        System.out.println();

        // 3. 매개변수(O), 반환값(X)
        method.method3(10, 20);
        System.out.println();

        // 4. 매개변수(O), 반환값(O)
        int result = method.method4(3, 4);
        System.out.println(result);
        System.out.println();

        // 5. 매개변수로 객체를 전달받는 메소드
        User user1 = new User("Hong123", "password1234", "홍길동");
        System.out.println(user1.information());

        method.method5(user1, "Lee123", "password1111", "이몽룡");
        System.out.println(user1.information());  // user1 객체의 필드가 변경됨
        System.out.println();

        // 6. 매개변수로 가변인자를 전달받는 메소드

        // 원래 방법은 배열로 인자 여러개를 전달받는 방식
        System.out.println(method.method6(new int[]{}));
        System.out.println(method.method6(new int[]{1, 2, 3}));
        System.out.println(method.method6(new int[]{1, 2, 3, 4, 5}));

        System.out.println();
        // 매개변수 사용 시 표현이 간단해짐
        System.out.println(method.method7());
        System.out.println(method.method7(1,2,3));
        System.out.println(method.method7(1,2,3,4,5));
        System.out.println();


    }
}
