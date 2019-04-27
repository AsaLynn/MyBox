package com.example.mybox.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */

public class MD5Utils {

    /**
     * 因url中含有特殊符合,不规范,根据url生成一个key,将图片的URL进行MD5编码，编码后的字符串肯定是唯一的，
     并且只会包含0-F这样的字符,符合命名规则,
     * @param key
     * @return
     */
    public static String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            //处理数据使用指定的 byte 数组更新摘要
            mDigest.update(key.getBytes());
            //完成哈希计算,并转换成
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**\
     * //二行制转字符串
     * @param bytes
     * @return
     */
    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            //此方法返回的字符串表示的无符号整数参数所表示的值以十六进制（基数为16）.
            //整数的16机制形式.
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
