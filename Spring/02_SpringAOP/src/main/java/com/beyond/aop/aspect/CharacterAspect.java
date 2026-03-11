package com.beyond.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CharacterAspect {

    @Pointcut("execution(* com.beyond.aop.character.Character.quest(..)) && args(questName)")
    public void questPointCut(String questName) {}

    // 로그를 남기는 기능(동작) = 어드바이스(메소드)
    // 적용될 시점들(조인포인트) -> 특정 시점 지정(포인트컷)
    // @Before("questPointCut()")  // 포인트컷 지정자 - 특정 클래스의 메소드에 적용할 것
    // @Before(value = "questPointCut(questName)", argNames = "questName")
    public void beforeQuest(String questName) {
        System.out.printf("[%s] 퀘스트 시작!\n", questName);
    }


    // 결과(예외 여부, ...)에 상관없이 필요한 작업들
    // @After("questPointCut()")
    // @After(value = "questPointCut(questName)", argNames = "questName")
    public void afterQuest(String questName) {
        System.out.printf("[%s] 퀘스트 완료\n", questName);
    }


    // 대상 메소드가 정상적으로 실행된 경우 실행될 작업들
    // @AfterReturning(
    //         value = "questPointCut(questName)",
    //         returning = "result",  // 대상 메소드가 반환한 값도 받아올 수 있다.
    //         argNames = "questName, result")
    public void success(String questName, String result) {
        System.out.print(result);
        System.out.printf("[%s] 퀘스트 완료. 경험치를 획득합니다.\n", questName);
    }

    // 대상 메소드 실행 중 예외가 발생한 경우 실행될 작업들
    // @AfterThrowing(
    //         value = "questPointCut(questName)",
    //         throwing = "exception",
    //         argNames = "questName, exception")
    public void fail(String questName, Exception exception) {
        System.out.printf("[%s] %s(으)로 인해 퀘스트 실패\n", questName, exception.getMessage());
    }

    // 다 한번에 -> 주로 이걸로 만들어진다.
    @Around("execution(* com.beyond.aop.character.Character.quest(..))")
    public String around(ProceedingJoinPoint jp) {
        String result = "";
        String questName = String.format("[%s]", jp.getArgs()[0]);

        try {
            // proceed() 이전은 @Before 역할

            System.out.printf("%s 퀘스트 시작\n", questName);


            // proceed()에서 대상 메소드가 실행
            result = (String) jp.proceed(new Object[] {questName});
            // proceed() 이후는 @AfterReturning

            System.out.println(result);
            System.out.printf("%s 퀘스트 성공\n", questName);

        } catch (Throwable e) {
            // 대상 메소드에서 예외를 throw할 경우
            // @AfterThrowing 어드바이스의 기능

            System.out.printf("%s %s(으)로 인해 퀘스트 실패\n", questName, e.getMessage());


        }

        return result;
    }


}
