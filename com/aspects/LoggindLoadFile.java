package com.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@Aspect
public class LoggindLoadFile {

//    @Around(value = "execution(String createNew*(*))", argNames = "proceedingJoinPoint,joinPoint")
//    public Object loggingCreatNewFile(ProceedingJoinPoint proceedingJoinPoint, JoinPoint joinPoint) throws Throwable {
//        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
//        System.out.println("логируется попытка вызвать метод: " + methodSignature.getName());
//        Object[] args = joinPoint.getArgs();
//        MultipartFile multipartFile = (MultipartFile) args[0];
//        System.out.println("с именем файла: " + multipartFile.getName());
//        Object object =  proceedingJoinPoint.proceed();
//        System.out.println("тут должно быть имея файла и лог в файл что файл с именем бла бла там сохранен");
//        return object;
//    }

    public void loggingDownLoadFile() {

    }
}
