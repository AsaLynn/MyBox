package com.zxning.library.db.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.zxning.library.db.domain.CardInfo;
import com.zxning.library.db.dphelper.KxdDBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 * 银行卡的数据操作.
 */
public class CardDao implements BaseDao<CardInfo> {
    private Context mContext;
    private KxdDBHelper kxdDBHelper;
    private Dao<CardInfo, Long> mCardLongDao;

    public CardDao(Context mContext) {
        this.mContext = mContext;
        try {
            this.kxdDBHelper = KxdDBHelper.getHelper(mContext);
            this.mCardLongDao = kxdDBHelper.getDao(CardInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean save(CardInfo entity) {
        boolean flag = false;
        try {
            int result = mCardLongDao.create(entity);
            if (result > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean update(CardInfo entity) {
        boolean flag = false;
        try {
            int result = mCardLongDao.update(entity);
            if (result > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean delete(CardInfo entity) {
        boolean flag = false;
        try {
            int result = mCardLongDao.delete(entity);
            if (result > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<CardInfo> findAll() {
        List<CardInfo> infos = null;
        try {
            infos = mCardLongDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return infos;
    }

    @Override
    public CardInfo findOneById(long id) {
        CardInfo user = null;
        try {
            user = mCardLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public long countAll() {
        long count = 0;
        try {
            count = mCardLongDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<CardInfo> findAllByLimit(final long offset, final long limit) {
        List<CardInfo> query = null;
        try {
            query = mCardLongDao.queryBuilder().
                    orderBy("create_date", false).
                    offset(offset).
                    limit(limit).
                    query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 不存在,返回false
     *
     * @param cardNo
     * @return
     */
    public CardInfo findEntryByCardNo(String cardNo) {
        CardInfo info = null;
        try {
            List<CardInfo> users = mCardLongDao.queryForEq("cardNo", cardNo);
            if (users.size() > 0) {
                info = users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    public boolean isExistenceCardNO(String cardNo) {//isExistenceCardNO
        boolean flag = false;
        try {
            List<CardInfo> users = mCardLongDao.queryForEq("cardNo", cardNo);
            if (users.size() > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 这个可以考虑用源生sql查询.
     *
     * @return
     */
    public List<CardInfo> findUNP() {
        GenericRawResults<CardInfo> rawResults = null;
        List<CardInfo> results = null;
        try {
            String sql = "SELECT CardInfoname, password FROM tb_CardInfo";
            RawRowMapper<CardInfo> mapper = new RawRowMapper<CardInfo>() {
                @Override
                public CardInfo mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                    CardInfo info = new CardInfo();
                    info.name = resultColumns[0];
                    info.cardNo = resultColumns[1];
                    return info;
                }
            };
            rawResults = mCardLongDao.queryRaw(sql, mapper);
            results = rawResults.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rawResults != null) {
                try {
                    rawResults.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            return results;
        }
    }
}
