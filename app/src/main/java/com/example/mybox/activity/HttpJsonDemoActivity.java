package com.example.mybox.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.mybox.R;
import com.example.mybox.adapter.JsonListAdapter;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.bean.LoginInfo;
import com.example.mybox.bean.SchoolInfo;
import com.example.mybox.bean.StuClzInfo;
import com.example.mybox.utils.UC;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zxning.library.tool.StreamUtils;
import com.zxning.library.tool.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 1,解析JSONObject封装到bean:
 * { "resultCode":"1" , "resultMessage":"登录成功!" }
 * 2,解析JSONArray封装到bean集合:
 * {
 "clazz": [
 {
 "code": "0100",
 "count": 20
 },
 {
 "code": "0101",
 "count": 21
 },
 {
 "code": "0102",
 "count": 22
 },
 {
 "code": "0103",
 "count": 23
 }
 ],
 "school": "积云"
 "school": "积云"
 }
 * 3,解析JSONObject中带有JSONArray的json,封装到bean以及bean中的集合中:
 * [
 {
 "code": "500",
 "count": 30
 },
 {
 "code": "501",
 "count": 31
 },
 {
 "code": "502",
 "count": 32
 },
 {
 "code": "503",
 "count": 33
 }
 ]
 4,gson使用步骤
01,app中得build.gradle中配置dependencies {compile 'com.google.code.gson:gson:2.8.1'}后同步.
 02,bean中定义得字段名称必须和json得key保持一致.
 03,bean中必须定义无参构造.
 5,fastjson使用步骤
 01,app中得build.gradle中配置dependencies {compile 'com.alibaba:fastjson:1.2.37'}后同步.
 02,bean中定义得字段名称必须和json得key保持一致.
 03,bean中必须定义无参构造.
 */

public class HttpJsonDemoActivity extends BaseActivity {

    private String[] itemList = {"手动解析JSONObject", "GSON解析JSONObject", "FASTJSON解析JSONObject",
            "手动解析JSONArray", "GSON解析JSONArray", "FASTJSON解析JSONArray",
            "手动解析JSONObject带JSONArray", "GSON解析JSONObject带JSONArray", "FASTJSON解析JSONObject带JSONArray"};
    private String path = "";
    private TextView tv_type;

    //弹出
    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("json解析方式");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setItems(itemList, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (0 == which || 1 == which || 2 == which) {
                    path = UC.IP_URL + "/WebHigh01/servlet/Server1Servlet";//jsonobject
                } else if (3 == which || 4 == which || 5 == which) {
                    path = UC.IP_URL + "/WebHigh01/servlet/Server3Servlet";//jsonarray
                } else if (6 == which || 7 == which || 8 == which) {
                    path = UC.IP_URL + "/WebHigh01/servlet/Server2Servlet";//jsonobjarr.
                }
                request(which);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private TextView tv_name;
    private ListView lv_content;


    @Override
    protected void showTitle() {
        initToolbar(true, "json解析");
    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_http_json_demo);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_type = (TextView) view.findViewById(R.id.tv_type);
        lv_content = (ListView) view.findViewById(R.id.lv_content);
        view.findViewById(R.id.btn1).setOnClickListener(this);
        return view;
    }

