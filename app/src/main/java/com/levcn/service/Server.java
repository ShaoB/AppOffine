package com.levcn.service;

import com.blankj.utilcode.util.LogUtils;
import com.levcn.listener.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author : shaoBin
 * date   : 2021/7/6 14:52
 * desc   : Server监听端口主服务
 */
public class Server {

    private ServerSocket serverSocket;

    /**
     * 监听端口
     *
     * @param port 端口
     */
    public Server(int port) {
        try {
            this.serverSocket = new ServerSocket(port);
            LogUtils.eTag("sb", "服务端启动成功，端口：" + port);
        } catch (IOException e) {
            e.printStackTrace();
            LogUtils.eTag("sb", e.toString());
        }
    }


    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                doStart();
            }
        }).start();
    }

    /**
     * 等待客户端链接
     */
    private void doStart() {
        while (true) {
            try {
                LogUtils.eTag("sb","等待客户端链接...");
                //接收连接，如果没有连接，accept() 方法会阻塞
                Socket client = serverSocket.accept();
                new ClientHandler(client).start();
            } catch (IOException e) {
                LogUtils.eTag("sb", "服务端异常:" + e.toString());
            }
        }
    }
}
