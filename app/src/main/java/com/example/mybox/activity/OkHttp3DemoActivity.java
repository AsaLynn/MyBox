package com.example.mybox.activity;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.utils.UC;
import com.zxning.library.tool.UIUtils;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/*
特点
1.支持http和https协议,api相同,易用;
2.http使用线程池,https使用多路复用;
3.okhttp支持同步和异步调用;
4.支持普通form和文件上传form;
5.提供了拦截器,操作请求和响应(日志,请求头,body等);
6.okhttp可以设置缓存;

使用
1.创建OkHttpClient对象;
2.设置timeout时间,包括连接超时,读超市,写超时;
3.可选(拦截器);
4.创建RequestBody对象,即请求体;
5.创建Request对象,发送请求;
6.创建Response对象,接受响应内容;

配置
 1,Android Studio 配置gradle：
 compile 'com.squareup.okhttp3:okhttp:3.8.1'
 compile 'com.squareup.okio:okio:1.13.0'
2,添加网络权限：
 <uses-permission android:name="android.permission.INTERNET"/>
 请求方式
 同步：提交请求->等待服务器处理->处理完毕返回 这个期间客户端浏览器不能干任何事
 异步: 请求通过事件触发->服务器处理（这是浏览器仍然可以作其他事情）->处理完毕
 同步就是你叫我去吃饭，我听到了就和你去吃饭；如果没有听到，你就不停的叫，直到我告诉你听到了，才一起去吃饭。
 异步就是你叫我，然后自己去吃饭，我得到消息后可能立即走，也可能等到下班才去吃饭。(某一不确定时间去)
 同步Get请求
 同步Post请求
 call.execute()就是在执行http请求了，但是这是个同步操作，子线程中执行.

异步GET请求
 异步POST请求
 OkHttp3异步POST请求和OkHttp2.x有一些差别就是没有FormEncodingBuilder这个类，替代它的是功能更加强大的FormBody：
 异步上传文件
 异步下载文件
 异步上传Multipart文件
 设置超时时间和缓存
 关于取消请求和封装

synchronous,同步
asynchronous,异步


 */
public class OkHttp3DemoActivity extends BaseActivity {

