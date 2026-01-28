package com.beyond.method.practice;

public class Method {
    // 1. 매개변수(X), 반환값(X)
    public void method1() {  // 반환값이 없으면 반환타입을 void로 사용함
        System.out.println("매개변수도 없고 반환값도 없는 메소드");
        // return;  // 쓸 수는 있는데, 뒤에 값을 보낼 수는 없다
        // System.out.println();  // 도달할 수 없는 구문은 에러를 띄운다
    }

    // 2. 매개변수(X), 반환값(O)
    public String method2() {
        return "매개변수는 없는데 반환값은 있는 메소드";  // null값도 가능함
    }

    // 3. 매개변수(O), 반환값(X)
    public void method3(int num1, int num2) {
        int sum = num1 + num2;
        System.out.println("매개변수는 있는데 반환값은 없는 메소드");
        System.out.printf("입력받은 매개변수 %d, %d의 합은 %d\n", num1, num2, sum);
    }

    // 4. 매개변수(O), 반환값(O)
    public int method4(int num1, int num2) {
        System.out.println("매개변수, 매개값 둘 다 있는 메소드");
        return num1 * num2;
    }

    // 5. 매개변수로 객체를 전달받는 메소드
    public void method5(User user, String id, String password, String name) {
        user.setId(id);
        user.setPassword(password);
        user.setName(name);
    }  // 객체를 받아서 작업할 수 있다. 이 때 객체는 메인에서 사용하는 객체와 같은 영역을 참조하고 있어, 여기서 수정하는 내용은 객체에 영향을 준다.

    // 6. 매개변수로 가변인자를 전달받는 메소드
    // 가변인자: 메소드 선언 시점에 매개변수가 몇개 들어올지 알 수 없는 경우

    // 가변인자가 없을 때
    public int method6(int[] numbers) {
        int sum = 0;

        for (int i : numbers){
            sum += i;
        }
        return sum;
    }

    // 가변인자 사용시
    public int method7(int... numbers) {
        int sum = 0;

        for (int i : numbers){
            sum += i;
        }
        return sum;
    }
}
