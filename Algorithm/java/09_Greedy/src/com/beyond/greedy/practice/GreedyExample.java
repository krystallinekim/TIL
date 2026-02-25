package com.beyond.greedy.practice;

import java.util.Arrays;
import java.util.Comparator;

public class GreedyExample {
    public static int minCoins(int amount, int[] coins) {
        int count = 0;

        Arrays.sort(coins);

        for (int i = coins.length - 1; i >= 0 ; i--) {
            int sum = 0;

            while(amount >= coins[i]) {
                amount -= coins[i]; // amount = amount - coins[i];

                sum++;
                count++;
            }

            System.out.printf("%d 동전은 %d개가 필요합니다.\n", coins[i], sum);
        }

        return count;
    }

    public static int maxActivities(int[][] activities) {
        int count = 1;

        // 활동 종료 시간을 기준으로 정렬한다.
        Arrays.sort(activities, Comparator.comparingInt(activity -> activity[1]));

        // for (int i = 0; i < activities.length; i++) {
        //     for (int j = 0; j < activities[i].length; j++) {
        //         System.out.print(activities[i][j] + " ");
        //     }
        //
        //     System.out.println();
        // }

        // 첫 번째 활동을 선택한다.
        int lastEndTime = activities[0][1];

        System.out.printf("활동 시작 시간: %d, 활동 종료 시간: %d\n", activities[0][0], lastEndTime);

        // 선택된 활동의 종료 시간 이후에 시작하는 활동이 없을 때까지 반복을 수행한다.
        for (int i = 1; i < activities.length; i++) {
            if (activities[i][0] >= lastEndTime) {
                count++;

                lastEndTime = activities[i][1];

                System.out.printf("활동 시작 시간: %d, 활동 종료 시간: %d\n", activities[i][0], lastEndTime);
            }
        }

        return count;
    }
}
