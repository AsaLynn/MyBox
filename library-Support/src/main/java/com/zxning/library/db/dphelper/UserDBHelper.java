package com.zxning.library.db.dphelper;

import android.content.Context;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.zxning.library.db.dao.UserDao;
import com.zxning.library.db.domain.UserInfo;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * 保存用户信息.
 */
public class UserDBHelper {

    private AndroidDatabaseConnection connection;
    private UserDao mUserDao;
    private Context mContext;

    public UserDBHelper(Context mContext) {
        this.mContext = mContext;
        mUserDao = new UserDao(mContext);
        connection = new AndroidDatabaseConnection(
                KxdDBHelper.getHelper(mContext).getWritableDatabase(), true);
    }

    public int saveUser(UserInfo user) {
        int flag = -1;//保存失败
        Savepoint savepoint = null;
        try {
            boolean byUsername = mUserDao.findByUsername(user.username);
            if (byUsername) {
                flag = 0;   //用户名存在
            } else {
                savepoint = connection.setSavePoint("save");
                if (mUserDao.save(user)) {
                    flag = 1;//保存成功.
                    connection.commit(savepoint);
                }
            }
        } catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback(savepoint);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        return flag;
    }

    public boolean updateUser(UserInfo user) {
        return mUserDao.update(user);
    }

    public boolean deleteUser(UserInfo user) {
        return mUserDao.delete(user);
    }

    public UserInfo getUserById(long id) {
        return mUserDao.findOneById(id);
    }

    public List<UserInfo> getAll() {
        List<UserInfo> users = mUserDao.findAll();
        return users;
    }

    public long countAll() {
        return mUserDao.countAll();
    }

    public List<UserInfo> getAllByLimit(long currentPage, long size) {
        return mUserDao.findAllByLimit(currentPage - 1, size);
    }

    public boolean getByUsername(String username) {
        return mUserDao.findByUsername(username);
    }

    public UserInfo getOneByName(String name) {
        UserInfo user = mUserDao.findOneByName(name);
        return user;
    }

   /* public List<UserInfo> getAllUserOnlyUsernamePassword() {
        return mUserDao.findUNP();
    }*/

}
