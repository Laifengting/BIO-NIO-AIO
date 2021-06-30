package com.lft.bio.chapter01;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;

/**
 * 客户端 发送一行数据
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("==== 客户端启动 ====");
        try {
            // 1. 创建 Socket 对象请求服务端的链接
            Socket socket = new Socket("127.0.0.1", 9999);
            
            // 2. 从 Socket 对象中获取一个字节输出流。
            OutputStream outputStream = socket.getOutputStream();
            
            // 3. 把字节输出流包装成一个打印流
            PrintStream printStream = new PrintStream(outputStream);
            // 4. 发送一行消息
            printStream.println("Hello World! 服务端。你好！！");
            printStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
