package com.example.mybox.utils;

import java.net.URLEncoder;

/**
 *
 */

public interface UC {
    //     String IP_URL = "http://172.16.39.48";
//     String IP_URL = "http://172.16.42.8";
    String IP_URL = "http://172.16.65.23";
    //     String IP_URL = "http://192.168.1.102";
//     String IP_URL = "http://192.168.43.59:";
    String URL_SERVLET_POST_LOGIN = IP_URL + "/WebHigh01/servlet/Server1Servlet";
    String URL_SERVLET_GET_LOGIN = UC.URL_SERVLET_POST_LOGIN + "?name=" + URLEncoder.encode("张三") + "&pass=" + URLEncoder.encode("123");
}
