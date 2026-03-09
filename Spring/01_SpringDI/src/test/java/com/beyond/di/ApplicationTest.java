package com.beyond.di;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplicationTest {

    // 프로젝트에서 junit이 실행 가능한 상태인지 체크
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    @Test
    public void nothing() {
        String name = "홍길동";

        Assertions.assertThat(name).isEqualTo("홍길동");
    }
}
