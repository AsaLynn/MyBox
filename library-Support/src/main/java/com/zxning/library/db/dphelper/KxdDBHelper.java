package com.zxning.library.db.dphelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.zxning.library.db.domain.CardInfo;
import com.zxning.library.db.domain.UserInfo;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据库帮助类.
 */
public class KxdDBHelper extends OrmLiteSqliteOpenHelper {
    private final static String TAG = KxdDBHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "zxn_db";
    private static final int DATABASE_VERSION = 1;

    private Map<String, Dao> daos = new HashMap<String, Dao>();

    private static KxdDBHelper instance;

    public KxdDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, UserInfo.class);
            TableUtils.createTable(connectionSource, CardInfo.class);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int i, int i2) {
        try {
            TableUtils.dropTable(connectionSource, UserInfo.class, true);
            TableUtils.dropTable(connectionSource, CardInfo.class, true);
            onCreate(sqLiteDatabase, connectionSource);
        } catch (SQLException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 单例获取该Helper
     *
     * @param context
     * @return
     */
    public static synchronized KxdDBHelper getHelper(Context context) {
        context = context.getApplicationContext();
        if (instance == null) {
            synchronized (KxdDBHelper.class) {
                if (instance == null)
                    instance = new KxdDBHelper(context);
            }
        }
        return instance;
    }

    public synchronized Dao getDao(Class clazz) throws SQLException {
        Dao dao = null;
        String className = clazz.getSimpleName();

        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            dao = super.getDao(clazz);
            daos.put(className, dao);
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}
