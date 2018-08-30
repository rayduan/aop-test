package com.geely.devops.aoptest.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.aop
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2018-08-30 15:52
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
@Aspect
@Component
public class ArchivesLogAspect {
    @Pointcut("execution(public * com.geely.devops.aoptest.controller.*.*(..))")
    public void webRequestLog() {}

}
