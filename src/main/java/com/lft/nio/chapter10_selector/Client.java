package com.lft.nio.chapter10_selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * 目标：客户端案例实现-基于 NIO 非阻塞通信
 */
public class Client {
    public static void main(String[] args) throws Exception {
        // 1. 获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8989));
        
        // 2. 切换成非阻塞模式
        
        socketChannel.configureBlocking(false);
        
        // 3. 分配指定缓冲区大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        
        // 4. 发送数据给服务端
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("请输入消息：");
            String msg = scanner.nextLine();
            byteBuffer.put(("李四：" + msg).getBytes(StandardCharsets.UTF_8));
            byteBuffer.flip();
            socketChannel.write(byteBuffer);
            byteBuffer.clear();
        }
    }
}
