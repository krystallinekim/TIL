package com.beyond.field;

import com.beyond.field.practice.User;

public class Application {
    public static void main(String[] args) {
        User user = new User();

        // 접근제한자 test

        // 1. public
        // public은 어디서나 바로 접근 가능
        System.out.println(user.id);

        user.id = "Hong1234";

        System.out.println(user.id);  // 수정도 가능함
        System.out.println();

        // 2. protected
        // 동일 패키지 or 상속 관계인 자식 클래스에서 접근 가능
        // System.out.println(user.address);  // 다른 패키지니까 직접 접근 불가(에러)

        // 3. default
        // 동일 패키지 내에서만 접근 가능
        // System.out.println(user.name);

        // 4. private
        // 선언된 클래스에서만 접근 가능
        // System.out.println(user.password);



        // 변수 구분
        // 1. 필드 (멤버 변수, 인스턴스 변수)
        // - 클래스 영역에 선언하는 변수로, 클래스 내에선 어디서나 사용 가능
        // - 필드는 접근제한자를 사용할 수 있다
        // - new 연산자를 통해 객세(인스턴스) 생성 시 메모리(Heap)에 생성되고, 객체 소멸 시 함께 소멸

        // 2. 지역변수
        // - 메소드, 생성자, 제어문 등에 선언 > 해당 구역에서만 사용 가능함
        // - 접근제한자는 사용 불가
        // - 메소드, 생성자, 제어문 실행 시 메모리에 생성, 실행이 종료되면 같이 소멸

        user.test(10);
    }
}
