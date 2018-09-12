package com.geely.devops.aoptest.service.impl;

import com.geely.devops.aoptest.bean.Menu;
import com.geely.devops.aoptest.dao.MenuDao;
import com.geely.devops.aoptest.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.service.impl
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2018/9/7 23:12
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Override
    public Integer add(Menu menu) {
        return menuDao.insert(menu);
    }
}
