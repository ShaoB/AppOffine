package com.levcn.listener;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.levcn.eventbus.EventBusUtils;
import com.levcn.eventbus.EventCode;
import com.levcn.eventbus.EventMessage;
import com.levcn.greendao.entiy.TaskEntity;
import com.levcn.greendao.utils.DaoUtilsStore;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

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
                socket.getOutputStream().write(data);
            }
            LogUtils.eTag("sb", "完整的消息：" + stringBuilder);

            if (stringBuilder.toString().startsWith("{")) {
                TaskEntity taskEntity = new Gson().fromJson(stringBuilder.toString(), TaskEntity.class);

                boolean b = DaoUtilsStore.getInstance().getTaskUtils().insert(taskEntity);
                LogUtils.eTag("sb", "插入：" + b);
                if (b) {
                    EventBusUtils.post(new EventMessage<>(EventCode.INSERT_DATA_SUCCESS));
                } else {
                    ToastUtils.showShort("插入数据失败");
                }
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
                    out.println(data);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
