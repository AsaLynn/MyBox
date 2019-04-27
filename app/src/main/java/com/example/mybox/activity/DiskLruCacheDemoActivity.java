package com.example.mybox.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mybox.R;
import com.example.mybox.base.BaseActivity;
import com.example.mybox.disk.DiskLruCache;
import com.zxning.library.tool.UIUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 */

public class DiskLruCacheDemoActivity extends BaseActivity {

    private ImageView imageView;
    String[] items = {"0下载网络图片写入缓存", "1读取缓存加载图片", "2移除缓存的图片",
            "3获取缓存数据总字节数","4获取缓存的路径","5最大缓存量",
            "6关闭缓存功能","7删除全部缓存","8重新开启缓存","9图片重置","10取消操作"};
    private DiskLruCache mDiskLruCache;
    String imageUrl = "http://img.my.csdn.net/uploads/201309/01/1378037235_7476.jpg";
    private TextView tv_num;
    private TextView tv_file;
    private TextView tv_max_cache;

    @Override
    protected View initContentView() {
        View view = UIUtils.inflate(R.layout.activity_disk_lru_cache);
        view.findViewById(R.id.btn_select).setOnClickListener(this);
        imageView = (ImageView) view.findViewById(R.id.iv_disk);
        tv_num = (TextView) view.findViewById(R.id.tv_num);
        tv_file = (TextView) view.findViewById(R.id.tv_file);
        tv_max_cache = (TextView) view.findViewById(R.id.tv_mac_cache);
        openDiskLruCache();

        return view;
    }

    private void openDiskLruCache() {
        //1,获取DiskLruCache的实例.
        try {
            File cacheDir = getDiskCacheDir(this, "bitmap");
            //1缓存地址,2应用程序的版本,3缓存文件的数目,4缓存多少字节的数据,通常10mb
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(this), 1, 10 * 1024 * 1024);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_select) {
            showAlertDialog("disklrucache操作", items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            //从网络下载图片,并且写入缓存.
                            if (mDiskLruCache.isClosed()){
                                UIUtils.showMsg("缓存已经关闭,请重新开启!");
                                return;
                            }
                            saveImageToDish();
                            break;
                        case 1:
                            //从本地缓存读取缓存图片,展示到UI.
                            if (mDiskLruCache.isClosed()){
                                UIUtils.showMsg("缓存已经关闭,请重新开启!");
                                return;
                            }
                            getImageFromDish();
                            break;
                        case 2:
                            //从本地缓存移除缓存图片,展示到UI.
                            if (mDiskLruCache.isClosed()){
                                UIUtils.showMsg("缓存已经关闭,请重新开启!");
                                return;
                            }
                            removeImageFromDish();
                            break;
                        case 3:
                            //缓存数据大小.
                            tv_num.setText("缓存数据大小:" + mDiskLruCache.size() + "字节");
                            UIUtils.showMsg("" + mDiskLruCache.size());
                            break;
                        case 4:
                        //缓存的路径:
                        tv_file.setText("缓存路径:" + mDiskLruCache.getDirectory().toString());
                        break;
                        case 5:
                            //最大缓存限制.
                            tv_max_cache.setText("最大缓存限制:"+ mDiskLruCache.maxSize());
                            break;

                        case 6:
                            //关闭缓存功能,此操作通常在activity的,onDestory()中调用.
                            if (!mDiskLruCache.isClosed()) {
                                try {
                                    mDiskLruCache.close();
                                    UIUtils.showMsg("缓存功能关闭!");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                UIUtils.showMsg("缓存已经关闭!");
                            }
                            break;
                        case 7:
                            //删除全部缓存数据,并且关闭缓存.
                            try {
                                mDiskLruCache.delete();
                                tv_num.setText("缓存数据大小:" + mDiskLruCache.size() + "字节");
                                UIUtils.showMsg("删除全部缓存数据.");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case 8:
                            //重新开启.
                            if (mDiskLruCache.isClosed()){
                                openDiskLruCache();
                                imageView.setImageResource(R.mipmap.ic_launcher);
                            }else {
                                UIUtils.showMsg("缓存已经开启!");
                            }
                            break;

                        case 9:
                            //图片重置.
                            imageView.setImageResource(R.mipmap.ic_launcher);
                            break;

                        case 10:
                            //取消操作
                            if (dialog != null) {
                                dialog.dismiss();
                            }
                            break;
                    }
                }
            });
        }
    }

    private void removeImageFromDish() {
        String key = hashKeyForDisk(imageUrl);
        try {
            mDiskLruCache.remove(key);
            UIUtils.showMsg("缓存移除成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //获取缓存中的图片.
    private void getImageFromDish() {

        String key = hashKeyForDisk(imageUrl);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (null != snapshot) {
                //只缓存了1条数据,所有索引取第一条,传0.
                InputStream inputStream = snapshot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveImageToDish() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   String key = hashKeyForDisk(imageUrl);
                    DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                    if (editor != null) {
                        //前面在设置valueCount的时候指定的是1，只缓存了一个文件,所以这里index传0,获取第一个文件的数据.
                        OutputStream outputStream = editor.newOutputStream(0);

                        if (downloadUrlToStream(imageUrl, outputStream)) {
                            editor.commit();//写入提交成功.
                            DiskLruCacheDemoActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    UIUtils.showMsg("缓存成功!");
                                }
                            });
                        } else {
                            //放弃此次写入
                            editor.abort();
                        }
                    }
                    //用于将内存中的操作记录同步到日志文件
                    //比较标准的做法就是在Activity的onPause()方法中去调用一次flush()方法就可以了。
                    mDiskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 因url中含有特殊符合,不规范,根据url生成一个key,将图片的URL进行MD5编码，编码后的字符串肯定是唯一的，
     并且只会包含0-F这样的字符,符合命名规则,
     * @param key
     * @return
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            //处理数据使用指定的 byte 数组更新摘要
            mDigest.update(key.getBytes());
            //完成哈希计算,并转换成
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    /**\
     * //二行制转字符串
     * @param bytes
     * @return
     */
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            //此方法返回的字符串表示的无符号整数参数所表示的值以十六进制（基数为16）.
            //整数的16机制形式.
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    //获取缓存的路径.1,上下文,2,文件后缀名.不同类型的数据进行区分而设定的一个唯一值
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        //当SD卡存在或者SD卡不可被移除的时候
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            ///sdcard/Android/data/<application package>/cache
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            //--手机自身缓存路径/data/data/<application package>/cache
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    //获取应用程序版本号.
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    private boolean downloadUrlToStream(String urlString, OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(), 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
