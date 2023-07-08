package com.blockchain.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blockchain.common.lang.Result;
import com.blockchain.entity.Blockprojectinfo;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.entity.Projectinfo;
import com.blockchain.entity.User;
import com.blockchain.service.BlockprojectinfoService;
import com.blockchain.service.BlockrecordinfoService;
import com.blockchain.service.ProjectinfoService;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/blockprojectinfo")
public class BlockprojectinfoController extends BaseController {

    @Autowired
    BlockprojectinfoService blockprojectinfoService;
    @Autowired
    ProjectinfoService projectinfoService;

    @GetMapping("/query")
    public Result query(String id) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        Blockprojectinfo blockprojectinfo =blockprojectinfoService.queryById(id);

        if(blockprojectinfo==null) {
            return Result.fail("未查到");
        }else{
            LambdaQueryWrapper<Projectinfo> queryWrapper0 = new LambdaQueryWrapper();
            queryWrapper0.eq(Projectinfo::getProjectid,blockprojectinfo.getProjectid());
            Projectinfo projectinfo = projectinfoService.getOne(queryWrapper0);

            blockprojectinfo.setDonationamount(projectinfo.getDonationamount());
            blockprojectinfo.setDonationtimes(projectinfo.getDonationtimes()+"");
            return Result.succ(blockprojectinfo);
        }
    }

    @GetMapping("/mysqlquery")
    public Result mysqlquery(String id) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {


        LambdaQueryWrapper<Projectinfo> queryWrapper2 = new LambdaQueryWrapper();
        queryWrapper2.eq(Projectinfo::getProjectid,id);
        Projectinfo projectinfoServiceOne = projectinfoService.getOne(queryWrapper2);


        String s1=projectinfoServiceOne.getDonationamount();
        Integer s2=projectinfoServiceOne.getDonationtimes();
        return Result.succ(
                MapUtil.builder()
                        .put("donationamount", s1)
                        .put("donationtimes", s2)
                        .build()
        );
    }


}
