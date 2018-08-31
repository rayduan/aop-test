package com.geely.devops.aoptest.util;

import org.aspectj.lang.JoinPoint;

import java.lang.reflect.Method;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.util
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2018-08-31 10:16
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
public class Common {

    /**
     * 获取方法上的注解对象
     * @param clazz
     * @param joinPoint
     * @param <T>
     * @return
     * @throws ClassNotFoundException
     */
//    public static <T> T getAnnotation(Class<T> clazz, JoinPoint joinPoint) throws ClassNotFoundException {
//        T annotation = null;
//        String targetName = joinPoint.getTarget().getClass().getName();
//        String methodName = joinPoint.getSignature().getName();
//        Object[] arguments = joinPoint.getArgs();
//        Class targetClass = Class.forName(targetName);
//        Method[] methods = targetClass.getMethods();
//        for (Method method : methods)
//            if (method.getName().equals(methodName)) {
//                Class[] clazzs = method.getParameterTypes();
//                if (clazzs.length == arguments.length) {
//                    annotation = (T) method.getAnnotation(clazz);
//                    break;
//                }
//            }
//        return annotation;
//    }
}
