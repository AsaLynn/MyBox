package com.zxning.library.tool;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
    private static final int STROKE_WIDTH = 4;

    //从assets资源中获取图片
    public static Bitmap getBitmap(Context context, String filename) {


        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {

            InputStream is = am.open(filename);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static String getTextFromStream(InputStream is){
        int len = 0;
        byte[] b = new byte[1024];
        //定义一个字节数组输出流，保存每次读取到的字节
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            while((len = is.read(b)) != -1){
                bos.write(b, 0, len);
            }
            //使用哪个码表来构造这个字符串
            String text = new String(bos.toByteArray());
            return text;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
