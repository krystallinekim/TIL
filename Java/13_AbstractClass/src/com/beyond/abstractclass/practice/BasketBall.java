package com.beyond.abstractclass.practice;

public class BasketBall extends Sports{

    public BasketBall(int numberOfPlayers) {
        super(numberOfPlayers);
    }

    @Override
    public void printRule() {
        System.out.printf("농구는 %d명이서 팀을 이뤄 공을 던져서 링에 넣는 게임\n", super.numberOfPlayers);
    }


}
