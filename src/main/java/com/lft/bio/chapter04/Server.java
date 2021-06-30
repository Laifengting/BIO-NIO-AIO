package com.lft.bio.chapter04;

import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：开发实现伪异步通信架构
 */
public class Server {
    
    public static void main(String[] args) {
        System.out.println("==== 服务端启动 ====");
        try {
            // 1. 注册端口
            ServerSocket serverSocket = new ServerSocket(9999);
            // 2. 定义一个循环接收客户端的 Socket 链接请求
            // 3.1 初始化一个线程池对象
            HandlerSocketServerThreadPool pool = new HandlerSocketServerThreadPool(3, 10);
            while (true) {
                Socket socket = serverSocket.accept();
                // 3. 把 Socket 对象交给一个线程池进行处理。
                // 3.2 把 socket 封装成一个任务对象
                Runnable target = new ServerRunnableTarget(socket);
                // 3.3 交给线程池处理
                pool.execute(target);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
