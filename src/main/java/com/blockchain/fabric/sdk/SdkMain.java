package com.blockchain.fabric.sdk;

import com.alibaba.fastjson.JSONObject;

import org.hyperledger.fabric.protos.common.Ledger;
import org.hyperledger.fabric.sdk.*;
import org.hyperledger.fabric.sdk.exception.*;
import org.hyperledger.fabric_ca.sdk.HFCAClient;
import org.hyperledger.fabric_ca.sdk.exception.EnrollmentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;
//projectInfo:1.0    usageInfo:5.0
public class SdkMain {

    private static final Logger log = LoggerFactory.getLogger(SdkMain.class);

    private static final String keyFolderPath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\keystore";
    private static final String keyFileName="df46a978487527f0614be6ca922859ce44276950afb4d4e4dd854b1e30d75594_sk";
    private static final String certFoldePath="C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\admincerts";
    private static final String certFileName="Admin@org1.example.com-cert.pem";

    private static final String org2keyFolderPath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org2.example.com\\users\\Admin@org2.example.com\\msp\\keystore";
    private static final String org2keyFileName="a976d19d36894512f47f5b01214811f07c3219f64d05129d9ddaf05ad99d6c29_sk";
    private static final String org2certFoldePath="C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org2.example.com\\users\\Admin@org2.example.com\\msp\\admincerts";
    private static final String org2certFileName="Admin@org2.example.com-cert.pem";

    private static  final String tlsOrderFilePath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\ordererOrganizations\\example.com\\tlsca\\tlsca.example.com-cert.pem";

    //private static final String txfilePath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\test.tx";

    private static  final String tlsPeerFilePath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer0.org1.example.com\\msp\\tlscacerts\\tlsca.org1.example.com-cert.pem";

    //另一个组织机构
    private static  final String tlsPeerFilePathAddtion = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org2.example.com\\peers\\peer0.org2.example.com\\msp\\tlscacerts\\tlsca.org2.example.com-cert.pem";

    //创建channel 要重新生成tx文件
//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, TransactionException, ProposalException, org.bouncycastle.crypto.CryptoException {
//
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//
//        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//
//        //Channel channel111 =  fabricClient.createChannel("test",fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath),txfilePath);
//
//        Channel channel = fabricClient.getChannel("test");
//        channel.addOrderer(fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath));
//        channel.joinPeer(fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath));
//        channel.initialize();
//    }

    //安装合约
//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, TransactionException, ProposalException, org.bouncycastle.crypto.CryptoException {
//
//        List list = new ArrayList();
//        UserContext userContext = new UserContext();
//        //org2
//        userContext.setAffiliation("Org2");
//        userContext.setMspId("Org2MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment =  UserUtils.getEnrollment(org2keyFolderPath,org2keyFileName,org2certFoldePath,org2certFileName);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer0 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",tlsPeerFilePathAddtion);
//        Peer peer1 = fabricClient.getPeer("peer1.org2.example.com","grpcs://peer1.org2.example.com:10051",tlsPeerFilePathAddtion);
//
//        //org1
////        userContext.setAffiliation("Org1");
////        userContext.setMspId("Org1MSP");
////        userContext.setAccount("李伟");
////        userContext.setName("admin");
////        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
////        userContext.setEnrollment(enrollment);
////        FabricClient fabricClient = new FabricClient(userContext);
////        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
////        Peer peer1 = fabricClient.getPeer("peer1.org1.example.com","grpcs://peer1.org1.example.com:8051",tlsPeerFilePath);
//
//
//        List<Peer> peers = new ArrayList<Peer>();
//        peers.add(peer0);
//        peers.add(peer1);
//        fabricClient.installChaincode(TransactionRequest.Type.GO_LANG,"projectInfo","1.0","C:\\go\\workspace\\projectInfo","projectInfo",peers);
//
//        //测试用的record        recordInfo :1.0 projectInfo:1.0    usageInfo:5.0
//        // fabricClient.installChaincode(TransactionRequest.Type.GO_LANG,"record","2.0","C:\\go\\workspace\\record","record",peers);
//    }

   //合约实例化 只需一次
    //测试用的record        recordInfo :1.0 projectInfo:1.0    usageInfo:5.0

//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, ProposalException, TransactionException, org.bouncycastle.crypto.CryptoException {
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
//        Orderer order = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath);
//        String initArgs[] = {""};
//        fabricClient.initChaincode("mychannel", TransactionRequest.Type.GO_LANG,"projectInfo","1.0",order,peer,"init",initArgs);
//
//        //usageInfo
//        //fabricClient.initChaincode("mychannel", TransactionRequest.Type.GO_LANG,"usageInfo","2.0",order,peer,"init",initArgs);
//
//    }

