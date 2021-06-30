package com.lft.bio.chapter01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 目标：客户端发送一行消息，服务端接收一行消息
 */
public class Server {
    
    public static void main(String[] args) {
        System.out.println("==== 服务端启动 ====");
        try {
            // 1. 定义一个 ServerSocket 对象进行服务端的端口注册
            ServerSocket serverSocket = new ServerSocket(9999);
            
            // 2. 监听客户端的 Socket 链接请求
            Socket socket = serverSocket.accept();
            
            // 3. 从 Socket 管道中得到一个字节输入流对象
            InputStream inputStream = socket.getInputStream();
            
            // 4. 把字节输入流包装成一个缓冲字符输入流。
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            
            String msg;
            // 5. 读取一行消息。
            if ((msg = bufferedReader.readLine()) != null) {
                System.out.println("服务端接收到消息为：" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
