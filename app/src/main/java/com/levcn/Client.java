package com.levcn;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author : shaoBin
 * date   : 2021/7/6 9:48
 * desc   :
 */
public class Client {

    final static Scanner s = new Scanner(System.in);

    public static void main(String[] args) {
        OutputStream out = null;
        Socket socket = null;
        try {
            //绑定到本地端口
            socket = new Socket("127.0.0.1", 10081);
            //发送消息
            while (true) {
                System.out.println("==========");
                out = socket.getOutputStream();
                //输入文字，从控制台输入
                Scanner san = new Scanner(System.in);
                String str = san.next();
                out.write(str.getBytes());
                out.flush();
                //接收信息
                InputStream in = socket.getInputStream();
                //获取输入流里面数据并存储数据
                byte[] b = new byte[1024];
                StringBuffer sb = new StringBuffer();
                int len;
                while ((len = in.read(b))!= -1) {
                    String message = new String(b, 0, len);
                    System.out.println(message);
                    sb.append(message);
                }
                System.out.println("来自服务器的数据:" + sb);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
