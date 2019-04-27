package com.example.mybox.activity;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.bean.PostQueryInfo;
import com.example.mybox.retrofit.GitHubService;
import com.zxning.library.tool.UIUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 *
 */

public class RetrofitDemoActivity extends BaseActivity {
    private String TAG = this.getClass().getName();
    private TextView textView;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_retrofit_demo);
        view.findViewById(R.id.btn_retrofit).setOnClickListener(this);
        textView = (TextView) view.findViewById(R.id.tv_retrofit);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_retrofit:
                showAlertDialog("", new String[]{"retrofit同步GET", "retrofit同步POST", "retrofit异步GET", "retrofit异步POST", "retrofit异步POSTQueryMap"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                //同步get请求,retrofitSynchronousGet
                                retrofitSynchronousGet();
                                break;
                            case 1:
                                //同步post,synchronous,retrofitSynchronousPost
                                retrofitSynchronousPost();
                                break;
                            case 2:
                                //异步get请求,
                                asynGet();
                                break;
                            case 3:
                                //异步post请求.
                                asynPost();
                                break;
                            case 4:
                                //异步post请求,@QueryMap携带多个参数.
                                //asynGetPostInfoByPostQueryMap
                                asynGetPostInfoByPostQueryMap();
                                break;
                        }
                    }
                });
                break;
        }
    }

    private void asynGetPostInfoByPostQueryMap() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService apiService = retrofit.create(GitHubService.class);
        Map<String,String> map = new HashMap<>();
        map.put("type","yuantong");
        map.put("postid","500379523313");
        Call<PostQueryInfo> infoCall = apiService.getPostInfoByPostQueryMap(map);
        infoCall.enqueue(new Callback<PostQueryInfo>() {
            @Override
            public void onResponse(Call<PostQueryInfo> call, Response<PostQueryInfo> response) {
                Log.i(TAG, "retrofitPOST: ---" + response.body().getNu() + "--->" +
                        response.body().getNu() + "--->" +
                        response.body().getCom());
                textView.setText("retrofitPOST:" + response.body().getNu() + "--->" +
                        response.body().getNu() + "--->" +
                        response.body().getCom());
            }

            @Override
            public void onFailure(Call<PostQueryInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void retrofitSynchronousGet() {
        //http://www.kuaidi100.com/query?type=快递公司代号&postid=快递单号
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService apiService = retrofit.create(GitHubService.class);
        final Call<PostQueryInfo> call = apiService.getPostInfoByGet("yuantong", "500379523313");
        new Thread(){
            @Override
            public void run() {
                try {
                    final Response<PostQueryInfo> infoResponse = call.execute();
                    UIUtils.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            String result = "retrofit同步get--->"+infoResponse.body().getCom()
                                    +"--->"+infoResponse.body().getNu()
                                    +"--->"+infoResponse.body().getMessage();
                            textView.setText(result);
                            Log.i(TAG, "run: --->"+result);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void retrofitSynchronousPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                //添加数据解析ConverterFactory
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService apiService = retrofit.create(GitHubService.class);
        final Call<PostQueryInfo> call = apiService.search("yuantong", "500379523313");
        new Thread() {
            @Override
            public void run() {
                try {
                    final Response<PostQueryInfo> response = call.execute();
                    UIUtils.getHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            String text = "retrofit同步post--->快递公司:" + response.body().getCom() + "快递号:" + response.body().getNu();
                            textView.setText(text);
                            Log.i(TAG, "run: "+text);
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private void asynPost() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.kuaidi100.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService apiService = retrofit.create(GitHubService.class);
        Call<PostQueryInfo> call = apiService.search("yuantong", "500379523313");
        call.enqueue(new Callback<PostQueryInfo>() {
            @Override
            public void onResponse(Call<PostQueryInfo> call, Response<PostQueryInfo> response) {
                Log.i(TAG, "retrofitPOST: ---" + response.body().getNu() + "--->" +
                        response.body().getNu() + "--->" +
                        response.body().getCom());
                textView.setText("retrofitPOST:" + response.body().getNu() + "--->" +
                        response.body().getNu() + "--->" +
                        response.body().getCom());
            }

            @Override
            public void onFailure(Call<PostQueryInfo> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void asynGet() {
        //baseUrl()设置最基本url,也就是http请求的url前缀,可以把项目中重复的前缀用这个来设置
        //.addConverterFactory(GsonConverterFactory.create())是添加Gson数据解析
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        GitHubService service = retrofit.create(GitHubService.class);
        Call<ResponseBody> repos = service.listRepos("octocat");
        repos.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                textView.setText("retrofit异步get" + response.body().source().toString());
                Log.i(TAG, "onResponse: --->" + response.body().source().toString());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
