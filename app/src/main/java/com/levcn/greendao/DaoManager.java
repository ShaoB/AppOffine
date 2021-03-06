package com.levcn.greendao;

import android.annotation.SuppressLint;
import android.content.Context;

import com.levcn.BuildConfig;
import com.levcn.greendao.entiy.DaoMaster;
import com.levcn.greendao.entiy.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;


/**
 * @author : shaobin
 * Profile: 创建数据库、创建数据库表、包含增删改查的操作以及数据库的升级
 */
public class DaoManager {
    private static final String DB_NAME = "greendao.db";

    private Context context;

    /**
     * 多线程中要被共享的使用volatile关键字修饰
     */
    @SuppressLint("StaticFieldLeak")
    private volatile static DaoManager manager = new DaoManager();
    private static DaoMaster sDaoMaster;
    @SuppressLint("StaticFieldLeak")
    private static DaoMaster.DevOpenHelper sHelper;
    private static DaoSession sDaoSession;

    /**
     * 单例模式获得操作数据库对象
     */
    public static DaoManager getInstance() {
        return manager;
    }

    private DaoManager() {
        setDebug();
    }

    public void init(Context context) {
        this.context = context;
    }

    /**
     * 判断是否有存在数据库，如果没有则创建
     */
    private DaoMaster getDaoMaster() {
        if (sDaoMaster == null) {
            DbOpenHelper helper = new DbOpenHelper(context, DB_NAME, null);
            sDaoMaster = new DaoMaster(helper.getWritableDatabase());
        }
        return sDaoMaster;
    }

    /**
     * 完成对数据库的添加、删除、修改、查询操作，仅仅是一个接口
     */
    public DaoSession getDaoSession() {
        if (sDaoSession == null) {
            if (sDaoMaster == null) {
                sDaoMaster = getDaoMaster();
            }
            sDaoSession = sDaoMaster.newSession();
        }
        return sDaoSession;
    }

    /**
     * 打开输出日志，默认关闭
     */
    private void setDebug() {
        if (BuildConfig.DEBUG) {
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        }
    }

    /**
     * 关闭所有的操作，数据库开启后，使用完毕要关闭
     */
    public void closeConnection() {
        closeHelper();
        closeDaoSession();
    }

    private void closeHelper() {
        if (sHelper != null) {
            sHelper.close();
            sHelper = null;
        }
    }

    private void closeDaoSession() {
        if (sDaoSession != null) {
            sDaoSession.clear();
            sDaoSession = null;
        }
    }
}
