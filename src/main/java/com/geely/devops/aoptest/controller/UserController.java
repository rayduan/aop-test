package com.geely.devops.aoptest.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.geely.devops.aoptest.Annotation.SysLog;
import com.geely.devops.aoptest.dto.ReviewDto;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * @ProjectName: aop-test
 * @Package: com.geely.devops.aoptest.controller
 * @Description: java类作用描述
 * @Author: duanrui
 * @CreateDate: 2018-08-31 16:42
 * @Version: 1.0
 * <p>
 * Copyright: Copyright (c) 2018
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    @SysLog(operationName = "用户模块", operationType = "修改")
    @ApiOperation(value = "修改用户", notes = "查询数据库中某个学生的信息")
    public String add(
            @ApiParam(required = true, name = "id", value = "id")
            @PathVariable(value = "id") String id,
            @ApiParam(required = true, name = "name", value = "用户名称")
            @RequestParam(name = "name", required = true) String name) {
        return name;
    }


    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "获取用户", notes = "查询数据库中某个学生的信息")
    public String get(
            @ApiParam(required = true, name = "id", value = "id")
            @PathVariable(value = "id") String id,
            @ApiParam(required = true, name = "name", value = "用户名称")
            @RequestParam(name = "name", required = true) String name) {
        return name;
    }


}
