package com.geely.devops.aoptest.config;

import com.geely.devops.aoptest.service.MenuService;
import com.geely.devops.aoptest.service.impl.MenuServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.config
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2019/4/5 18:15
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2019
 */
@Configuration
public class AppConfig {
    @Bean
    public MenuService menuService(){
        return new MenuServiceImpl();
    }
}
