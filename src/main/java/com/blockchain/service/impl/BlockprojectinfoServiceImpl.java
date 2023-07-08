package com.blockchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.entity.Blockprojectinfo;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.mapper.BlockprojectinfoMapper;
import com.blockchain.service.BlockprojectinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blockchain.utils.blockchainUtil;
import lombok.SneakyThrows;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@Service
public class BlockprojectinfoServiceImpl extends ServiceImpl<BlockprojectinfoMapper, Blockprojectinfo> implements BlockprojectinfoService {
    @SneakyThrows
    @Override
    public boolean save(Blockprojectinfo blockprojectinfo) {

// 将Blockprojectinfo对象转换成JSON格式的字符串
        String jsonStr = JSONObject.toJSONString(blockprojectinfo);
// 手动拼接成要求的格式，注意大写
        String initArgs[] = new String[]{
                blockprojectinfo.getProjectid(),
                "{\"projectID\":\"" + blockprojectinfo.getProjectid() + "\","
                        + "\"projectName\":\"" + blockprojectinfo.getProjectname() + "\","
                        + "\"userID\":\"" + blockprojectinfo.getUserid() + "\","
                        + "\"userName\":\"" + blockprojectinfo.getUsername() + "\","
                        + "\"expetationAmount\":\"" + blockprojectinfo.getExpetationamount() + "\","
                        + "\"createTime\":\"" + blockprojectinfo.getCreatetime() + "\","
                        + "\"projectInfo\":\"" + blockprojectinfo.getProjectinfo() + "\","
                        + "\"donationAmount\":\"" + blockprojectinfo.getDonationamount() + "\","
                        + "\"donationTimes\":\"" + blockprojectinfo.getDonationtimes() + "\"}"
        };

        System.out.println(Arrays.toString(initArgs));
        blockchainUtil.save("projectInfo",initArgs);
        return true;
    }

    @Override
    public Blockprojectinfo queryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        String queryresult = blockchainUtil.query("projectInfo", id);
        Blockprojectinfo blockprojectinfo=new Blockprojectinfo();
        if(queryresult ==null){

            return blockprojectinfo;
        }else{
            JSONObject jsonObject = JSONObject.parseObject(queryresult);
// 获取每个键对应的值

//            ProjectID        string `json:"projectID"`
//            ProjectName      string `json:"projectName"`
//            UserID           string `json:"userID  "`
//            UserName         string `json:"userName  "`
//            ExpetationAmount int    `json:"expetationAmount  "`
//            ProjectInfo      string `json:"projectInfo  "`
//            CreateTime       string `json:"createTime   "`
//            DonationAmount   string `json:"donationAmount   "`
//            DonationTimes    string `json:"donationTimes   "`

            //System.out.println("projectID:" +jsonObject.getString("projectID") +","+"userID:" +jsonObject.getString("userID"));
            blockprojectinfo.setUserid(jsonObject.getString("userID"));
            blockprojectinfo.setProjectid( jsonObject.getString("projectID") );
            blockprojectinfo.setProjectname(jsonObject.getString("projectName") );
            blockprojectinfo.setUsername(jsonObject.getString("userName") );
            blockprojectinfo.setExpetationamount(jsonObject.getString("expetationAmount"));

            blockprojectinfo.setProjectinfo(jsonObject.getString("projectInfo") );
            blockprojectinfo.setCreatetime(jsonObject.getString("createTime") );
            blockprojectinfo.setDonationamount(jsonObject.getString("donationAmount") );
            blockprojectinfo.setDonationtimes(jsonObject.getString("donationTimes"));
            return blockprojectinfo;
        }

    }
}
