package com.geely.devops.aoptest;

import com.geely.devops.aoptest.config.AppConfig;
import com.geely.devops.aoptest.service.MenuService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2019/4/5 14:04
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2019
 */
public class SpingTest extends AopTestApplicationTests{

    @Test
    public  void test(){
        AnnotationConfigApplicationContext context= new AnnotationConfigApplicationContext(AppConfig.class);
        MenuService menuService = (MenuService) context.getBean("menuService");
//        context.getba
    }
}
