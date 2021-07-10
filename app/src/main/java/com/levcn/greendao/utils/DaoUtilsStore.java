package com.levcn.greendao.utils;

import com.levcn.greendao.DaoManager;
import com.levcn.greendao.entiy.RiskEntity;
import com.levcn.greendao.entiy.RiskEntityDao;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.entiy.TaskEntityDao;

/**
 * @author shaobin
 * Profile:存放各个DaoUtils
 */
public class DaoUtilsStore {
    private volatile static DaoUtilsStore instance;
    private CommonDaoUtils<TaskEntity> taskUtil;
    private final CommonDaoUtils<RiskEntity> riskUtils;

    public static DaoUtilsStore getInstance() {
        if (instance == null) {
            synchronized (DaoUtilsStore.class) {
                if (instance == null) {
                    instance = new DaoUtilsStore();
                }
            }
        }
        return instance;
    }

    private DaoUtilsStore() {
        DaoManager mManager = DaoManager.getInstance();

        //任务
        TaskEntityDao taskEntityDao = mManager.getDaoSession().getTaskEntityDao();
        taskUtil = new CommonDaoUtils<>(TaskEntity.class, taskEntityDao);

        //隐患
        RiskEntityDao riskEntityDao = mManager.getDaoSession().getRiskEntityDao();
        riskUtils = new CommonDaoUtils<>(RiskEntity.class, riskEntityDao);

    }

    public CommonDaoUtils<TaskEntity> getTaskUtils() {
        return taskUtil;
    }

    public CommonDaoUtils<RiskEntity> getRiskUtils() {
        return riskUtils;
    }
}
