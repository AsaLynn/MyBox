package com.example.mybox.activity;

import android.view.View;
import android.widget.EditText;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.zxning.library.tool.StreamUtils;
import com.zxning.library.tool.UIUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpUrlConnnectionDemoActivity extends BaseActivity {

    private EditText et_name;
    private EditText et_pass;
    private String ipURL = "http://172.16.42.8";//212
//    private String ipURL = "http://172.16.39.48";//209

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(this, R.layout.activity_http_url_connnection_demo);
        view.findViewById(R.id.btn_submit).setOnClickListener(this);
        et_name = (EditText) view.findViewById(R.id.et_name);
        et_pass = (EditText) view.findViewById(R.id.et_pass);
        view.findViewById(R.id.btn_submit_post).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_submit){
            login();
        }else if (view.getId() == R.id.btn_submit_post){
            postLogin();
        }
    }

    private void postLogin() {
        new Thread(){
            @Override
            public void run() {
                String name = et_name.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                //URLEncoder.encode(name);编码.,,
                String path = ipURL+"/WebHigh01/servlet/Server1Servlet";
                try{
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(5000);
                    String content = "name="+URLEncoder.encode(name)+"&pass="+URLEncoder.encode(pass);
                    urlConnection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length",content.length()+"");
                    //开启请求流,获取请求流,写入流.
                    urlConnection.setDoOutput(true);
                    OutputStream outputStream = urlConnection.getOutputStream();
                    outputStream.write(content.getBytes());
                    if (urlConnection.getResponseCode() == 200){
                        InputStream inputStream = urlConnection.getInputStream();
                        final String text = StreamUtils.getTextFromStream(inputStream);
                        HttpUrlConnnectionDemoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                UIUtils.showMsg(text);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }



    private void login() {
        new Thread(){
            @Override
            public void run() {
                String name = et_name.getText().toString().trim();
                String pass = et_pass.getText().toString().trim();
                //URLEncoder.encode(name);编码.
                String path = ipURL+"/WebHigh01/servlet/Server1Servlet?name="+URLEncoder.encode(name)+"&pass="+URLEncoder.encode(pass);
                try{
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    if (urlConnection.getResponseCode() == 200){
                        InputStream inputStream = urlConnection.getInputStream();
                        final String text = StreamUtils.getTextFromStream(inputStream);
                        HttpUrlConnnectionDemoActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                               UIUtils.showMsg(text);
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
