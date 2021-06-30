package com.lft.bio.chapter06_chat;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 * Class Name:      ServerReaderThread
 * Package Name:    com.lft.io.chapter05
 * <p>
 * Function: 		A {@code ServerReaderThread} object With Some FUNCTION.
 * Date:            2021-06-28 16:44
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class ServerReaderThread extends Thread {
    private Socket socket;
    
    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }
    
    @Override
    public void run() {
        FileOutputStream fileOutputStream = null;
        try {
            // 1. 得到一个数据输入流读取客户端发送过来的数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            // 2. 读取客户端发送过来的消息
            String message;
            while ((message = bufferedReader.readLine()) != null) {
                // 3. 服务端接收到了客户端的消息之后，推送给当前所有在线 Socket
                sendMsgToAllClient(message);
            }
        } catch (IOException e) {
            System.out.println("当前下线了");
            // 从在线 Socket 集合中移除 Socket
            Server.allOnLineSocket.remove(this.socket);
        }
    }
    
    private void sendMsgToAllClient(String message) {
        for (Socket sk : Server.allOnLineSocket) {
            try {
                PrintStream printStream = new PrintStream(sk.getOutputStream());
                printStream.println(message);
                printStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
