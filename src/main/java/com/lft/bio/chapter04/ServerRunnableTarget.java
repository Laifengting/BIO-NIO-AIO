package com.lft.bio.chapter04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Class Name:      ServerRunnableTarget
 * Package Name:    com.lft.io.chapter04
 * <p>
 * Function: 		A {@code ServerRunnableTarget} object With Some FUNCTION.
 * Date:            2021-06-28 16:23
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class ServerRunnableTarget implements Runnable {
    private Socket socket;
    
    public ServerRunnableTarget(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            // 处理接收到的客户端 Socket
            // 3. 从 Socket 管道中得到一个字节输入流对象
            InputStream inputStream = socket.getInputStream();
            
            // 4. 把字节输入流包装成一个缓冲字符输入流。
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            
            String msg;
            // 5. 阻塞读取消息。
            while ((msg = bufferedReader.readLine()) != null) {
                System.out.println("服务端接收到消息为：" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
