package com.example.mybox.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.bean.NewsInfo;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * 普通的ListView加载网络图片.
 */

public class MyLvAdapter extends BaseAdapter {

    private List<NewsInfo> mList;
    private Resources mParentResources;
    private ViewGroup mParent;

    public MyLvAdapter(List<NewsInfo> list) {
        mList = list;
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
        if (mParentResources == null) {
            mParentResources = parent.getResources();
        }
        if (mParent == null) {
            mParent = parent;
        }
        ViewHolder viewHolder = null;
        if (null == convertView) {
            convertView = View.inflate(parent.getContext(), R.layout.item_lv_demo, null);
            viewHolder = new ViewHolder();
            viewHolder.tv1 = (TextView) convertView.findViewById(R.id.tv_subject);
            viewHolder.tv2 = (TextView) convertView.findViewById(R.id.tv_summary);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.iv_news);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.tv1.setText(mList.get(position).getSubject());
        viewHolder.tv2.setText(mList.get(position).getSummary());
        viewHolder.imageView.setTag(mList.get(position).getCover());
        new ImageTsak().execute(mList.get(position).getCover());
        return convertView;
    }

    class ImageTsak extends AsyncTask<String, Void, BitmapDrawable> {


        private String imageUrl;

        @Override
        protected BitmapDrawable doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downLoadImage();
            BitmapDrawable bitmapDrawable = new BitmapDrawable(mParentResources, bitmap);
            return bitmapDrawable;
        }

        private Bitmap downLoadImage() {
            HttpURLConnection connection = null;
            Bitmap bitmap = null;
            try {
                URL url = new URL("http://litchiapi.jstv.com" + imageUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5 * 1000);
                connection.setReadTimeout(5 * 1000);
                InputStream inputStream = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != connection) {
                    connection.disconnect();
                }
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(BitmapDrawable bitmapDrawable) {
            ImageView imageView = (ImageView) mParent.findViewWithTag(imageUrl);
            if (bitmapDrawable != null && null != imageView) {
                imageView.setImageDrawable(bitmapDrawable);
            }
        }
    }

    class ViewHolder {
        TextView tv1, tv2;
        ImageView imageView;
    }
}
