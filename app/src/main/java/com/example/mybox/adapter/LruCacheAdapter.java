package com.example.mybox.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.bean.NewsInfo;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 *
 */

public class LruCacheAdapter extends BaseAdapter {

    private List<NewsInfo> mList;
    private final LruCache<String, BitmapDrawable> mLruCache;

    private ViewGroup mParent;
    private Resources resources;

    public LruCacheAdapter(List<NewsInfo> list) {
        mList = list;
        //获取应用分配给每个应用的最大内存.
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        //分配内存的1/8给LruCache使用.
        int cacheize = maxMemory / 8;
        //创建LruCache,并重写sizeof方法计算大小.
        mLruCache = new LruCache<String, BitmapDrawable>(cacheize) {
            @Override
            protected int sizeOf(String key, BitmapDrawable value) {
                return value.getBitmap().getByteCount();
            }
        };
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (mParent == null) {
            mParent = parent;
        }
        if (null == resources) {
            resources = parent.getResources();
        }
        ViewHolder holder = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_lv_demo, null);
            holder = new ViewHolder();
            holder.tv1 = (TextView) convertView.findViewById(R.id.tv_summary);
            holder.tv2 = (TextView) convertView.findViewById(R.id.tv_subject);
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_news);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setTag(mList.get(position).getCover());

        holder.tv1.setText(mList.get(position).getSummary());
        holder.tv2.setText(mList.get(position).getSubject());
        if (mLruCache.get(mList.get(position).getCover()) != null) {
            holder.imageView.setImageDrawable(mLruCache.get(mList.get(position).getCover()));
        } else {
            ImageTask task = new ImageTask();
            task.execute(mList.get(position).getCover());
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv1, tv2;
        ImageView imageView;
    }

    class ImageTask extends AsyncTask<String, Void, BitmapDrawable> {
        private String imageUrl;
        //子线程里面的操作．
        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downLoadImageBitmap();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
           if (null == mLruCache.get(imageUrl)){
               mLruCache.put(params[0], bitmapDrawable);
           }
            return bitmapDrawable;
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            //更新ui
            ImageView iv = (ImageView) mParent.findViewWithTag(imageUrl);
            if (null != bitmapDrawable && null != iv) {
                iv.setImageDrawable(bitmapDrawable);
            }
        }

        private Bitmap downLoadImageBitmap() {
            HttpURLConnection conn = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL("http://litchiapi.jstv.com" + imageUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(5 * 1000);
                conn.setConnectTimeout(5 * 1000);
                if (200 == conn.getResponseCode()) {
                    InputStream inputStream = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (null != conn) {
                    conn.disconnect();
                }
            }
            return bitmap;
        }
    }



}