   //合约升级   需要先安装2.0合约，再升级
//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, ProposalException, TransactionException, ChaincodeEndorsementPolicyParseException, org.bouncycastle.crypto.CryptoException {
//        UserContext userContext = new UserContext();
//
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
//
//        //        //org2
////        userContext.setAffiliation("Org2");
////        userContext.setMspId("Org2MSP");
////        userContext.setAccount("李伟");
////        userContext.setName("admin");
////        Enrollment enrollment =  UserUtils.getEnrollment(org2keyFolderPath,org2keyFileName,org2certFoldePath,org2certFileName);
////        userContext.setEnrollment(enrollment);
////        FabricClient fabricClient = new FabricClient(userContext);
////        Peer peer = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",tlsPeerFilePathAddtion);
//
//
//        Orderer order = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath);
//        String initArgs[] = {""};
//        fabricClient.upgradeChaincode("mychannel", TransactionRequest.Type.GO_LANG,"usageInfo","5.0",order,peer,"init",initArgs);
//    }

    //invoke 合约
    //由于2.0的合约是要org1和org2背书，所以需要在org2上也安装合约
    //org1或者org2任一节点都可以触发invoke 合约
//     public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, ProposalException, TransactionException, ChaincodeEndorsementPolicyParseException, org.bouncycastle.crypto.CryptoException {
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
//        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",tlsPeerFilePathAddtion);
//        List<Peer> peers = new ArrayList<>();
//        peers.add(peer0);
//        peers.add(peer1);
//        Orderer order = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath);
//        //String initArgs[] = {"362422","{\"name\":\"周\",\"identity\":\"362422\",\"mobile\":\"18079xxxxxx\"}"};
//
//         //usageInfo
//         //String initArgs[] = {"1","{\"projectID\":\"6025\",\"userID\":\"180id\",\"userName\":\"00周迎安\"}"};
//         //String initArgs[] = {"4","{\"projectID\":\"303\",\"userID\":\"180id\",\"userName\":\"周00迎安\",\"usedAmount\":\"800\",\"usedTime\":\"2023-3-18\",\"usedInfo\":\"只要人人都献出一点爱,世界将变成美好的明天!\"}"};
//
////         //recordInfo
////         String initArgs[] = {"1","{\"projectID\":\"6025\",\"userID\":\"180id\",\"donationAmount\":\"2000\",\"donationTime\":\"2023-3-18\",\"message\":\"这是第二条测试数据！\"}"};
////        //peer chaincode query -C mychannel -n recordInfo -c '{"Args":["query","1"]}'
////         //peer chaincode query -C mychannel -n recordInfo -c '{"Args":["queryHistory","1"]}'
////        fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG,"recordInfo",order,peers,"save",initArgs);
//
//         //projectInfo
//         String initArgs[] = {"2","{\"projectID\":\"2\",\"projectName\":\"第二个测试项目名\",\"userID\":\"180id\",\"userName\":\"周迎安\",\"expetationAmount\":\"500000\",\"createTime\":\"2023-3-20\",\"projectInfo\":\"具体的项目信息\",\"donationAmount\":\"50000\",\"donationTimes\":\"96\"}"};
//         //peer chaincode query -C mychannel -n projectInfo -c '{"Args":["query","1"]}'
//
//         fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG,"projectInfo",order,peers,"save",initArgs);
//    }
        //测试用的record        recordInfo :1.0 projectInfo:1.0    usageInfo:5.0
    //查询合约
    //peer chaincode query -C mychannel -n basicinfo -c '{"Args":["query","362422"]}'
   public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, ProposalException, TransactionException, org.bouncycastle.crypto.CryptoException {
        UserContext userContext = new UserContext();
        userContext.setAffiliation("Org1");
        userContext.setMspId("Org1MSP");
        userContext.setAccount("李伟");
        userContext.setName("admin");
        Enrollment enrollment =  UserUtils.getEnrollment(keyFolderPath,keyFileName,certFoldePath,certFileName);
        userContext.setEnrollment(enrollment);
        FabricClient fabricClient = new FabricClient(userContext);
        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",tlsPeerFilePathAddtion);
        List<Peer> peers = new ArrayList<>();
        peers.add(peer0);
        peers.add(peer1);
        String initArgs[] = {"4"};
        Map map =  fabricClient.queryChaincode(peers,"mychannel", TransactionRequest.Type.GO_LANG,"usageInfo","query",initArgs);
        System.out.println("------查询到的数据------");
        System.out.println(map);

       Map<String, String> map1 = new HashMap<>();
       String projectID;
       String userID ;
       String userName;
       String usedAmount ;
       String usedTime;
       String usedInfo ;
       for (Object key : map.keySet()) {
           System.out.println("key= " + key + " and value= " + map.get(key));
           String str= (String) map.get(key);
           //System.out.println("projectID:" +map.get(key).getClass().getName() );
           JSONObject jsonObject = JSONObject.parseObject(str);
// 获取每个键对应的值
           projectID = jsonObject.getString("projectID");
           userID = jsonObject.getString("userID");
           userName = jsonObject.getString("userName");
           usedAmount = jsonObject.getString("usedAmount");
           usedTime = jsonObject.getString("usedTime");
           usedInfo = jsonObject.getString("usedInfo");
           System.out.println("projectID:" +projectID +","+"userID:" +userID);
           System.out.println("userName:" +userName +","+"usedAmount:" +usedAmount);
           System.out.println("usedTime:" +usedTime +","+"usedInfo:" +usedInfo);
       }


   }

