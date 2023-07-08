package com.blockchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.common.lang.Result;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.mapper.BlockrecordinfoMapper;
import com.blockchain.service.BlockrecordinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.BlockchainInfo;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.blockchain.utils.blockchainUtil;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;


import com.blockchain.entity.Blockrecordinfo;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@Slf4j
@Service
public class BlockrecordinfoServiceImpl extends ServiceImpl<BlockrecordinfoMapper, Blockrecordinfo> implements BlockrecordinfoService {

    //        UserID         string `json:"userID  "`
//        ProjectID      string `json:"projectID"`
//        DonationAmount string `json:"donationAmount  "`
//        DonationTime   string `json:"donationTime   "`
//        Message        string `json:"message  "`
    @Override
    public Blockrecordinfo queryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        String queryresult =blockchainUtil.query("recordInfo", id);
        Blockrecordinfo blockrecordinfo=new Blockrecordinfo();
        if(queryresult ==null){
            log.info("serviceimpl未查到--0");
            log.info(blockrecordinfo.getUserid());
            return blockrecordinfo;
        }else{
            JSONObject jsonObject = JSONObject.parseObject(queryresult);
// 获取每个键对应的值

            //System.out.println("projectID:" +jsonObject.getString("projectID") +","+"userID:" +jsonObject.getString("userID"));
            blockrecordinfo.setUserid(jsonObject.getString("userID"));
            blockrecordinfo.setProjectid( jsonObject.getString("projectID") );

            blockrecordinfo.setDonationamount( jsonObject.getString("donationAmount") );
            blockrecordinfo.setDonationtime( jsonObject.getString("donationTime") );
            blockrecordinfo.setMessage(jsonObject.getString("message"));

//            System.out.println("jsonObject的值-------");
//            System.out.println(jsonObject);
            return blockrecordinfo;
        }

    }

    @Override
    public List <Blockrecordinfo> queryHistoryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        List<String> list =blockchainUtil.queryHistory("recordInfo", id,"recordInfos"); //里面的是String类型
        List <Blockrecordinfo> queryresult= new ArrayList<Blockrecordinfo>(); //queryresult是Blockrecordinfo类型


        //使用迭代器遍历，集合特有的遍历方式
        Iterator <String> it = list.iterator();
        String s=null;
//        System.out.println("queryHistoryById方法调用-------");
        JSONObject jsonObject;
        while (it.hasNext()){
            s=it.next();
//            System.out.println("s的值-------");
//            System.out.println(s);

            jsonObject = JSONObject.parseObject(s);

//            System.out.println("jsonObject的值-------");
//            System.out.println(jsonObject);
            Blockrecordinfo blockrecordinfo=new Blockrecordinfo();
            //System.out.println("projectID:" +jsonObject.getString("projectID") +","+"userID:" +jsonObject.getString("userID"));
            blockrecordinfo.setUserid(jsonObject.getString("userID"));
            blockrecordinfo.setProjectid( jsonObject.getString("projectID") );
            blockrecordinfo.setDonationamount( jsonObject.getString("donationAmount") );
            blockrecordinfo.setDonationtime( jsonObject.getString("donationTime") );
            blockrecordinfo.setMessage(jsonObject.getString("message"));

//            System.out.println("blockrecordinfo111-------");
//            System.out.println(blockrecordinfo.getMessage());
//            System.out.println(blockrecordinfo.getUserid());
            queryresult.add(blockrecordinfo);
        }
//        System.out.println("queryresult");
//        System.out.println(queryresult);
        return queryresult;
    }

    @Override
    public boolean saveid( Blockrecordinfo blockrecordinfo) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        // 将Blockprojectinfo对象转换成JSON格式的字符串
        String jsonStr = JSONObject.toJSONString(blockrecordinfo);
// 手动拼接成要求的格式，注意大写
        String initArgs[] = new String[]{
                blockrecordinfo.getUserid(),
                "{\"projectID\":\"" + blockrecordinfo.getProjectid() + "\","
                        + "\"userID\":\"" + blockrecordinfo.getUserid() + "\","
                        + "\"donationAmount\":\"" + blockrecordinfo.getDonationamount() + "\","
                        + "\"donationTime\":\"" + blockrecordinfo.getDonationtime() + "\","
                        + "\"message\":\"" + blockrecordinfo.getMessage() + "\"}"
        };
// {"1","{\"projectID\":\"6025\",\"userID\":\"180id\",\"donationAmount\":\"2000\",\"donationTime\":\"2023-3-18\",\"message\":\"这是第二条测试数据！\"}"};

        System.out.println(Arrays.toString(initArgs));
        blockchainUtil.save("recordInfo",initArgs);
        return true;
    }
}
