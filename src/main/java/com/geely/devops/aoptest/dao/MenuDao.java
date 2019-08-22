package com.geely.devops.aoptest.dao;

import com.geely.devops.aoptest.bean.Menu;
import org.springframework.stereotype.Repository;

@Repository
//@Mapper
public interface MenuDao {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
}