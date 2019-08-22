package com.geely.devops.aoptest.complet;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.boot.test.autoconfigure.data.ldap.DataLdapTest;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.complet
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2019/8/23 0:42
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2019
 */
@Data
@AllArgsConstructor
public class StoreUser {
    private String storeCode;

    private String storeUserCode;
}
