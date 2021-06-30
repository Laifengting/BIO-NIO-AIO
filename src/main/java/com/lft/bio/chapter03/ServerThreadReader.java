package com.lft.bio.chapter03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Administrator
 */
public class ServerThreadReader extends Thread {
    
    private Socket socket;
    
    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
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
