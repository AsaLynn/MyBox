package com.example.mybox.retrofit;

import com.example.mybox.bean.PostQueryInfo;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 *
 */

public interface GitHubService {
    //@GET表示请求.
    @GET("users/{user}/repos")//网址下面的子目录   user表示分类，因为子目录只有一点不一样
    Call<ResponseBody> listRepos(@Path("user") String user);


    //@POS-->post请求
    @POST("query")//query网址下面的子目录,@Query("type") String type--参数的键值对.
    Call<PostQueryInfo> search(@Query("type") String type, @Query("postid") String postid);

    //@POS-->post请求,@QueryMap,携带多个参数.
    @POST("query")//query网址下面的子目录
    Call<PostQueryInfo> getPostInfoByPostQueryMap(@QueryMap Map<String, String> options);

    @GET("query")
    Call<PostQueryInfo> getPostInfoByGet(@Query("type") String type, @Query("postid") String postid);
}
