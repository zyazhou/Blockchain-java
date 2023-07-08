package com.blockchain.common.lang;

import com.blockchain.fabric.sdk.SdkMain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Const {

	public final static String CAPTCHA_KEY = "captcha";

	public final static Integer STATUS_ON = 0;
	public final static Integer STATUS_OFF = 1;

	public static final String DEFULT_PASSWORD = "11111";
	public static final String DEFULT_AVATAR = "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg";



	public static final String keyFolderPath = "/opt/javaproject/src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/keystore";
	public static final String keyFileName="df46a978487527f0614be6ca922859ce44276950afb4d4e4dd854b1e30d75594_sk";
	public static final String certFoldePath="/opt/javaproject/src/main/resources/crypto-config/peerOrganizations/org1.example.com/users/Admin@org1.example.com/msp/admincerts";
	public static final String certFileName="Admin@org1.example.com-cert.pem";

	public static final String org2keyFolderPath = "/opt/javaproject/src/main/resources/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/keystore";
	public static final String org2keyFileName="a976d19d36894512f47f5b01214811f07c3219f64d05129d9ddaf05ad99d6c29_sk";
	public static final String org2certFoldePath="/opt/javaproject/src/main/resources/crypto-config/peerOrganizations/org2.example.com/users/Admin@org2.example.com/msp/admincerts";
	public static final String org2certFileName="Admin@org2.example.com-cert.pem";

	public static  final String tlsOrderFilePath = "/opt/javaproject/src/main/resources/crypto-config/ordererOrganizations/example.com/tlsca/tlsca.example.com-cert.pem";

	//private static final String txfilePath = "C:/javaweb/project/Blockchain-java/src/main/resources/test.tx";

	public static  final String tlsPeerFilePath = "/opt/javaproject/src/main/resources/crypto-config/peerOrganizations/org1.example.com/peers/peer0.org1.example.com/msp/tlscacerts/tlsca.org1.example.com-cert.pem";

	//另一个组织机构
	public static  final String tlsPeerFilePathAddtion = "/opt/javaproject/src/main/resources/crypto-config/peerOrganizations/org2.example.com/peers/peer0.org2.example.com/msp/tlscacerts/tlsca.org2.example.com-cert.pem";

	//public static final String uploadimage="C:/javaweb/project/Blockchain-java/src/main/resources/static/images/";

	public static final String ip="http://124.223.6.219"; //图片的地址
	public static final String port="8081";
	//public static final String uploadimage="..../resources/static/images/";

}





//package com.blockchain.common.lang;
//
//		import com.blockchain.fabric.sdk.SdkMain;
//		import org.slf4j.Logger;
//		import org.slf4j.LoggerFactory;
//
//public class Const {
//
//	public final static String CAPTCHA_KEY = "captcha";
//
//	public final static Integer STATUS_ON = 0;
//	public final static Integer STATUS_OFF = 1;
//
//	public static final String DEFULT_PASSWORD = "11111";
//	public static final String DEFULT_AVATAR = "https://image-1300566513.cos.ap-guangzhou.myqcloud.com/upload/images/5a9f48118166308daba8b6da7e466aab.jpg";
//
//
//
//	public static final String keyFolderPath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\keystore";
//	public static final String keyFileName="df46a978487527f0614be6ca922859ce44276950afb4d4e4dd854b1e30d75594_sk";
//	public static final String certFoldePath="C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\users\\Admin@org1.example.com\\msp\\admincerts";
//	public static final String certFileName="Admin@org1.example.com-cert.pem";
//
//	public static final String org2keyFolderPath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org2.example.com\\users\\Admin@org2.example.com\\msp\\keystore";
//	public static final String org2keyFileName="a976d19d36894512f47f5b01214811f07c3219f64d05129d9ddaf05ad99d6c29_sk";
//	public static final String org2certFoldePath="C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org2.example.com\\users\\Admin@org2.example.com\\msp\\admincerts";
//	public static final String org2certFileName="Admin@org2.example.com-cert.pem";
//
//	public static  final String tlsOrderFilePath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\ordererOrganizations\\example.com\\tlsca\\tlsca.example.com-cert.pem";
//
//	//private static final String txfilePath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\test.tx";
//
//	public static  final String tlsPeerFilePath = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org1.example.com\\peers\\peer0.org1.example.com\\msp\\tlscacerts\\tlsca.org1.example.com-cert.pem";
//
//	//另一个组织机构
//	public static  final String tlsPeerFilePathAddtion = "C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\crypto-config\\peerOrganizations\\org2.example.com\\peers\\peer0.org2.example.com\\msp\\tlscacerts\\tlsca.org2.example.com-cert.pem";
//
//	//public static final String uploadimage="C:\\javaweb\\project\\Blockchain-java\\src\\main\\resources\\static\\images\\";
//
//	public static final String ip="http://localhost";
//	public static final String port="8081";
//	//public static final String uploadimage="..../resources/static/images/";
//
//}






