package com.zxning.library.db.dphelper;

import android.content.Context;

import com.j256.ormlite.android.AndroidDatabaseConnection;
import com.zxning.library.db.dao.CardDao;
import com.zxning.library.db.domain.CardInfo;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.List;

/**
 * 保存银行卡信息.
 */
public class CardDBHelper {

    private AndroidDatabaseConnection connection;
    private CardDao mCardDao;
    private Context mContext;

    public CardDBHelper(Context mContext) {
        this.mContext = mContext;
        mCardDao = new CardDao(mContext);
        connection = new AndroidDatabaseConnection(
                KxdDBHelper.getHelper(mContext).getWritableDatabase(), true);
    }

    public int saveUser(CardInfo info) {//CardInfo
        int flag = -1;//保存失败
        Savepoint savepoint = null;
        try {
            boolean byUsername = mCardDao.isExistenceCardNO(info.cardNo);
            if (byUsername) {
                flag = 0;   //用户名存在
            } else {
                savepoint = connection.setSavePoint("save");
                if (mCardDao.save(info)) {
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

    public boolean updateUser(CardInfo user) {
        return mCardDao.update(user);
    }

    public boolean deleteUser(CardInfo user) {
        return mCardDao.delete(user);
    }

    public CardInfo getUserById(long id) {
        return mCardDao.findOneById(id);
    }

    public List<CardInfo> getAll() {
        List<CardInfo> cards = mCardDao.findAll();
        return cards;
    }

    public long countAll() {
        return mCardDao.countAll();
    }

    public List<CardInfo> getAllByLimit(long currentPage, long size) {
        return mCardDao.findAllByLimit(currentPage - 1, size);
    }

    public boolean isExistenceCardNO(String cardNo) {//isExistenceCardNO
        return mCardDao.isExistenceCardNO(cardNo);
    }

    public CardInfo getEntryByCardNo(String cardNo) {
        CardInfo user = mCardDao.findEntryByCardNo(cardNo);
        return user;
    }

   /* public List<CardInfo> getAllUserOnlyUsernamePassword() {
        return mCardDao.findUNP();
    }*/

}
