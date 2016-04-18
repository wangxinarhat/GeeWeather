package wang.wangxinarhat.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import wang.wangxinarhat.dao.CITY;

import wang.wangxinarhat.dao.CITYDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig cITYDaoConfig;

    private final CITYDao cITYDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        cITYDaoConfig = daoConfigMap.get(CITYDao.class).clone();
        cITYDaoConfig.initIdentityScope(type);

        cITYDao = new CITYDao(cITYDaoConfig, this);

        registerDao(CITY.class, cITYDao);
    }
    
    public void clear() {
        cITYDaoConfig.getIdentityScope().clear();
    }

    public CITYDao getCITYDao() {
        return cITYDao;
    }

}
