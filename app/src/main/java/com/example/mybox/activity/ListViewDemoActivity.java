package com.example.mybox.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.example.mybox.R;
import com.example.mybox.adapter.MyLvAdapter;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.bean.NewsInfo;
import com.zxning.library.tool.UIUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ListView加载网络图片。
 */

public class ListViewDemoActivity extends BaseActivity {

    private ListView listView;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    //更新UI
                    MyLvAdapter adapter = new MyLvAdapter(list);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    };
    private List<NewsInfo> list;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_lv_demo);
        listView = (ListView) view.findViewById(R.id.lv_load_iv);
        initData();
        return view;
    }

    private void initData() {
        OkHttpClient okHttpClient = new OkHttpClient();
//        String httpUrl = "http://litchiapi.jstv.com/api/GetFeeds?column=3&PageSize=20&pageIndex=1&val=100511D3BE5301280E0992C73A9DEC41";
        String httpUrl = "http://litchiapi.jstv.com/api/GetFeeds?column=3&PageSize=20&pageIndex=5&val=100511D3BE5301280E0992C73A9DEC41";
        Request request = new Request.Builder().url(httpUrl).build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                list = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    JSONObject paramz = jsonObject.getJSONObject("paramz");
                    JSONArray feeds = paramz.getJSONArray("feeds");
                    for (int i = 0; i < feeds.length(); i++) {
                        JSONObject feedsJSONObject = feeds.getJSONObject(i);
                        JSONObject data = feedsJSONObject.getJSONObject("data");
                        NewsInfo info = new NewsInfo(data.getString("cover"), data.getString("subject"), data.getString("summary"));
                        list.add(info);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.obtainMessage(0).sendToTarget();
            }
        });
    }
}
