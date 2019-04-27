package com.zxning.library.db.dphelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.zxning.library.constant.Name;
import com.zxning.library.tool.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 用于打开在assets目录下的数据库文件.
 */
public class OutlayDBHelper {
    private static String TOP_PATH = "data/data/";
    //private static String TOP_PATH = "databases/";
    //数据库存储路径
    private static String filePath;
    //数据库存放的文件夹 data/data/包名下面
    public static String pathStr;
    //private static SQLiteDatabase db;

    public static String getDbFilePath(Context context, String dbName) {
        if (TextUtils.isEmpty(pathStr)) {
            filePath = TOP_PATH + context.getPackageName() + dbName;
        }
        return filePath;
    }

    public static String getDbPath(Context context) {
        if (TextUtils.isEmpty(pathStr)) {
            pathStr = TOP_PATH + context.getPackageName();
        }
        return pathStr;
    }

    //将数据库文件从assets目下复制到sd卡上.
    public static void copyDbFromAssetsToSdCard(Context context, String name) {

        File jhPath = new File(getDbFilePath(context, name));
        if (!jhPath.exists()) {
            if (TextUtils.isEmpty(pathStr)) {
                pathStr = TOP_PATH + context.getPackageName();
            }
            File path = new File(pathStr);
            path.mkdir();
        }

        try {
            //得到资源
            AssetManager am = context.getAssets();
            //得到数据库的输入流
            InputStream is = am.open("zxn_app.db");
            //用输出流写到SDcard上面
            FileOutputStream fos = new FileOutputStream(jhPath);
            //创建byte数组  用于1KB写一次
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = is.read(buffer)) > 0) {
                fos.write(buffer, 0, count);
            }
            fos.flush();
            fos.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //将数据库文件复制到sd卡后,在sd卡上读取数据库文件.
    public static SQLiteDatabase openDatabaseOnAssets(Context context, String dbName) {
        copyDbFromAssetsToSdCard(context, dbName);
        //打开写到sd卡上的数据库文件.
        return openDatabaseOnSDCrad(context, dbName);
    }

    //打开写到sd卡上的数据库文件.
    public static SQLiteDatabase openDatabaseOnSDCrad(Context context, String dbName) {
        if (TextUtils.isEmpty(filePath)) {
            filePath = FileUtils.getFileDirectory(Name.DB.DATA_BASES, dbName);
        }
        File jhPath = new File(filePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            //copyDbFromAssetsToSdCard(context, dbName);
            try {
                FileUtils.getFileFromAssetToSDcard(context,Name.DB.DATA_BASES,dbName);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabaseOnSDCrad(context, dbName);
        }
    }

    //打开sd卡上-->
    /*public static SQLiteDatabase openDatabaseOnSdData(Context context, String dbName) {
        if (TextUtils.isEmpty(filePath)) {
            filePath = TOP_PATH + context.getPackageName() + File.separator+dbName;
        }
        File jhPath = new File(filePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            copyDbFromAssetsToSdCard(context, dbName);
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabaseOnSDCrad(context, dbName);
        }
    }*/

}
