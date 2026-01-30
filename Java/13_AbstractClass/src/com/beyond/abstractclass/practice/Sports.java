package com.beyond.abstractclass.practice;

public abstract class Sports {
    protected int numberOfPlayers;  // 참여하는 사람 수

    // 생성자를 가질 수 있다
    public Sports(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    // 추상 메소드
    // 각 자식 클래스마다 메소드가 달라질건데, rule이라는 기능은 유지하고 싶을 때
    public abstract void printRule();  // 선언부만 작성하고, 내용은 나중에 자식 클래스에서 작성할 것

}
