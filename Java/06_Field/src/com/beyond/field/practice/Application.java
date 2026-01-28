package com.beyond.field.practice;

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
        System.out.println(user.address);  // 같은 패키지 내부가 되었으므로 이제 접근 가능해짐

        user.address = "서울시";

        System.out.println(user.address);
        System.out.println();

        // 3. default
        // 동일 패키지 내에서만 접근 가능
        System.out.println(user.name);

        user.name = "홍XX";

        System.out.println(user.name);
        System.out.println();

        // 4. private
        // 선언된 클래스에서만 접근 가능
        // System.out.println(user.password);  // 여기서도 실행 불가
    }
}
