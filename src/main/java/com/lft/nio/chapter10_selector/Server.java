package com.lft.nio.chapter10_selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 目标：NIO 非阻塞通信下的入门案例：服务端开发
 */
public class Server {
    public static void main(String[] args) throws Exception {
        // 1. 获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        
        // 2. 切换为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        
        // 3. 绑定连接端口
        serverSocketChannel.bind(new InetSocketAddress(8989));
        
        // 4. 获取选择器 Selector
        Selector selector = Selector.open();
        
        // 5. 将通道注册到选择器中。并且开始指定监听接收事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        // 6. 使用 Selector 选择器轮询已经就绪好的事件
        while (selector.select() > 0) {
            // 7. 获取选择器中所有注册的通道中已经就绪好的事件
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            
            // 8. 开始遍历这些准备好的事件
            while (it.hasNext()) {
                // 8.1 提取当前事件
                SelectionKey selectionKey = it.next();
                // 9. 判断这个事件具体是什么？
                if (selectionKey.isAcceptable()) {
                    // 10. 直接获取当前接入的客户端通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 11. 切换成非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12. 将本客户端通道注册到选择器
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    
                } else if (selectionKey.isReadable()) {
                    // 13. 获取当前选择器上的读就绪事件
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 14. 读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int len;
                    while ((len = socketChannel.read(byteBuffer)) > 0) {
                        // 将缓冲区切换到读模式
                        byteBuffer.flip();
                        // 读取使用数据
                        System.out.println(new String(byteBuffer.array(), 0, len));
                        // 清空缓冲区
                        byteBuffer.clear();
                    }
                }
                // 20. 处理完毕之后 需要移除当前事件
                it.remove();
            }
        }
    }
}
