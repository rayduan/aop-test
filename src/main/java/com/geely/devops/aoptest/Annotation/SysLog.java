package com.geely.devops.aoptest.Annotation;

import java.lang.annotation.*;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.Annotation
 * @Description: 注解参数
 * @Author: duanrui
 * @CreateDate: 2018-08-30 15:44
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    /** 要执行的操作类型比如：add操作 **/
    public String operationType() default "";

    /** 要执行的具体操作比如：添加用户 **/
    public String operationName() default "";
}
