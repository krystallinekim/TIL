package com.beyond.api.practice;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTime {

    // java.util.Date 클래스
    public void method1() {
        System.out.println("java.util.Date");
        // Date 객체 생성
        Date today = new Date();
        System.out.println(today);  // Tue Feb 03 12:03:58 KST 2026

        // Date when = new Date(1770100000000L);
        Date when = new Date(126, 1, 3);
        System.out.println(when);  // Wed Mar 03 00:00:00 KST 3926

        // 날짜, 시간정보 출력
        System.out.println(today.getTime());  // 시각을 밀리초 단위로 표현
        System.out.println(today.getYear());  // 연도에서 1900을 뺀 값(1900부터 셈)
        System.out.println(today.getYear() + 1900);
        System.out.println(today.getMonth());  // 달에서 1을 뺀 값(0부터 셈)
        System.out.println(today.getMonth() + 1);
        System.out.println(today.getDate());  // 현재 날짜
        System.out.println(today.getHours());  // 시간정보
        System.out.println(today.getMinutes());  // 분정보
        System.out.println(today.getSeconds());  // 초정보

        // SimpleDateFormat 클래스

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일(E) HH시 mm분 ss초");
        System.out.println(sdf.format(today));


    }

    // java.time 패키지
    public void method2() {
        System.out.println("java.time");

        // java.time.LocalDateTime
        // LocalDateTime now = new LocalDateTime();  // 생성자의 접근제한이 private이다
        LocalDateTime now = LocalDateTime.now();  // 제공되는 정적 메소드를 이용해서 만들어야 함
        System.out.println(now);

        ZonedDateTime now2 = ZonedDateTime.now(ZoneId.of("UTC"));
        System.out.println(now2);

        ZonedDateTime now3 = ZonedDateTime.now(ZoneId.of("America/New_York"));
        System.out.println(now3);

        LocalDateTime when = LocalDateTime.of(2026, 2, 3, 15, 8, 30);
        System.out.println(when);

        LocalDateTime when2 = LocalDateTime.parse("2025-12-31T12:30:11");
        System.out.println(when2);


        // 날짜, 시간 정보 출력
        System.out.println(now.getYear());
        System.out.println(now.getMonth());
        System.out.println(now.getMonthValue());
        System.out.println(now.getDayOfYear());
        System.out.println(now.getDayOfMonth());
        System.out.println(now.getDayOfWeek());

        // 날짜, 시간 조작
        System.out.println(now.minusYears(3));
        System.out.println(now.plusYears(2).minusDays(3).plusMonths(4).minusWeeks(5));

        System.out.println(now.isAfter(now.plusDays(3)));
        System.out.println(now.isEqual(now.plusDays(3)));
        System.out.println(now.isBefore(now.plusDays(3)));


        // java.time.LocalDate
        LocalDate today = LocalDate.now();
        System.out.println(today);

        now.toLocalDate();
        // java.time.LocalTime

        LocalTime timeNow = LocalTime.now();
        System.out.println(timeNow);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd(E) HH:mm:ss");
        System.out.println(formatter.format(now));
        System.out.println(now.format(formatter));

        System.out.println(now.format(DateTimeFormatter.BASIC_ISO_DATE));
        System.out.println(LocalDate.parse("2025-W20-3", DateTimeFormatter.ISO_WEEK_DATE));
    }
}
