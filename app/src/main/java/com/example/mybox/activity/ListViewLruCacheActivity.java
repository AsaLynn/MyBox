package com.example.mybox.activity;

import android.view.View;
import android.widget.ListView;

import com.example.mybox.R;
import com.example.mybox.adapter.LruCacheAdapter;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.bean.NewsInfo;
import com.zxning.library.tool.UIUtils;

import org.json.JSONArray;
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
 * Created by think on 2017/9/3.
 */

public class ListViewLruCacheActivity extends BaseActivity{

    private ListView lv_load_iv;
    private List<NewsInfo> infos;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_lv_demo);
        lv_load_iv = (ListView) view.findViewById(R.id.lv_load_iv);
        getData();

        return view;
    }

    private void getData() {
        String httpUrl = "http://litchiapi.jstv.com/api/GetFeeds?column=3&PageSize=20&pageIndex=5&val=100511D3BE5301280E0992C73A9DEC41";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(httpUrl).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                infos = new ArrayList<>();
                try {
                    JSONObject object = new JSONObject(result);
                    JSONObject paramz = object.getJSONObject("paramz");
                    JSONArray feeds = paramz.getJSONArray("feeds");
                    for (int i = 0; i < feeds.length(); i++) {
                        NewsInfo info = new NewsInfo();
                        JSONObject feedsJSONObject = feeds.getJSONObject(i);
                        JSONObject data = feedsJSONObject.getJSONObject("data");
                        info.setCover(data.getString("cover"));
                        info.setSubject(data.getString("subject"));
                        info.setSummary(data.getString("summary"));
                        infos.add(info);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                UIUtils.getHandler().post(new Runnable() {
                    @Override
                    public void run() {
                        LruCacheAdapter adapter = new LruCacheAdapter(infos);
                        lv_load_iv.setAdapter(adapter);
                    }
                });
            }
        });
    }
}
