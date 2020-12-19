package com.iemij.test.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iemij.test.common.Response;
import com.iemij.test.domain.Test;
import com.iemij.test.domain.customized.Page;
import com.iemij.test.mapper.TestMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);
    private static final String tags = "测试";
    @Autowired
    private TestMapper testMapper;

    @RequestMapping("/test")
    @ApiImplicitParam(paramType = "query", name = "id", value = "测试id", required = true, dataType = "Long", defaultValue = "1")
    @ApiOperation(value = "测试一个参数的接口", httpMethod = "GET", tags = {tags})
    public Response test(Long id) {
        Test test = testMapper.selectByPrimaryKey(id);
        return Response.succInstance(test);
    }

    @RequestMapping("/test1")
    @ApiOperation(value = "测试多参数接口", httpMethod = "GET", tags = {tags})
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "id", value = "测试id", required = true, dataType = "Long", defaultValue = "1"),
            @ApiImplicitParam(paramType = "query", name = "name", value = "名字", required = true, dataType = "String"),
    })
    public Response test1(Long id, String name) {
        Test test = new Test();
        test.setId(id);
        test.setName(name);
        return Response.succInstance(testMapper.selectOne(test));
    }

    @RequestMapping(value = "/test2")
    @ApiOperation(value = "测试对象参数接口", httpMethod = "POST", tags = {tags})
    public Response test2(@RequestBody Test test) {
        Test test1 = new Test();
        test1.setId(test.getId());
        test1.setName(test.getName());
        return Response.succInstance(testMapper.selectOne(test1));
    }

    @RequestMapping(value = "/test3")
    @ApiOperation(value = "测试分页", httpMethod = "GET", tags = {tags})
    public Response test2(Page page) {
        PageInfo<Test> pageInfo = PageHelper.startPage(page.getPageNo(), page.getPageSize())
                .doSelectPageInfo(() -> testMapper.selectAll());
        page.setResult(pageInfo.getList());
        page.setTotalCount((int) pageInfo.getTotal());
        return Response.succInstance(page);
    }
}
