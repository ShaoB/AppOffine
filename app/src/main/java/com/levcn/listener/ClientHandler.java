package com.levcn.listener;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.levcn.activity.MainActivity;
import com.levcn.bean.NeedDecisionInfo;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.entiy.TaskEntityDao;
import com.levcn.greendao.utils.CommonDaoUtils;
import com.levcn.greendao.utils.Constants;
import com.levcn.greendao.utils.DaoUtilsStore;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 15:01
 * desc   : 客户端接入监听类
 */
public class ClientHandler {
    /**
     * 数据连接值
     */
    public static final int MAX_DATA_LEN = 1024;
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void start() {
        LogUtils.eTag("sb", "新客户端接入");
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    /**
     * 对客户端的业务处理
     */
    private void doStart() {
        try {
            InputStream inputStream = socket.getInputStream();
            StringBuilder stringBuilder = new StringBuilder();
            // (true) {
            byte[] data = new byte[MAX_DATA_LEN];
            int len;
            while ((len = inputStream.read(data)) != -1) {
                String message = new String(data, 0, len);
                stringBuilder.append(message);
                LogUtils.eTag("sb", "收到客户端消息：" + message);
                //socket.getOutputStream().write(data);
                if ("1".equals(message)) {
                    String s = new Gson().toJson(DaoUtilsStore.getInstance().getTaskUtils().queryByQueryBuilder(TaskEntityDao.Properties.State.eq(Constants.TASK_STATE_NEED_UPDATE)));
                    sendMessage(s);
                    updateData();
                } else {
                    sendMessage("不是一");
                }
            }
            LogUtils.eTag("sb", "完整的消息：" + stringBuilder);

            if (StringUtils.isEmpty(stringBuilder)) {
                return;
            }

            if (stringBuilder.toString().startsWith("{")) {
               /* BaseBean<TaskEntity> baseBean = new Gson().fromJson(stringBuilder.toString(), new TypeToken<BaseBean<List<TaskEntity>>>() {
                }.getType());*/
                NeedDecisionInfo needDecisionInfo = new Gson().fromJson(stringBuilder.toString(), NeedDecisionInfo.class);
                if (needDecisionInfo.isFlag()) {
                    List<TaskEntity> taskEntityList = needDecisionInfo.getData();
                    int count = 0;
                    for (TaskEntity task : taskEntityList) {
                        boolean b = DaoUtilsStore.getInstance().getTaskUtils().insert(task);
                        LogUtils.eTag("sb", "插入：" + b);
                        if (b) {
                            count++;
                        }
                    }
                    if (count == taskEntityList.size()) {
                        EventBusUtils.post(new EventMessage<>(EventCode.INSERT_DATA_SUCCESS));
                    } else {
                        ToastUtils.showShort(taskEntityList.size() - count + "条数据，插入数据失败");
                    }
                } else {
                    ToastUtils.showShort("后台返回数据，flag为false");
                }
            } else {
                ToastUtils.showShort("请检查数据格式");
            }
            //}

        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.eTag("sb", "读取stream失败：" + e.getMessage());
        }
    }

    /**
     * 发送数据给客户端
     */
    public void sendMessage(final String data) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    PrintWriter out = new PrintWriter(new BufferedWriter(
                            new OutputStreamWriter(socket.getOutputStream())), true);
                    LogUtils.eTag("sb", "发送给客户端的数据：" + data);
                    out.println(data);
                    out.flush();
                    //关闭输出流
                    socket.shutdownOutput();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateData() {
        CommonDaoUtils<TaskEntity> taskUtils = DaoUtilsStore.getInstance().getTaskUtils();
        List<TaskEntity> taskEntities = taskUtils.queryByQueryBuilder(TaskEntityDao.Properties.State.eq(Constants.TASK_STATE_NEED_UPDATE));
        int count = 0;
        for (TaskEntity taskEntity : taskEntities) {
            taskEntity.setState(Constants.TASK_STATE_COMPLETE);
            boolean b = taskUtils.update(taskEntity);
            if (b) {
                count++;
            }
        }
        if (count == taskEntities.size()) {
            ToastUtils.showShort("全部上传成功");
        } else {
            ToastUtils.showShort(taskEntities.size() - count + "条数据未上传成功");
        }
        EventBusUtils.post(new EventMessage<>(EventCode.UPDATE_DATA_SUCCESS));
    }
}
