package com.blockchain.service;

import com.blockchain.entity.Blockrecordinfo;
import com.blockchain.entity.Blockusageinfo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.bouncycastle.crypto.CryptoException;
import org.hyperledger.fabric.sdk.exception.InvalidArgumentException;
import org.hyperledger.fabric.sdk.exception.ProposalException;
import org.hyperledger.fabric.sdk.exception.TransactionException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 基于区块链的慈善救助平台项目
 * @since 2023-03-19
 */
public interface BlockusageinfoService extends IService<Blockusageinfo> {

    Blockusageinfo queryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException;

    List<Blockusageinfo> queryHistoryById(String id) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException;

    boolean saveid(Blockusageinfo blockusageinfo) throws ProposalException, IOException, NoSuchAlgorithmException, InvocationTargetException, IllegalAccessException, NoSuchMethodException, InvalidArgumentException, TransactionException, InvalidKeySpecException, CryptoException, org.hyperledger.fabric.sdk.exception.CryptoException, ClassNotFoundException, InstantiationException;

}
