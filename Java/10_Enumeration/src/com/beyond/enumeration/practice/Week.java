package com.beyond.enumeration.practice;

public enum Week {
    MONDAY("월요일", "월", 1),     // 0
    TUESDAY("화요일", "화", 2),    // 1
    WEDNESDAY("수요일", "수", 3),  // 2
    THURSDAY("목요일", "목", 4),   // 3
    FRIDAY("금요일", "금", 5),     // 4
    SATURDAY("토요일", "토", 6),   // 5
    SUNDAY("일요일", "일", 7);     // 6

    // 필드 선언
    private final String fullName;
    private final String shortName;
    private final int value;

    // 생성자

    private Week(String fullName, String shortName, int value) {
        this.fullName = fullName;
        this.shortName = shortName;
        this.value = value;
    }

    public String getFullName() {
        return fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public int getValue() {
        return value;
    }
}
