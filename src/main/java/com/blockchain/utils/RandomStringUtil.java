package com.blockchain.utils;

import java.util.Random;

/**
 * 产生随机字符串，长度由参数指定。
 * @paramlength 产生的字符串的长度
 * @return 已产生的字符串
 */
public class RandomStringUtil {

    public static String getRandString(int length)
    {
        String charList = "0123456789";
        //String charList = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String rev = "";
        Random f = new Random();
        for(int i=0;i<length;i++)
        {
            rev += charList.charAt(Math.abs(f.nextInt())%charList.length());
        }
        return rev;
    }
}