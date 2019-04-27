package com.zxning.library.tool;

/**
 * 处理字符串的工具类.
 */
public class ZStringUtils {

    //银行卡号每四位增加空格.
    public static String getSpaceNumber(String bankNo) {

        String retV = null;
        if (bankNo != null) {
            StringBuffer sb = new StringBuffer(bankNo);
            for (int i = 0; i < 3; i++) {
                sb.insert(4 + 5 * i, " ");
            }
            return retV = sb.toString();
        }
        return retV;
    }
}
