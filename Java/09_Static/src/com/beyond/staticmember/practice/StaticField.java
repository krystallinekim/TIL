package com.beyond.staticmember.practice;

public class StaticField {
    // static 필드
    // 일반 필드 - 객체를 만든 뒤 사용
    // static 필드 - 클래스가 로드될 때 생성되고 프로그램이 종료되면 소멸
    public static int num = 1;  // IntelliJ 에서 이탤릭체로 표시해줌
    private static String msg = "Hello World";

    // public String getMsg() {  // 일반 메소드로 만들면 객체를 따로 만들어서 사용해야 한다
    public static String getMsg() {  // static 필드에 접근하는 getter, setter는 똑같이 static 메소드로 만들어서 쓴다
        return msg;
    }

    public static void setMsg(String msg) {
        StaticField.msg = msg;
    }


}
