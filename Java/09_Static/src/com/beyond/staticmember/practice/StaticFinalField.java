package com.beyond.staticmember.practice;

public class StaticFinalField {
    // final field
    private final String gender1 = "Male";  // 초기화되지 않으면 기본값으로 들어가는데, 값이 정해지면 더이상 변경되지 않으므로 의미없는 필드가 된다
    private final String gender2;

    // public StaticFinalField() {}; // final 필드의 값을 지정해주지 않는 생성자가 있으면 에러남
    public StaticFinalField(String gender2) {
        this.gender2 = gender2;
    }

    public String getGender2() {
        return gender2;
    } // Setter는 만들 수 없다 - 변경이 안되니까

    // static final field - 상수 선언
    public static final int MAX_LEVEL = 99;

}
