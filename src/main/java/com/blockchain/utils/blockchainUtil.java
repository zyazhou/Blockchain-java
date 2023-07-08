package com.blockchain.utils;


import com.blockchain.common.lang.Const;
import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.fabric.sdk.FabricClient;
import com.blockchain.fabric.sdk.UserContext;
import com.blockchain.fabric.sdk.UserUtils;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.protos.common.Ledger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.*;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class blockchainUtil {
    public static String  query(String chaincodename, String id) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CryptoException, InvalidArgumentException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, TransactionException, ProposalException {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment =  UserUtils.getEnrollment(Const.keyFolderPath,Const.keyFileName,Const.certFoldePath,Const.certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",Const.tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",Const.tlsPeerFilePathAddtion);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        String initArgs[] = {id};
        Map map =  fabricClient.queryChaincode(peers,"mychannel", TransactionRequest.Type.GO_LANG,chaincodename,"query",initArgs);
//        System.out.println("------查询到的数据------");
//        System.out.println(map);
        Integer k = null;
        for (Object key : map.keySet()) {
//            System.out.println("key= " + key + " and value= " + map.get(key));
            k=(Integer)key;
        }
        return (String) map.get(k);
    }

    public static List<String>  queryHistory(String chaincodename, String id,String tmp) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CryptoException, InvalidArgumentException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, TransactionException, ProposalException {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment =  UserUtils.getEnrollment(Const.keyFolderPath,Const.keyFileName,Const.certFoldePath,Const.certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",Const.tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",Const.tlsPeerFilePathAddtion);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        String initArgs[] = {id};
        Map map =  fabricClient.queryChaincode(peers,"mychannel", TransactionRequest.Type.GO_LANG,chaincodename,"queryHistory",initArgs);

        List <String> list= new ArrayList<String>();

        Integer k = null;
        for (Object key : map.keySet()) {
            //System.out.println("key= " + key + " and value= " + map.get(key));
            k=(Integer)key;
        }
        String s1=(String) map.get(k);


        //"recordInfos"
        JSONObject json = new JSONObject(s1);
        JSONArray recordInfos = json.getJSONArray(tmp);
        for (int i = 0; i < recordInfos.length(); i++) {
            JSONObject record = recordInfos.getJSONObject(i);
            list.add(record.toString());
        }
        //使用迭代器遍历，集合特有的遍历方式
        Iterator <String> it = list.iterator();
        String s=null;
        while (it.hasNext()) {
            s = it.next();
            //System.out.println("s的值-------");

            //System.out.println(s);
        }
        //System.out.println("s的值结束-------");
        //System.out.println(list);
        return list;
    }

    public static String  save(String chaincodename, String []init) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException, CryptoException, InvalidArgumentException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException, TransactionException, ProposalException {

        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment =  UserUtils.getEnrollment(Const.keyFolderPath,Const.keyFileName,Const.certFoldePath,Const.certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",Const.tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",Const.tlsPeerFilePathAddtion);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        Orderer order = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",Const.tlsOrderFilePath);
        //String initArgs[] = {"362422","{\"name\":\"周\",\"identity\":\"362422\",\"mobile\":\"18079xxxxxx\"}"};

        //usageInfo
        //String initArgs[] = {"1","{\"projectID\":\"6025\",\"userID\":\"180id\",\"userName\":\"00周迎安\"}"};
        //String initArgs[] = {"4","{\"projectID\":\"303\",\"userID\":\"180id\",\"userName\":\"周00迎安\",\"usedAmount\":\"800\",\"usedTime\":\"2023-3-18\",\"usedInfo\":\"只要人人都献出一点爱,世界将变成美好的明天!\"}"};

//         //recordInfo
//         String initArgs[] = {"1","{\"projectID\":\"6025\",\"userID\":\"180id\",\"donationAmount\":\"2000\",\"donationTime\":\"2023-3-18\",\"message\":\"这是第二条测试数据！\"}"};
//        //peer chaincode query -C mychannel -n recordInfo -c '{"Args":["query","1"]}'
//         //peer chaincode query -C mychannel -n recordInfo -c '{"Args":["queryHistory","1"]}'
//        fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG,"recordInfo",order,peers,"save",initArgs);

        //projectInfo
        String initArgs[] = init;
        //peer chaincode query -C mychannel -n projectInfo -c '{"Args":["query","1"]}'
        fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG,chaincodename,order,peers,"save",initArgs);

        return "succ";

    }
}