   //注册用户 MetcTSuAhDWB
//   public static void main(String[] args) throws Exception {
//        FabricCAClient caClient = new FabricCAClient("http://124.223.6.219:7054",null);
//        UserContext register = new UserContext();
//        register.setName("lihua");
//        register.setAffiliation("org1");
//        Enrollment enrollment = caClient.enroll("admin","adminpw");
//        UserContext registar = new UserContext();
//        registar.setName("admin");
//        registar.setAffiliation("org1");
//        registar.setEnrollment(enrollment);
//       String secret =  caClient.register(registar,register);
//       System.out.println(secret);
//    }

   //注册用户查询合约
//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, CryptoException, ProposalException, TransactionException, org.bouncycastle.crypto.CryptoException, EnrollmentException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
//        FabricCAClient caClient = new FabricCAClient("http://124.223.6.219:7054",null);
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment = caClient.enroll("lihua","MetcTSuAhDWB");
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
//        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",tlsPeerFilePathAddtion);
//        List<Peer> peers = new ArrayList<>();
//        peers.add(peer0);
//        peers.add(peer1);
//        String initArgs[] = {"362422"};
//        Map map =  fabricClient.queryChaincode(peers,"mychannel", TransactionRequest.Type.GO_LANG,"basicinfo","query",initArgs);
//        System.out.println(map);
//    }



    //注册用户invoke合约
//    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, CryptoException, IOException, IllegalAccessException, InvalidArgumentException, InstantiationException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, org.hyperledger.fabric.sdk.exception.CryptoException, ProposalException, TransactionException, ChaincodeEndorsementPolicyParseException, EnrollmentException, org.hyperledger.fabric_ca.sdk.exception.InvalidArgumentException {
//        FabricCAClient caClient = new FabricCAClient("http://124.223.6.219:7054",null);
//        UserContext userContext = new UserContext();
//        userContext.setAffiliation("Org1");
//        userContext.setMspId("Org1MSP");
//        userContext.setAccount("李伟");
//        userContext.setName("admin");
//        Enrollment enrollment = caClient.enroll("lihua","MetcTSuAhDWB");
//        userContext.setEnrollment(enrollment);
//        FabricClient fabricClient = new FabricClient(userContext);
//        Peer peer0 = fabricClient.getPeer("peer0.org1.example.com","grpcs://peer0.org1.example.com:7051",tlsPeerFilePath);
//        Peer peer1 = fabricClient.getPeer("peer0.org2.example.com","grpcs://peer0.org2.example.com:9051",tlsPeerFilePathAddtion);
//        List<Peer> peers = new ArrayList<>();
//        peers.add(peer0);
//        peers.add(peer1);
//        Orderer order = fabricClient.getOrderer("orderer.example.com","grpcs://orderer.example.com:7050",tlsOrderFilePath);
//        String initArgs[] = {"362422","{\"name\":\"zhangsan\",\"identity\":\"362422\",\"mobile\":\"18910012222\"}"};
//        fabricClient.invoke("mychannel", TransactionRequest.Type.GO_LANG,"basicinfo",order,peers,"save",initArgs);
//    }
}
