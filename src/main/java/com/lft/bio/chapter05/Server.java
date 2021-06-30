package com.lft.bio.chapter05;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：实现客户端上传任意类型的文件数据给服务端保存到磁盘上。
 */
public class Server {
    
    public static void main(String[] args) {
        System.out.println("==== 服务端启动 ====");
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                // 交给一个独立的线程来处理与这个客户端的文件通信需求
                new ServerReaderThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
