package com.blockchain.controller;


import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blockchain.common.lang.Result;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.entity.Blockusageinfo;
import com.blockchain.entity.Projectinfo;
import com.blockchain.entity.User;
import com.blockchain.service.BlockrecordinfoService;
import com.blockchain.service.BlockusageinfoService;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@RestController
@RequestMapping("/blockusageinfo")
public class BlockusageinfoController extends BaseController {
    @Autowired
    BlockusageinfoService blockusageinfoService;

    @GetMapping("/query")
    public Result query(String id) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        Blockusageinfo blockusageinfo=blockusageinfoService.queryById(id);
        if(blockusageinfo==null) {
            return Result.fail("未查到");
        }else{
            return Result.succ(blockusageinfo);
        }
    }
    //http://localhost:8081/blockrecordinfo/queryhistory?id=1
    @GetMapping("/queryhistory")
    public Result queryHistory(String id) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        List<Blockusageinfo> blockusageinfos=blockusageinfoService.queryHistoryById(id);
        if(blockusageinfos==null) {
            return Result.fail("未查到");
        }else{
            return Result.succ(blockusageinfos);
        }
    }

    @PostMapping("/usagesave")
    public Result usagesave(@RequestBody Blockusageinfo blockusageinfo) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        System.out.println("执行/blockusageinfo/usagesave-------------------------------------------");
        if( blockusageinfoService.saveid(blockusageinfo) ) {
            System.out.println("cg");
        }else {
            System.out.println("sb");
        }
        return Result.succ(
                MapUtil.builder()
                        .put("blockusageinfo",blockusageinfo )
                        .build()
        );

    }
}
