package com.levcn.service;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.levcn.listener.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

/**
 * @author : shaoBin
 * date   : 2021/7/6 14:52
 * desc   : Server监听端口主服务
 */
public class Server {

    private ServerSocket serverSocket;
    private static List<ClientHandler> mClientHandlerList = new LinkedList<>();

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
        new Thread(() -> doStart()).start();
    }

    /**
     * 等待客户端链接
     */
    private void doStart() {
        while (true) {
            try {
                LogUtils.eTag("sb", "等待客户端链接...");
                //接收连接，如果没有连接，accept() 方法会阻塞
                Socket client = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(client);
                mClientHandlerList.add(clientHandler);
                clientHandler.start();
            } catch (IOException e) {
                LogUtils.eTag("sb", "服务端异常:" + e.toString());
            }
        }
    }

    public static ClientHandler getClientHandler() {
        if (mClientHandlerList.size() > 0) {
            return mClientHandlerList.get(0);
        }
        return null;
    }
}
