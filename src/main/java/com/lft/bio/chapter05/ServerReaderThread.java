package com.lft.bio.chapter05;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.UUID;

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
            InputStream inputStream = socket.getInputStream();
            DataInputStream dataInputStream = new DataInputStream(inputStream);
            
            // 2. 读取客户端发送过来的文件类型
            String suffix = dataInputStream.readUTF();
            System.out.println("服务端已经成功接收到了文件类型：" + suffix);
            
            String fileName = UUID.randomUUID().toString().replaceAll("-", "") + suffix;
            // 3. 定义一个字节输出管道负责把客户端发来的文件数据写到文件中。
            fileOutputStream = new FileOutputStream("D:\\Server\\" + fileName);
            // 4. 从数据输入流中读取文件数据，写出到字节输出流中去。
            byte[] buffer = new byte[1024];
            int len;
            while ((len = dataInputStream.read(buffer)) > 0) {
                fileOutputStream.write(buffer, 0, len);
            }
            System.out.println("服务端文件保存成功，文件名为：" + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        
    }
}
