package com.geely.devops.aoptest.bean;

import lombok.Data;

@Data
public class Menu {
    private Integer id;

    private String menuName;

    private Integer isLeaf;

    private Integer parentId;

    private String menuUrl;

}