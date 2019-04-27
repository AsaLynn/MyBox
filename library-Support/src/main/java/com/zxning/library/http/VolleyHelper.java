package com.zxning.library.http;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * 单例的Volley,volley单例工具类
 */
public class VolleyHelper {
    private static VolleyHelper volleyHelper;
    private static RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private Context mContext;
    private VolleyHelper(Context context) {
        this.mContext = context;
        mRequestQueue = getRequestQueue();
        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache(){
                    private final LruCache<String,Bitmap> cache = new LruCache<String ,Bitmap>(20);
                    @Override
                    public Bitmap getBitmap(String url){
                        return cache.get(url);
                    }
                    @Override
                    public void putBitmap(String url,Bitmap bitmap){
                        cache.put(url,bitmap);
                    }
                });
    }



    /**
     * 获取实例.Instance
     * @param context
     * @return
     */
    public static synchronized VolleyHelper getInstance(Context context){
        if(volleyHelper == null){
            volleyHelper = new VolleyHelper(context);
        }
        return volleyHelper;
    }

    /**
     * 获取请求队列.
     * @return
     */
    private RequestQueue getRequestQueue(){
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }
//-----------------------一下为对外暴漏的方法------------------------
    /**
     * 添加请求到队列里面.
     * @param req
     * @param <T>
     */
    public <T>  void addToRequestQueue(Request<T> req){
        mRequestQueue.add(req);
    }

    /**
     *图片加载实例类.
     * @return
     */
    public ImageLoader getImageLoader() {
        return mImageLoader;
    }

    /**
     * 通过指定tag标记,来取消该网络请求.
     * @param tag
     */
    public void cancelRequestByTag(String tag){
        mRequestQueue.cancelAll(tag);
    }

}
