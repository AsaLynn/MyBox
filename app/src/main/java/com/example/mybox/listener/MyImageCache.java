package com.example.mybox.listener;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.toolbox.ImageLoader;

/**
 * Created by think on 2017/9/8.
 */

public class MyImageCache implements ImageLoader.ImageCache {

    private LruCache<String, Bitmap> cache;

    public MyImageCache() {
        //最大缓存缓存图片的大小设置为10M
        int maxSize = 10 * 1024 * 1024;
        //重写此方法计算大小.
        cache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
    }

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}
