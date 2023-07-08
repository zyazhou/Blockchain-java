package com.blockchain.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.blockchain.common.lang.Result;
import com.blockchain.entity.Blockusageinfo;
import com.blockchain.entity.Projectinfo;
import com.blockchain.mapper.ProjectinfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-23
 */
@RestController
@RequestMapping("/projectinfo")
public class ProjectinfoController extends BaseController {
    @Resource
    ProjectinfoMapper projectinfoMapper;
    //首页  ---所有项目的基本信息，查本地数据库
    @GetMapping("/allproject")
    public Result allproject(){
        //List<Projectinfo> projectinfos=new ArrayList<Projectinfo>();
        QueryWrapper<Projectinfo> wrapper = new QueryWrapper<>();
        wrapper.select("projectid", "title","expetationamount", "info","url", "donationamount","donationtimes");
        List<Projectinfo> projectinfos = projectinfoMapper.selectList(wrapper);

       // List<Projectinfo> projectinfos = projectinfoMapper.selectList(null);
       // System.out.println(projectinfos);
        return Result.succ(projectinfos);
    }
}
