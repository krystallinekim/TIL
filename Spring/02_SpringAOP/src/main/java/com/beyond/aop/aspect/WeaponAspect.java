package com.beyond.aop.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
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
}
