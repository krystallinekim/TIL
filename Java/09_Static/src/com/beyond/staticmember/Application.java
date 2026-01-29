package com.beyond.staticmember;

import com.beyond.staticmember.practice.StaticField;
import com.beyond.staticmember.practice.StaticFinalField;
import com.beyond.staticmember.practice.StaticMethod;

public class Application {
    public static void main(String[] args) {

        // static 필드 생성 및 출력
        System.out.println("static 필드 생성 및 출력");
        System.out.println(StaticField.num);  // 따로 객체를 생성하지 않아도 접근 가능
        System.out.println();

        // 객체의 static 필드 공유
        StaticField a = new StaticField();
        StaticField b = new StaticField();

        System.out.println(a.num);
        System.out.println(b.num);  // 객체 참조로 접근하는거보다는 클래스에서 접근하는게 좋다

        StaticField.num++;

        System.out.println(a.num);
        System.out.println(b.num);  // public이라 외부에서 수정도 가능하고, 수정 시 모든 참조에서 같이 바뀜

        System.out.println();

        // static 필드의 getter, setter
        // StaticField.msg;  // private static 필드는 외부 접근 불가 -> 간접 접근이 가능한 Getter, Setter 필요
        System.out.println(StaticField.getMsg());

        StaticField.setMsg("Static Field");

        System.out.println(StaticField.getMsg());
        System.out.println();

        // static 메소드
        System.out.println("static 메소드");
        StaticMethod.method1();  // StaticMethod.num1(10) + StaticMethod.num2(20), StaticMethod.num2++
        StaticMethod.method1();  // StaticMethod.num1(10) + StaticMethod.num2(21), StaticMethod.num2++

        System.out.println(StaticMethod.method2());  // num1(15) + StaticMethod.num2(22) - num2(15)

        StaticMethod.method3(18);  // method2(12) + i(18)

        System.out.println(StaticMethod.method4(4,5,15,25,30));
        System.out.println();

        // final 필드
        StaticFinalField finalField = new StaticFinalField("Female");
        System.out.println(finalField.getGender2());
        System.out.println();

        // 상수
        System.out.println(StaticFinalField.MAX_LEVEL);
        System.out.println(Math.PI);
        System.out.println(Math.E);

        // Math.PI = 3; final 필드에 변경 안됨

        // 기타 상수들
        System.out.printf("int의 범위: %d ~ %d", Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
}
