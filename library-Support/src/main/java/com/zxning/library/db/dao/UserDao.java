package com.zxning.library.db.dao;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.zxning.library.db.domain.UserInfo;
import com.zxning.library.db.dphelper.KxdDBHelper;

import java.sql.SQLException;
import java.util.List;

/**
 *
 */
public class UserDao implements BaseDao<UserInfo> {
    private Context mContext;
    private KxdDBHelper kxdDBHelper;
    private Dao<UserInfo, Long> mUserLongDao;

    public UserDao(Context mContext) {
        this.mContext = mContext;
        try {
            this.kxdDBHelper = KxdDBHelper.getHelper(mContext);
            this.mUserLongDao = kxdDBHelper.getDao(UserInfo.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean save(UserInfo entity) {
        boolean flag = false;
        try {
            int result = mUserLongDao.create(entity);
            if (result > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean update(UserInfo entity) {
        boolean flag = false;
        try {
            int result = mUserLongDao.update(entity);
            if (result > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public boolean delete(UserInfo entity) {
        boolean flag = false;
        try {
            int result = mUserLongDao.delete(entity);
            if (result > 0) {
                flag = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    @Override
    public List<UserInfo> findAll() {
        List<UserInfo> users = null;
        try {
            users = mUserLongDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public UserInfo findOneById(long id) {
        UserInfo user = null;
        try {
            user = mUserLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public long countAll() {
        long count = 0;
        try {
            count = mUserLongDao.countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public List<UserInfo> findAllByLimit(final long offset, final long limit) {
        List<UserInfo> query = null;
        try {
            query = mUserLongDao.queryBuilder().
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
     * @param username
     * @return
     */
    public UserInfo findOneByName(String username) {
        UserInfo info = null;
        try {
            List<UserInfo> users = mUserLongDao.queryForEq("username", username);
            if (users.size() > 0) {
                info = users.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return info;
    }

    public boolean findByUsername(String username) {
        boolean flag = false;
        try {
            List<UserInfo> users = mUserLongDao.queryForEq("username", username);
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
    public List<UserInfo> findUNP() {
        GenericRawResults<UserInfo> rawResults = null;
        List<UserInfo> results = null;
        try {
            String sql = "SELECT UserInfoname, password FROM tb_UserInfo";
            RawRowMapper<UserInfo> mapper = new RawRowMapper<UserInfo>() {
                @Override
                public UserInfo mapRow(String[] columnNames, String[] resultColumns) throws SQLException {
                    UserInfo user = new UserInfo();
                    user.username = resultColumns[0];
                    user.password = resultColumns[1];
                    return user;
                }
            };
            rawResults = mUserLongDao.queryRaw(sql, mapper);
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
