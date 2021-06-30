package com.lft.bio.chapter06_chat;

import com.lft.bio.chapter05.ServerReaderThread;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 目标：BIO 模式下的端口转发思想-服务端实现
 * 服务端实现的需求：
 * *    1. 注册端口
 * *    2. 接收客户端的 Socket 连接，交给一个独立的线程来处理。
 * *    3. 把当前连接的客户端 Socket 存入到一个所谓的在线 Socket 集合中保存
 * *    4. 接收客户端的消息，然后推送给当前所有在线的 Socket 接收。
 * @author Laifengting
 */
public class Server {
    public static List<Socket> allOnLineSocket = new ArrayList<>();
    
    public static void main(String[] args) {
        System.out.println("==== 服务端启动 ====");
        try {
            ServerSocket serverSocket = new ServerSocket(9999);
            while (true) {
                Socket socket = serverSocket.accept();
                // 把登录的客户端 Socket 存入到一个在线集合中去。
                allOnLineSocket.add(socket);
                // 交给一个独立的线程来处理与这个客户端的文件通信需求
                new ServerReaderThread(socket).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
