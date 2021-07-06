package com.levcn.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.levcn.greendao.entiy.DaoMaster;
import com.levcn.greendao.entiy.TaskEntityDao;

/**
 * @author : shaobin
 * Profile:
 */
public class DbOpenHelper extends DaoMaster.DevOpenHelper {

    DbOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //切记不要调用super.onUpgrade(db,oldVersion,newVersion)
        if (oldVersion < newVersion) {
            MigrationHelper.migrate(db, TaskEntityDao.class);
        }
    }
}