    private void request(final int which) {
        new Thread() {
            @Override
            public void run() {
                String name = "张三";
                String pass = "123";
                //URLEncoder.encode(name);编码.,,
                //String path = ipURL + "/WebHigh01/servlet/Server2Servlet";
                //path = ipURL + "/WebHigh01/servlet/Server3Servlet";
                HttpURLConnection urlConnection = null;
                try {
                    URL url = new URL(path);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    String content = "name=" + URLEncoder.encode(name) + "&pass=" + URLEncoder.encode(pass);
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", content.length() + "");
                    //开启请求流,获取请求流,写入流.
                    urlConnection.setDoOutput(true);
                    OutputStream outputStream = urlConnection.getOutputStream();
                    outputStream.write(content.getBytes());
                    if (urlConnection.getResponseCode() == 200) {
                        InputStream inputStream = urlConnection.getInputStream();
                        final String text = StreamUtils.getTextFromStream(inputStream);
                        HttpJsonDemoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                tv_type.setText(itemList[which]);
                                Log.i(this.getClass().getName(),text);
                                if (which == 0) {
                                    parseJson1(text);
                                } else if (which == 1) {
                                    parseJson2(text);
                                } else if (which == 2) {
                                    parseJson3(text);
                                } else if (which == 3) {
                                    parseAarray1(text);
                                } else if (which == 4) {
                                    parseAarray2(text);
                                } else if (which == 5) {
                                    parseAarray3(text);
                                } else if (which == 6) {
                                    parseJsonAarray1(text);
                                } else if (which == 7) {
                                    parseJsonAarray2(text);
                                } else if (which == 8) {
                                    parseJsonAarray3(text);
                                }
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();
    }

    //fastjson解析json数组
    private void parseAarray3(String text) {

        List<StuClzInfo> list = JSON.parseArray(text, StuClzInfo.class);
        tv_name.setText("fastjson解析数组字符串");
        JsonListAdapter adapter = new JsonListAdapter(list);
        lv_content.setAdapter(adapter);
    }

    //gson解析json数组
    private void parseAarray2(String text) {
        tv_name.setText("gson解析数组字符串");
        Gson gson = new Gson();
        Type type = new TypeToken<List<StuClzInfo>>() {}.getType();
        List<StuClzInfo> list = gson.fromJson(text, type);

        JsonListAdapter adapter = new JsonListAdapter(list);
        lv_content.setAdapter(adapter);
    }

    //手动解析json数组.
    private void parseAarray1(String text) {
        ArrayList<StuClzInfo> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(text);
            for (int i = 0; i < array.length(); i++) {
                StuClzInfo info = new StuClzInfo();
                JSONObject object = array.getJSONObject(i);
                info.setCode(object.getString("code"));
                info.setCount(object.getInt("count"));
                list.add(info);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        tv_name.setText("手动解析数组字符串");
        JsonListAdapter adapter = new JsonListAdapter(list);
        lv_content.setAdapter(adapter);

    }

    //fastjson解析对象中json数组.
    private void parseJsonAarray3(String text) {
        List<StuClzInfo> list = new ArrayList<>();
        SchoolInfo info = JSON.parseObject(text, SchoolInfo.class);
        tv_name.setText(info.getResultCode() + "--" + info.getSchool());
        list = info.getClazz();
        JsonListAdapter adapter = new JsonListAdapter(list);
        lv_content.setAdapter(adapter);
    }

    //GSON解析对象中json数组.
    private void parseJsonAarray2(String text) {
        List<StuClzInfo> list = new ArrayList<>();
        try {
            Gson gson = new Gson();
            SchoolInfo info = gson.fromJson(text, SchoolInfo.class);
            tv_name.setText(info.getResultCode() + "--" + info.getSchool());
            list = info.getClazz();
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonListAdapter adapter = new JsonListAdapter(list);
        lv_content.setAdapter(adapter);
    }

    //手动解析对象中json数组.
    private void parseJsonAarray1(String text) {
        try {
            JSONObject object = new JSONObject(text);
            String resultCode = object.getString("resultCode");
            if ("1".equals(resultCode)) {
                tv_name.setText(object.getString("school"));
                //object.getString("")
                JSONArray clazz = object.getJSONArray("clazz");
                List<StuClzInfo> list = new ArrayList<>();
                for (int i = 0; i < clazz.length(); i++) {
                    JSONObject o = clazz.getJSONObject(i);
                    StuClzInfo info = new StuClzInfo();
                    info.setCode(o.getString("code"));
                    info.setCount(o.getInt("count"));
                    list.add(info);
                }
                JsonListAdapter adapter = new JsonListAdapter(list);
                lv_content.setAdapter(adapter);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //手动
    private void parseJson1(String text) {

        try {
            JSONObject jsonObject = new JSONObject(text);
            String code = jsonObject.getString("resultCode");
            String message = jsonObject.getString("resultMessage");
            UIUtils.showMsg(code + "---" + message);
            tv_name.setText(code + "---" + message+"--->手动");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //GSON
    private void parseJson2(String text) {
        // 将json 转化成 java 对象
        //fromJson方法。参数一是json字符串。参数二是要转换的javabean
        //该javabean的字段名必须与json的key名字完全对应才能被正确解析。
        Gson gson = new Gson();
        LoginInfo loginInfo = gson.fromJson(text, LoginInfo.class);
        UIUtils.showMsg("Gson---" + loginInfo.getResultCode() + "---" + loginInfo.getResultMessage());
        tv_name.setText("Gson---" + loginInfo.getResultCode() + "---" + loginInfo.getResultMessage()+"--->gson");
    }

    //fastjson.
    private void parseJson3(String text) {
        // 将json 转化成 java 对象.必须定义无参构造.
        //该javabean的字段名必须与json的key名字完全对应才能被正确解析。
        try {
            LoginInfo loginInfo1 = JSON.parseObject(text, LoginInfo.class);
            UIUtils.showMsg("fastjson---" + loginInfo1.getResultCode() + "---" + loginInfo1.getResultMessage());
            tv_name.setText("fastjson---" + loginInfo1.getResultCode() + "---" + loginInfo1.getResultMessage()+"--->fastjson");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                showDialog();
                break;
        }
    }
}
