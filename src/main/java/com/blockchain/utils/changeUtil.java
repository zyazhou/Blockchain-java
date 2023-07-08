package com.blockchain.utils;

import java.util.HashMap;
import java.util.Map;

public class changeUtil {



    public static void main(String[] args) {
        String str = "{\"projectID\":\"303\",\"userID\":\"180id\",\"userName\":\"周00迎安\",\"usedAmount\":\"800\",\"usedTime\":\"2023-3-18\",\"usedInfo\":\"只要人人都献出一点爱,世界将变成美好的明天!\"}";
// 去掉大括号和引号
        String strWithoutBrackets = str.substring(1, str.length() - 1);
        String[] keyValuePairs = strWithoutBrackets.split(",");
// 创建一个Map，存储键值对
        Map<String, String> map = new HashMap<>();
        for (String pair : keyValuePairs) {
            // 分割键值对
            String[] entry = pair.split(":");
            // 如果值中包含引号，则去掉引号
            String value = entry[1].contains("\"") ? entry[1].substring(1, entry[1].length() - 1) : entry[1];
            // 存储键值对
            map.put(entry[0], value);
        }
        System.out.println(map);

    }
}
