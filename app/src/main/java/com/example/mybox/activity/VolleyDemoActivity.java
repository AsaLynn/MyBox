package com.example.mybox.activity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.bean.WeatherInfo;
import com.example.mybox.bean.Weather;
import com.example.mybox.listener.MyImageCache;
import com.example.mybox.utils.UC;
import com.example.mybox.volley.GSONRequest;
import com.zxning.library.tool.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * volley的使用教程.
 */

public class VolleyDemoActivity extends BaseActivity {

    private CharSequence[] items = {"StringRequest的用法", " ImageRequest的用法", "ImageLoader的用法",
            "NetworkImageView的用法", "自定义GsonRequest",
            "JsonObjectRequest的用法", "JsonArrayRequest的用法", "post请求的用法"};
    private RequestQueue requestQueue;
    private String TAG = this.getClass().getName();
    private String httpUrl = "";
    private TextView tv_volley;
    private String url = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    private ImageView iv_volley1;
    private int maxWith;
    private int maxHeight;
    private ImageView iv_volley2;
    private ImageLoader loader;
    private ImageLoader.ImageListener imageListener;
    private NetworkImageView iv_volley3;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_volley_demo);
        view.findViewById(R.id.btn_volley).setOnClickListener(this);
        tv_volley = (TextView) view.findViewById(R.id.tv_volley);
        iv_volley1 = (ImageView) view.findViewById(R.id.iv_volley1);
        iv_volley2 = (ImageView) view.findViewById(R.id.iv_volley2);
        iv_volley3 = (NetworkImageView) view.findViewById(R.id.iv_volley3);
        httpUrl = UC.URL_SERVLET_GET_LOGIN;
        //创建请求队列.
        requestQueue = Volley.newRequestQueue(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_volley) {
            showAlertDialog("volley的使用操作", items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            sendStringRequest();
                            break;
                        case 1:
                            sendImageRequest();
                            break;
                        case 2:
                            sendImageLoder();
                        case 3:
                            sendImageNet();
                            break;
                        case 4:
                            sendGsonRequest();
                            break;
                        case 5:
                            sendJsonObjectRequest();
                            break;
                        case 6:
                            sendJsonArrayRequest();
                            break;
                        case 7:
                            sendRequetByPost();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }

    private void sendRequetByPost() {
        //创建StringRequest.
        //url = "http://litchiapi.jstv.com/api/GetFeeds?column=1&PageSize=5&pageIndex=1&val=100511D3BE5301280E0992C73A9DEC41";
        url = "http://litchiapi.jstv.com/api/GetFeeds";
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: --->" + response);
                tv_volley.setText("post--->" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: --->" + error);
            }
        }) {

            //添加参数.
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("column","1");
                params.put("PageSize","1");
                params.put("pageIndex","1");
                params.put("val","100511D3BE5301280E0992C73A9DEC41");
                return params;
            }

            //出现乱码,重新此方法解决乱码问题.
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    //采用utf-8编码,获取字符串,保持和服务器编码一致.
                    parsed = new String(response.data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        //加入队列
        requestQueue.add(request);
    }

    private void sendJsonArrayRequest() {
        //http://172.16.65.23:/jsonarry.json
        url = UC.IP_URL + "/jsonarry.json";
        JsonArrayRequest arrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i(TAG, "onResponse: --->" + response.toString());
                tv_volley.setText(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: --->" + error);
            }
        });
        requestQueue.add(arrayRequest);
    }

    private void sendJsonObjectRequest() {
        url = "http://www.weather.com.cn/data/sk/101090101.html";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i(TAG, "onResponse: --->" + response.toString());
                try {
                    //tv_volley.setText(response.getString("weatherinfo"));
                    JSONObject weatherinfo = response.getJSONObject("weatherinfo");
                    String city = weatherinfo.getString("city");
                    String cityid = weatherinfo.getString("cityid");
                    tv_volley.setText(city + "--->" + cityid);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: --->" + error);
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void sendGsonRequest() {
        url = "http://www.weather.com.cn/data/sk/101010100.html";
        GSONRequest<Weather> gsonRequest = new GSONRequest<>(url, Weather.class, new Response.Listener<Weather>() {
            @Override
            public void onResponse(Weather response) {
                WeatherInfo dataInfo = response.getWeatherinfo();
                tv_volley.setText(dataInfo.getCity() + "--->" +
                        dataInfo.getCityid() + "--->" +
                        dataInfo.getTemp() + "--->" +
                        dataInfo.getTime());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: --->" + error);
            }
        });
        requestQueue.add(gsonRequest);
    }

    private void sendImageNet() {
        url = "http://litchiapi.jstv.com/Attachs/Article/288229/9ddacfb194074a58be9cf2c9555b1ee3_padmini.JPG";
//                            1. 创建一个RequestQueue对象。
//                            2. 创建一个ImageLoader对象。
        if (loader == null) {
            loader = new ImageLoader(requestQueue, new MyImageCache());
        }
//                            3. 在布局文件中添加一个NetworkImageView控件。
//                            4. 在代码中获取该控件的实例。
        iv_volley3.setDefaultImageResId(R.drawable.ic_launcher);
        iv_volley3.setErrorImageResId(R.mipmap.iv_m_img_face);
//                            5. 设置要加载的图片地址。
        iv_volley3.setImageUrl(url, loader);
        //iv_volley3.setImageUrl(url,loader,null);//ImageURLBuilder,重构图片url
    }

    private void sendImageLoder() {
        url = "http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg";
//                            1. 创建一个RequestQueue对象。
//                            2. 创建一个ImageLoader对象。
        if (loader == null) {
            loader = new ImageLoader(requestQueue, new MyImageCache());
        }
//                            3. 获取一个ImageListener对象。
        if (imageListener == null) {
            imageListener = ImageLoader.getImageListener(iv_volley2, R.drawable.ic_launcher, R.mipmap.iv_m_img_face);
        }
//                            4. 调用ImageLoader的get()方法加载网络上的图片。
        loader.get(url, imageListener);
    }

    private void sendImageRequest() {
        url = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
        maxWith = 0;
        maxHeight = 0;
        //参数:1,地址.2,成功监听.3,指定图片宽.4,高.5,图片质量.6,失败监听.
        ImageRequest imageRequest = new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                iv_volley1.setImageBitmap(response);
            }
        }, maxWith, maxHeight, Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: --->" + error);
            }
        });
        requestQueue.add(imageRequest);
    }

    private void sendStringRequest() {
        //创建StringRequest.
        StringRequest request = new StringRequest(httpUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse: --->" + response);
                tv_volley.setText("StringRequest--->" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i(TAG, "onErrorResponse: --->" + error);
            }
        }) {
            //出现乱码,重新此方法解决乱码问题.
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String parsed;
                try {
                    //采用utf-8编码,获取字符串,保持和服务器编码一致.
                    parsed = new String(response.data, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    parsed = new String(response.data);
                }
                return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        //加入队列
        requestQueue.add(request);
    }
}
