package com.beyond.aop.aspect;

import com.beyond.aop.annotation.Repeat;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class WeaponAspect {

    // @Around("execution(* com.beyond.aop.weapon.Weapon.attack(..))")
    // @Around("execution(* com.beyond.aop.weapon.*.attack(..))")
    // @Around("execution(* com.beyond.aop.weapon.*.attack(..)) && bean(플람베르주)")
    @Around("execution(* com.beyond.aop.weapon.*.attack(..)) && !@annotation(com.beyond.aop.annotation.Nologging)")
    public String around(ProceedingJoinPoint jp) {

        String result = "";

        try {
            System.out.println("무기를 꺼낸다");

            result = (String) jp.proceed();
            System.out.println(result);

            System.out.println("데미지를 입혔다");
        } catch (Throwable e) {
            System.out.println("공격이 빗나갔다");
        }

        return result;
    }

    @Around("@annotation(com.beyond.aop.annotation.Repeat)")
    public String repeatAdvice(ProceedingJoinPoint jp) {
        String result = "";
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Repeat repeat = signature.getMethod().getAnnotation(Repeat.class);

        System.out.println(repeat);
        System.out.println(repeat.value());
        System.out.println(repeat.count());


        try {
                System.out.println("반복 실행 전");

                result = (String) jp.proceed();

            for (int i = 0; i < repeat.count(); i++) {
                System.out.println(result);
            }
                System.out.println("반복 실행 후");

        } catch (Throwable e) {
            System.out.println("예외 발생");
        }


        return result;
    }
}
