package com.blockchain.controller;


import cn.hutool.core.lang.UUID;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.blockchain.common.lang.Const;
import com.blockchain.common.lang.Result;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.entity.Projectinfo;
import com.blockchain.entity.User;
import com.blockchain.mapper.UserMapper;
import com.blockchain.service.BlockprojectinfoService;
import com.blockchain.service.BlockrecordinfoService;
import com.blockchain.service.ProjectinfoService;
import com.blockchain.service.UserService;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@Slf4j
@RestController
@RequestMapping("/blockrecordinfo")
public class BlockrecordinfoController extends BaseController {
    @Autowired
    BlockrecordinfoService blockrecordinfoService;
    @Autowired
    UserService userService;
    @Autowired
    ProjectinfoService projectinfoService;
    @Autowired
    BlockprojectinfoService blockprojectinfoService;

    @GetMapping("/query")
    public Result query(String id) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        Blockrecordinfo blockrecordinfo=blockrecordinfoService.queryById(id);
        if(blockrecordinfo==null) {
            return Result.fail("未查到");
        }else{
            return Result.succ(blockrecordinfo);
        }
    }
//http://localhost:8081/blockrecordinfo/queryhistory?id=1
    @GetMapping("/queryhistory")
    public Result queryHistory(String id) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        List<Blockrecordinfo> blockrecordinfos=blockrecordinfoService.queryHistoryById(id);
        if(blockrecordinfos==null) {
            return Result.fail("未查到");
        }else{
            return Result.succ(blockrecordinfos);
        }
    }

    @PostMapping("/user/donation")
    public Result donation(@RequestBody Blockrecordinfo blockrecordinfo) throws IOException, ProposalException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        log.info("执行/user/donation-------------------------------------------");
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createtime= formatter.format(date);

        blockrecordinfo.setDonationtime(createtime);

        //1、上传 用户的捐赠记录 到区块链
        if( blockrecordinfoService.saveid(blockrecordinfo) ) {
            System.out.println("cg");
        }else {
            System.out.println("sb");
        }
        //2、在mysql数据库查用户的信息，用户累计捐款值增加
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper();
        queryWrapper1.eq(User::getUserId,blockrecordinfo.getUserid());
        User userServiceOne1 = userService.getOne(queryWrapper1);

        int inum1 = Integer.parseInt(userServiceOne1.getTotalmoney()); //捐款前的值
        //System.out.println(inum1);
        int inum2 = Integer.parseInt(blockrecordinfo.getDonationamount());//捐款值
        //System.out.println(inum2);
        int inum3=inum1+inum2; //更改后的值
       // System.out.println(inum3);
        String s = Integer.toString(inum3); //转为string
      //  System.out.println(s);

        UpdateWrapper<User> userUpdateWrapper =  new UpdateWrapper<>();
        userUpdateWrapper.eq("person_id",userServiceOne1.getPersonId());
        userUpdateWrapper.set("totalmoney",s);
        userService.update(userUpdateWrapper);

        //3、更新区块链的慈善项目信息,这里只保持在本地的mysql数据库
        LambdaQueryWrapper<Projectinfo> queryWrapper2 = new LambdaQueryWrapper();
        queryWrapper2.eq(Projectinfo::getProjectid,blockrecordinfo.getProjectid());
        Projectinfo projectinfoServiceOne = projectinfoService.getOne(queryWrapper2);
        int inum4 = Integer.parseInt(projectinfoServiceOne.getDonationamount()); //捐款前的值
      //  System.out.println(inum4);
        int inum5 = Integer.parseInt(blockrecordinfo.getDonationamount());//捐款值
      //  System.out.println(inum5);
        int inum6=inum4+inum5; //更改后的值
      //  System.out.println(inum6);
        String s2 = Integer.toString(inum6); //转为string
      //  System.out.println(s2);

        UpdateWrapper<Projectinfo> userUpdateWrapper2 =  new UpdateWrapper<>();
        userUpdateWrapper2.eq("projectid",projectinfoServiceOne.getProjectid());
        userUpdateWrapper2.set("donationamount",s2);
        userUpdateWrapper2.set("donationtimes",projectinfoServiceOne.getDonationtimes()+1);
        projectinfoService.update(userUpdateWrapper2);
        return Result.succ(
                MapUtil.builder()
                        .put("blockrecordinfo",blockrecordinfo )
                        .build()
        );

    }

}
