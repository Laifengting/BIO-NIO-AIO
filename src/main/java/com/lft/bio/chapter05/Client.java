package com.lft.bio.chapter05;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 实现客户端上传任意类型的文件数据给服务端保存起来。
 */
public class Client {
    public static void main(String[] args) {
        System.out.println("==== 客户端启动 ====");
        InputStream inputStream = null;
        try {
            // 1. 创建 Socket 对象请求服务端的链接
            Socket socket = new Socket("127.0.0.1", 9999);
            
            // 2. 从 Socket 对象中获取一个字节输出流。
            OutputStream outputStream = socket.getOutputStream();
            
            // 3. 把字节输出流包装成一个数据输出流
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            File file = new File("01.txt");
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            String substring = fileName.substring(index);
            System.out.println("扩展名：" + substring);
            // 4. 先发送上传文件的后缀给服务端
            dataOutputStream.writeUTF(substring);
            
            // 5. 把文件数据发送给服务端进行接收
            inputStream = new FileInputStream(file);
            
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > 0) {
                dataOutputStream.write(buffer, 0, len);
            }
            // 刷新此数据输出流。 这会强制将任何缓冲的输出字节写出到流中。
            dataOutputStream.flush();
            // 通知服务端数据发送完毕了
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
