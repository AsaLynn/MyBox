package com.zxning.library.db.dphelper;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/7/20.
 */
public class RegionDBHelper {
    //数据库存储路径
    static String filePath = "data/data/com.jtoushou.myapp/province_city.db";
    //数据库存放的文件夹 data/data/com.main.jh 下面
    static String pathStr = "data/data/com.jtoushou.myapp";
    private static SQLiteDatabase db;
    static String TAG = "REGION_DB_HELPER";

    SQLiteDatabase database;

    public static SQLiteDatabase openDatabase(Context context) {
        System.out.println("filePath:" + filePath);
        File jhPath = new File(filePath);
        //查看数据库文件是否存在
        if (jhPath.exists()) {
            Log.i("test", "存在数据库");
            //存在则直接返回打开的数据库
            return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
        } else {
            //不存在先创建文件夹
            File path = new File(pathStr);
            Log.i("test", "pathStr=" + path);
            if (path.mkdir()) {
                Log.i("test", "创建成功");
            } else {
                Log.i("test", "创建失败");
            }
            ;
            try {
                //得到资源
                AssetManager am = context.getAssets();
                //得到数据库的输入流
                InputStream is = am.open("province_city.db");
                Log.i("test", is + "");
                //用输出流写到SDcard上面
                FileOutputStream fos = new FileOutputStream(jhPath);
                Log.i("test", "fos=" + fos);
                Log.i("test", "jhPath=" + jhPath);
                //创建byte数组  用于1KB写一次
                byte[] buffer = new byte[1024];
                int count = 0;
                while ((count = is.read(buffer)) > 0) {
                    Log.i("test", "得到");
                    fos.write(buffer, 0, count);
                }
                //最后关闭就可以了
                fos.flush();
                fos.close();
                is.close();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            //如果没有这个数据库  我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
            return openDatabase(context);
        }
    }

    public static ArrayList<String> getProvince(Context context) {
        if (null == db) {
            db = openDatabase(context);
        }
        Cursor cursor = db.query("province_city",
                new String[]{"pname"},
                null,
                null,
                "pid",
                null,
                null);
        ArrayList pList = new ArrayList();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("pname"));
            //Log.i(TAG, name + "---");
            pList.add(name);
        }
        cursor.close();
        return pList;
    }

    public static ArrayList<String> getCitys(Context context, String pid) {
        /*public Cursor query(String table,
        String[] columns,
        String selection,
        String[] selectionArgs,
        String groupBy,
         String having,
         String orderBy) {
    table     要查询的表名.
    columns  想要显示的列，若为空则返回所有列，不建议设置为空，如果不是返回所有列
    selection where子句，声明要返回的行的要求，如果为空则返回表的所有行。
    selectionArgs     where子句对应的条件值
    groupBy     分组方式，若为空则不分组.
    having     having条件，若为空则返回全部（不建议）
    orderBy     排序方式，为空则为默认排序方式
    }*/
        // Cursor cursor = db.rawQuery("select * from t_users where id=?", new String[] { id.toString() });
        ArrayList list = new ArrayList();
        if (null == db) {
            db = openDatabase(context);
        }
       /* Cursor cursor = db.query("province_city",
                null,//new String[]{"name"}
                "pid=?",
                new String[]{pid},
                null,
                null,
                null);*/

        Cursor cursor = db.rawQuery("select * from province_city where pid="+pid, null);
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        cursor.close();
        return list;
    }
}
