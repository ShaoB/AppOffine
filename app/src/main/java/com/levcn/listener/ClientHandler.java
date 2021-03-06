package com.levcn.listener;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.levcn.base.BaseBean;
import com.levcn.bean.DeviceInfo;
import com.levcn.bean.NeedDecisionInfo;
import com.levcn.bean.ResultBean;
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
import java.util.LinkedList;
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
    private Socket socket;

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

            /*BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String clientInputStr = input.readLine();//这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
            // 处理客户端数据
            System.out.println("客户端发过来的内容:" + clientInputStr);

            if (StringUtils.isEmpty(clientInputStr)) {
                sendMessage("");
            } else {
                judgeData(clientInputStr);
            }*/

            InputStream inputStream = socket.getInputStream();
            byte[] data = new byte[MAX_DATA_LEN];
            int len;
            //这里要注意和客户端输出流的写方法对应,否则会抛 EOFException
            while ((len = inputStream.read(data)) != -1) {
                String message = new String(data, 0, len);
                LogUtils.eTag("sb", "收到客户端消息：" + message);

                if (StringUtils.isEmpty(message)) {
                    sendMessage("");
                } else {
                    judgeData(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.eTag("sb", e);
            LogUtils.eTag("sb", "读取stream失败：" + e.getMessage());
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (Exception e) {
                    socket = null;
                    System.out.println("服务端 finally 异常:" + e.getMessage());
                }
            }
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
                    //out.close();
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

    private void judgeData(String message) {
        if (message.startsWith("{")) {
            BaseBean baseBean = new Gson().fromJson(message, BaseBean.class);
            LogUtils.eTag("sb", new Gson().toJson(baseBean));
            if (baseBean.getAction().equals(Constants.GET_UNIQUE_DEVICE_ID)) {
                DeviceInfo deviceInfo = new DeviceInfo(DeviceUtils.getUniqueDeviceId());
                List<DeviceInfo> list = new LinkedList<>();
                list.add(deviceInfo);
                sendMessage(new Gson().toJson(new ResultBean<DeviceInfo>(true, Constants.GET_UNIQUE_DEVICE_ID, list)));
            } else if (baseBean.getAction().equals(Constants.SEND_BACKLOG)) {
                NeedDecisionInfo needDecisionInfo = new Gson().fromJson(message, NeedDecisionInfo.class);
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
                sendMessage(new Gson().toJson(new ResultBean<>(true, Constants.SEND_BACKLOG, null)));
            }
        } else {
            ToastUtils.showShort("请检查数据格式");
            sendMessage(new Gson().toJson(new ResultBean<>(false, "", null)));
        }
    }
}
