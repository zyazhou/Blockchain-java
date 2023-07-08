package com.blockchain.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.blockchain.entity.Blockprojectinfo;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.entity.Blockusageinfo;
import com.blockchain.mapper.BlockusageinfoMapper;
import com.blockchain.service.BlockusageinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.blockchain.utils.blockchainUtil;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
@Service
public class BlockusageinfoServiceImpl extends ServiceImpl<BlockusageinfoMapper, Blockusageinfo> implements BlockusageinfoService {
    @Override
    public boolean saveid(Blockusageinfo blockusageinfo) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        // 将Blockprojectinfo对象转换成JSON格式的字符串
        String jsonStr = JSONObject.toJSONString(blockusageinfo);
// 手动拼接成要求的格式，注意大写
        String initArgs[] = new String[]{
                blockusageinfo.getProjectid(),
                "{\"projectID\":\"" + blockusageinfo.getProjectid() + "\","
                        + "\"userID\":\"" + blockusageinfo.getUserid() + "\","
                        + "\"userName\":\"" + blockusageinfo.getUsername() + "\","
                        + "\"usedAmount\":\"" + blockusageinfo.getUsedamount() + "\","
                        + "\"usedInfo\":\"" + blockusageinfo.getUsedinfo() + "\","
                        + "\"usedTime\":\"" + blockusageinfo.getUsedtime() + "\"}"
        };
        //S{"4","{\"projectID\":\"303\",\"userID\":\"180id\",\"userName\":\"周00迎安\",\"usedAmount\":\"800\",\"usedTime\":\"2023-3-18\",\"usedInfo\":\"只要人人都献出一点爱,世界将变成美好的明天!\"}"};
        System.out.println(Arrays.toString(initArgs));
        blockchainUtil.save("usageInfo",initArgs);
        return true;
    }

    @Override
    public Blockusageinfo queryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {

        String queryresult = blockchainUtil.query("usageInfo", id);
        Blockusageinfo blockusageinfo = new Blockusageinfo();
        if (queryresult == null) {

            return blockusageinfo;
        } else {
            JSONObject jsonObject = JSONObject.parseObject(queryresult);
// 获取每个键对应的值

//            ProjectID  string `json:"projectID"`
//            UserID     string `json:"userID  "`
//            UserName   string `json:"userName  "`
//            UsedAmount string `json:"usedAmount  "`
//            UsedTime   string `json:"usedTime   "`
//            UsedInfo   string `json:"usedInfo  "`

            //System.out.println("projectID:" +jsonObject.getString("projectID") +","+"userID:" +jsonObject.getString("userID"));
            blockusageinfo.setUserid(jsonObject.getString("userID"));
            blockusageinfo.setProjectid(jsonObject.getString("projectID"));
            blockusageinfo.setUsername(jsonObject.getString("userName"));
            blockusageinfo.setUsedamount(jsonObject.getString("usedAmount"));
            blockusageinfo.setUsedtime(jsonObject.getString("usedTime"));
            blockusageinfo.setUsedinfo(jsonObject.getString("usedInfo"));

            return blockusageinfo;
        }


    }

    @Override
    public List<Blockusageinfo> queryHistoryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException {
        List<String> list =blockchainUtil.queryHistory("usageInfo", id,"usageInfos"); //里面的是String类型
        List <Blockusageinfo> queryresult= new ArrayList<Blockusageinfo>(); //queryresult是Blockrecordinfo类型


        //使用迭代器遍历，集合特有的遍历方式
        Iterator<String> it = list.iterator();
        String s=null;
//        System.out.println("queryHistoryById方法调用-------");
        JSONObject jsonObject;
        while (it.hasNext()){
            s=it.next();
            Blockusageinfo blockusageinfo=new Blockusageinfo();
            jsonObject = JSONObject.parseObject(s);
            //System.out.println("projectID:" +jsonObject.getString("projectID") +","+"userID:" +jsonObject.getString("userID"));
            blockusageinfo.setUserid(jsonObject.getString("userID"));
            blockusageinfo.setProjectid(jsonObject.getString("projectID"));
            blockusageinfo.setUsername(jsonObject.getString("userName"));
            blockusageinfo.setUsedamount(jsonObject.getString("usedAmount"));
            blockusageinfo.setUsedtime(jsonObject.getString("usedTime"));
            blockusageinfo.setUsedinfo(jsonObject.getString("usedInfo"));

            queryresult.add(blockusageinfo);
        }

        return queryresult;
    }
}
