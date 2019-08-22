package com.geely.devops.aoptest.service.impl;

import com.geely.devops.aoptest.AopTestApplicationTests;
import com.geely.devops.aoptest.bean.Menu;
import com.geely.devops.aoptest.service.MenuService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.service.impl
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2018/9/7 23:14
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
public class MenuServiceImplTest extends AopTestApplicationTests {

    @Autowired
    private MenuService menuService;

    @Test
    public void add() {
        Menu menu = new Menu();
        menu.setMenuName("菜单1");
        menu.setIsLeaf(0);
        menu.setMenuUrl("www.sdsdsd.com");
        menuService.add(menu);
    }
}