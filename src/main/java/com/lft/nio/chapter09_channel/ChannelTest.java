package com.lft.nio.chapter09_channel;

import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * Class Name:      ChannelTest
 * Package Name:    com.lft.nio.chapter09_channel
 * <p>
 * Function: 		A {@code ChannelTest} object With Some FUNCTION.
 * Date:            2021-06-30 8:14
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class ChannelTest {
    @Test
    public void write() {
        FileChannel channel = null;
        try {
            // 1. 字节输出流通向目标文件
            FileOutputStream fos = new FileOutputStream("data01.txt");
            
            // 2. 得到字节输出流对应的通道 Channel
            channel = fos.getChannel();
            
            // 3. 分配缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            
            // 4. 向缓冲区中添加数据
            buffer.put("HELLO WORLD".getBytes(StandardCharsets.UTF_8));
            
            // 5. 把缓冲区切换为读模式
            buffer.flip();
            
            // 6. 把缓冲区写入通道中
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                // 7. 关闭通道
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void read() {
        FileChannel channel = null;
        try {
            // 1. 定义一个文件输入流与源文件接通
            FileInputStream fis = new FileInputStream("data01.txt");
            
            // 2. 获取一个文件字节输入流的通道
            channel = fis.getChannel();
            
            // 3. 定义一个缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            
            // 4. 读取数据到缓冲区
            channel.read(buffer);
            // 5. 切换为读模式
            buffer.flip();
            // 6. 读取出缓冲区中数据并输出
            byte[] array = buffer.array();
            System.out.println(new String(array, 0, buffer.limit()));
            System.out.println(new String(array, 0, buffer.remaining()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                // 7. 关闭通道
                try {
                    channel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void copy() {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel isChannel = null;
        FileChannel osChannel = null;
        try {
            // 源文件
            File srcFile = new File("D:\\Server\\01.mp4");
            // 目标文件
            File destFile = new File("D:\\Server\\02.mp4");
            // 得到一个字节输入流
            fis = new FileInputStream(srcFile);
            // 得到一个字节输出流
            fos = new FileOutputStream(destFile);
            
            // 得到文件通道
            isChannel = fis.getChannel();
            osChannel = fos.getChannel();
            
            // 创建一个字节缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            
            while (true) {
                // 必须先清空缓冲区，再写入数据到缓冲区
                buffer.clear();
                // 读取一次数据
                int flag = isChannel.read(buffer);
                if (flag == -1) {
                    break;
                }
                // 已经读取了数据，把缓冲区的模式切换成读械
                buffer.flip();
                // 把数据写出
                osChannel.write(buffer);
            }
            System.out.println("复制完成");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osChannel != null) {
                try {
                    osChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isChannel != null) {
                try {
                    isChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void scatterAndGatherTest() {
        FileInputStream fis = null;
        FileChannel isChannel = null;
        FileOutputStream fos = null;
        FileChannel osChannel = null;
        try {
            // 1. 字节输入流
            fis = new FileInputStream("data01.txt");
            isChannel = fis.getChannel();
            // 2. 字节输出流
            fos = new FileOutputStream("data02.txt");
            osChannel = fos.getChannel();
            
            // 3. 定义多个缓冲区做数据分散
            ByteBuffer buffer1 = ByteBuffer.allocate(2);
            ByteBuffer buffer2 = ByteBuffer.allocate(4);
            ByteBuffer buffer3 = ByteBuffer.allocate(8);
            
            ByteBuffer[] byteBuffers = {buffer1, buffer2, buffer3};
            // 4. 从通道中读取数据分散到各个缓冲区
            while (true) {
                // 先清空每一个缓冲区
                for (ByteBuffer buffer : byteBuffers) {
                    buffer.clear();
                }
                // 读
                long flag = isChannel.read(byteBuffers);
                if (flag == -1) {
                    break;
                }
                // 5. （分散读取）从每个缓冲区查询是否有数据读取到了。
                for (ByteBuffer buffer : byteBuffers) {
                    // 切换到读数据模式
                    buffer.flip();
                    System.out.println(new String(buffer.array(), 0, buffer.limit()));
                }
                // 6. 聚集写入到通道
                osChannel.write(byteBuffers);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osChannel != null) {
                try {
                    osChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isChannel != null) {
                try {
                    isChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    @Test
    public void transferTest() {
        FileInputStream fis = null;
        FileChannel isChannel = null;
        FileOutputStream fos = null;
        FileChannel osChannel = null;
        try {
            // 1. 字节输入流
            fis = new FileInputStream("data01.txt");
            isChannel = fis.getChannel();
            // 2. 字节输出流
            fos = new FileOutputStream("data04.txt");
            osChannel = fos.getChannel();
            // 3. 复制数据
            // osChannel.transferFrom(isChannel, isChannel.position(), isChannel.size());
            isChannel.transferTo(isChannel.position(), isChannel.size(), osChannel);
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (osChannel != null) {
                try {
                    osChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isChannel != null) {
                try {
                    isChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
