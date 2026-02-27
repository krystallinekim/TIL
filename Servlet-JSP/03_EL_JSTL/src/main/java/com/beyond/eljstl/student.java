package com.beyond.eljstl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor  // 모든 필드 초기화 생성자
@NoArgsConstructor  // 기본 생성자
@Data  // Getter, Setter, toString, equals, 기본생성자를 한번에 생성
//@ToString  // toString만 생성
public class student {

    private String name;
    private int age;
    private int math;
    private int english;
}
