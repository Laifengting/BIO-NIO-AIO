package com.lft.bio.chapter03;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：实现服务端可以同时接收多个客户端的 Socket 通信需求
 * 思路：是服务端每接收到一个客户端 Socket 请求对象之后都交给一个独立的线程来处理客户端的数据交互需求。
 */
public class Server {
    
    public static void main(String[] args) {
        System.out.println("==== 服务端启动 ====");
        try {
            // 1. 定义一个 ServerSocket 对象进行服务端的端口注册
            ServerSocket serverSocket = new ServerSocket(9999);
            
            // 2. 定义一个死循环，负责不断地接收客户端的 Socket 链接请求
            while (true) {
                Socket socket = serverSocket.accept();
                // 3. 创建一个独立的线程为处理与这个客户端的 Socket 通信需求。
                new ServerThreadReader(socket).start();
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
