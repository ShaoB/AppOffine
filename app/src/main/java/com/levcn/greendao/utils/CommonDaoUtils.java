package com.levcn.greendao.utils;


import com.levcn.greendao.DaoManager;
import com.levcn.greendao.entiy.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

import java.util.List;

/**
 * @author : shaobin
 * Profile:
 */
public class CommonDaoUtils<T> {
    private static final String TAG = CommonDaoUtils.class.getSimpleName();

    private DaoSession daoSession;
    private Class<T> entityClass;
    private AbstractDao<T, Long> entityDao;

    CommonDaoUtils(Class<T> pEntityClass, AbstractDao<T, Long> pEntityDao) {
        DaoManager mManager = DaoManager.getInstance();
        daoSession = mManager.getDaoSession();
        entityClass = pEntityClass;
        entityDao = pEntityDao;
    }

    /**
     * 插入记录，如果表未创建，先创建表
     */
    public boolean insert(T pEntity) {
        boolean flag = entityDao.insert(pEntity) != -1;
        entityDao.insertInTx();
        return flag;
    }

    /**
     * 插入记录，如果表未创建，先创建表
     * 数据存在则替换，数据不存在则插入
     */
    public boolean insertOrReplace(T pEntity) {
        return entityDao.insertOrReplace(pEntity) != -1;
    }

    /**
     * 插入多条数据，在子线程操作
     */
    public boolean insertMulti(final List<T> pEntityList) {
        try {
            daoSession.runInTx(new Runnable() {
                @Override
                public void run() {
                    for (T t : pEntityList) {
                        daoSession.insertOrReplace(t);
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 修改一条数据
     */
    public boolean update(T pEntity) {
        try {
            daoSession.update(pEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除单条记录
     */
    public boolean delete(T pEntity) {
        try {
            //按照id删除
            daoSession.delete(pEntity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除所有记录
     */
    public boolean deleteAll() {
        try {
            daoSession.deleteAll(entityClass);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 查询所有记录
     */
    public List<T> queryAll() {
        return daoSession.loadAll(entityClass);
    }

    /**
     * 根据主键id查询记录
     */
    public T queryById(long key) {
        return daoSession.load(entityClass, key);
    }

    /**
     * 使用native sql进行查询操作
     */
    public List<T> queryByNativeSql(String sql, String[] conditions) {
        return daoSession.queryRaw(entityClass, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     */
    public List<T> queryByQueryBuilder(WhereCondition cond) {
        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        return queryBuilder.where(cond).list();
    }

    /**
     * 使用queryBuilder进行多表查询
     */
    public List<T> queryByQueryBuilderMany(Class<?> entity, Property property, WhereCondition cond) {
        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        queryBuilder.join(entity, property).where(cond);
        return queryBuilder.list();
    }

    public List<T> queryByQueryBuilder(WhereCondition cond1, WhereCondition... cond2) {
        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        return queryBuilder.where(cond1, cond2).list();
    }

    /**
     * 使用queryBuilder进行删除
     */
    public void deleteContentByQueryBuilder(WhereCondition cond) {
        QueryBuilder<T> queryBuilder = daoSession.queryBuilder(entityClass);
        //CacheContentDao.Properties.Cache_key.eq(key);
        queryBuilder.where(cond).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
