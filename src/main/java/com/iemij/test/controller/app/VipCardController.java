package com.iemij.test.controller.app;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.iemij.test.common.Response;
import com.iemij.test.controller.BaseController;
import com.iemij.test.domain.Test;
import com.iemij.test.domain.VipCard;
import com.iemij.test.domain.customized.Page;
import com.iemij.test.mapper.VipCardMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("vipCardController")
@RequestMapping("/app/vip-card")
public class VipCardController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(VipCardController.class);
    private static final String tags = "App端会员卡相关接口";
    @Autowired
    private VipCardMapper vipCardMapper;

    @RequestMapping("/create")
    @ApiOperation(value = "创建会员卡", httpMethod = "POST", tags = {tags})
    public Response create(@RequestBody VipCard vipCard) {
        try {
            vipCardMapper.insert(vipCard);
            return Response.succInstance();
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return Response.errInstance(e.getMessage());
        }
    }

    @RequestMapping(value = "/list")
    @ApiOperation(value = "会员卡分页查询", httpMethod = "GET", tags = {tags})
    public Response list(Page page) {
        LOGGER.info(String.valueOf(System.currentTimeMillis()));
        PageInfo<VipCard> pageInfo = PageHelper.startPage(page.getPageNo(), page.getPageSize())
                .doSelectPageInfo(() -> vipCardMapper.selectAll());
        page.setResult(pageInfo.getList());
        page.setTotalCount((int) pageInfo.getTotal());
        return Response.succInstance(page);
    }

    @RequestMapping(value = "/detail")
    @ApiOperation(value = "会员卡详情查询", httpMethod = "GET", tags = {tags})
    @ApiImplicitParam(paramType = "query", name = "vipCardId", value = "会员卡id", required = true, dataType = "Long", defaultValue = "1")
    public Response detail(Long vipCardId) {
        return Response.succInstance(vipCardMapper.selectByPrimaryKey(vipCardId));
    }

    @RequestMapping(value = "/delete")
    @ApiOperation(value = "会员卡删除", httpMethod = "GET", tags = {tags})
    @ApiImplicitParam(paramType = "query", name = "vipCardId", value = "会员卡id", required = true, dataType = "Long", defaultValue = "1")
    public Response delete(Long vipCardId) {
        return Response.succInstance(vipCardMapper.deleteByPrimaryKey(vipCardId));
    }

    @RequestMapping(value = "/update")
    @ApiOperation(value = "会员卡更新", httpMethod = "POST", tags = {tags})
    public Response update(@RequestBody VipCard vipCard) {
        return Response.succInstance(vipCardMapper.updateByPrimaryKeySelective(vipCard));
    }
}
