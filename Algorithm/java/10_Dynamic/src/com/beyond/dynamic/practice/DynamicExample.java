package com.beyond.dynamic.practice;

import java.util.HashMap;
import java.util.Map;

public class DynamicExample {
    // 계산된 피보나치 수열의 항을 저장하는 자료구조이다.
    //  - 키는 피보나치 수열의 항
    //  - 값은 해당 항의 피보나치 수
    private static final Map<Long, Long> memo = new HashMap<>();

    /*
        피보나치 수열
          - 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, ...
     */
    public static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        } else if (memo.containsKey(n)) {
            return memo.get(n);
        } else {
            long result = fibonacci(n - 1) + fibonacci(n - 2);

            memo.put(n, result);

            return result;
        }
    }
}
