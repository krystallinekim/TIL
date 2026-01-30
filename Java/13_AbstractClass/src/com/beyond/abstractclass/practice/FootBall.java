package com.beyond.abstractclass.practice;

public class FootBall extends Sports {

    public FootBall(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    @Override
    public void printRule() {
        System.out.printf("축구는 %d명이서 팀을 이뤄 공을 발을 이용해 골대에 넣는 게임\n", super.numberOfPlayers);
    }
}