    private TextView tv;
    private String[] items = {"同步Get请求",
            "同步Post请求",
            "异步Get请求",
            "异步Post请求",
            "异步上传文件",
            "异步Post请求2"};
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    synchronousGet();
                    break;
                case 1:
                    synchronousPost();
                    break;
                case 2:
                    asynchronousGet();
                    break;
                case 3:
                    asynchronousPost();
                    break;
                case 4:
                    asynchronousUpload();
                    break;
                case 5:
                    String path = "http://www.kuaidi100.com/query";
                    //创建okhttp
                    OkHttpClient okHttpClient = new OkHttpClient();
                    //创建请求体,添加参数.
                    RequestBody requestBody = new FormBody.Builder().add("type", "yuantong").add("postid", "500379523313").build();
                    //构建请求.method("POST",requestBody)== post(requestBody)
                    Request request = new Request.Builder().url(path).post(requestBody).build();
                    //创建执行对象,发送请求.
                    Call call = okHttpClient.newCall(request);
                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i(this.getClass().getName(), "--->请求失败" );
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            final String text = response.body().string();
                            Log.i(this.getClass().getName(), "--->" + text);
                            //主线程更新ui
                            OkHttp3DemoActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText("异步POST-->" + text);
                                }
                            });
                        }
                    });
                    break;
            }
        }
    };

    //异步文件上传.
    private void asynchronousUpload() {
        String path = "https://api.github.com/markdown/raw";
        OkHttpClient client = new OkHttpClient();
        File file = new File("/sdcard/1.txt");
        //首先定义上传文件类型
        MediaType contentType = MediaType.parse("text/x-markdown; charset=utf-8");
        RequestBody body = RequestBody.create(contentType, file);
        Request request = new Request.Builder().url(path).post(body).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(this.getClass().getName(),"--->请求失败!");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String msg = response.body().string();
                Log.i(this.getClass().getName(), msg);
                OkHttp3DemoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        UIUtils.showMsg("上传成功!");
                    }
                });
            }
        });
    }

    private void asynchronousPost() {
        String path = UC.IP_URL + "/WebHigh01/servlet/Server1Servlet";
        //创建okhttp
        OkHttpClient okHttpClient = new OkHttpClient();
        //创建请求体,添加参数.
        RequestBody requestBody = new FormBody.Builder().add("name", "张三").add("pass", "123").build();
        //构建请求.method("POST",requestBody)== post(requestBody)
        Request request = new Request.Builder().url(path).post(requestBody).build();
        //创建执行对象,发送请求.
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i(this.getClass().getName(), "--->请求失败" );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String text = response.body().string();
                Log.i(this.getClass().getName(), "--->" + text);
                //主线程更新ui
                OkHttp3DemoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("异步POST-->" + text);
                    }
                });
            }
        });

    }

    private void asynchronousGet() {
//        String url = "https://www.baidu.com/";
        String name = "张三";
        String pass = "123";
        String url = UC.IP_URL + "/WebHigh01/servlet/Server1Servlet?name=" + URLEncoder.encode(name) + "&pass=" + URLEncoder.encode(pass);
        //new OkHttpClient()只是快速创建OkHttpClient的方式，更标准的是使用OkHttpClient.Builder。后者可以设置一堆参数。
        //OkHttpClient okHttpClient = new OkHttpClient();
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        //method("",null)可以省略，默认是GET请求
        Request request = new Request.Builder()
                .url(url).method("GET", null)
                .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            //请求失败
            @Override
            public void onFailure(Call call, IOException e) {
                //使用错误的连接可以演示.
                Log.i(this.getClass().getName(), "-->我是异步请求失败！！");
                e.printStackTrace();
            }

            //成功
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(this.getClass().getName(), "-->我是异步线程,线程Id为:" + Thread.currentThread().getId());
                final String string = response.body().string();

                OkHttp3DemoActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv.setText("异步get请求-->" + string);
                    }
                });
            }
        });

        //我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是异步线程,线程Id为:11
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
//        我是主线程,线程Id为:1
        // onFailure()和onResponse()异步线程执行，不能直接更新UI.

        for (int i = 0; i < 10; i++) {
            Log.i(this.getClass().getName(), "-->我是主线程,线程Id为:" + Thread.currentThread().getId());
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //OkHttp设置携带，保存和更新Cookie,自动管理Cookie
//        private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();
//        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
//                        cookieStore.put(httpUrl.host(), list);
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
//                        List<Cookie> cookies = cookieStore.get(httpUrl.host());
//                        return cookies != null ? cookies : new ArrayList<Cookie>();
//                    }
//                })
//                .build();
    }

    private void synchronousPost() {
        String url = UC.IP_URL + "/WebHigh01/servlet/Server1Servlet";
        OkHttpClient okHttpClient = new OkHttpClient();
        //普通的表单请求体,post请求需要提交一个表单
        //RequestBody的数据格式都要指定Content-Type，常见三种：
        // application/x-www-form-urlencoded 数据是个普通表单,
        // multipart/form-data 数据里有文件,
        // application/json 数据是个json
        //FormBody继承RequestBody，已经指定了数据类型application/x-www-form-urlencoded
        RequestBody body = new FormBody.Builder()
                .add("name", "张三")
                .add("pass", "123")
                .build();

        //如果表单是个json
//        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//        RequestBody body = RequestBody.create(JSON, "你的json");
        //如果数据包含文件
        //MultipartBody也是继承了RequestBody,看下源码可知它适用于这五种Content-Type:
//        public static final MediaType MIXED = MediaType.parse("multipart/mixed");
//        public static final MediaType ALTERNATIVE = MediaType.parse("multipart/alternative");
//        public static final MediaType DIGEST = MediaType.parse("multipart/digest");
//        public static final MediaType PARALLEL = MediaType.parse("multipart/parallel");
//        public static final MediaType FORM = MediaType.parse("multipart/form-data");

//        RequestBody requestBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
//                .build();


        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        final Call call = okHttpClient.newCall(request);
        new Thread() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    final String string = response.body().string();
                    Log.i(this.getClass().getName(), "-->" + string);
                    OkHttp3DemoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText("同步post--->" + string);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    //NetworkOnMainThreadException
    private void synchronousGet() {
        //String url = "https://www.baidu.com/";
        String name = "张三";
        String pass = "123";
        String url = UC.IP_URL + "/WebHigh01/servlet/Server1Servlet?name=" + URLEncoder.encode(name) + "&pass=" + URLEncoder.encode(pass);
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        //请求头添加参数.
//        Request request = new Request.Builder()
//                .url(url)
//                .header("键", "值")
//                .header("键", "值")
//                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread() {
            @Override
            public void run() {
                try {
                    final Response response = call.execute();
                    //请求码,输出.
                    final String msg = response.code() + response.body().string();
                    Log.i(this.getClass().getName(), "--->" + msg);
                    OkHttp3DemoActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tv.setText(msg);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_okhttp_demo);
        view.findViewById(R.id.btn).setOnClickListener(this);
        tv = (TextView) view.findViewById(R.id.tv_content);
        tv.setText("请求结果");
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn) {
            showAlertDialog("okhttp的请求方式", items, listener);
        }
    }
}
