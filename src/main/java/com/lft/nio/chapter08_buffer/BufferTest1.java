package com.lft.nio.chapter08_buffer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Class Name:      BufferTest
 * Package Name:    com.lft.nio.chapter08_buffer
 * <p>
 * Function: 		A {@code BufferTest} object With Some FUNCTION.
 * Date:            2021-06-30 7:39
 * <p>
 * @author Laifengting / E-mail:laifengting@foxmail.com
 * @version 1.0.0
 * @since JDK 8
 */
public class BufferTest1 {
    public static void main(String[] args) {
        // 1. 分配一个新的字节缓冲区。新缓冲区的位置将为零，它的限制将是它的容量，它的标记将是未定义的，并且它的每个元素都将被初始化为零。 它将有一个backing array ，其array offset为零。
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 0
        System.out.println("Position: " + buffer.position());
        // 1024
        System.out.println("Limit: " + buffer.limit());
        // 1024
        System.out.println("Capacity: " + buffer.capacity());
        
        System.out.println("======================================================");
        
        // 2. 此方法将给定源字节数组的全部内容传输到此缓冲区中。
        buffer.put("Hello World".getBytes(StandardCharsets.UTF_8));
        // 11
        System.out.println("Position: " + buffer.position());
        // 1024
        System.out.println("Limit: " + buffer.limit());
        // 1024
        System.out.println("Capacity: " + buffer.capacity());
        
        System.out.println("======================================================");
        // 翻转这个缓冲区（由写状态，变为读状态）。 将限制设置为当前位置，然后将位置设置为零。 如果定义了标记，则将其丢弃。
        buffer.flip();
        // 0
        System.out.println("Position: " + buffer.position());
        // 11
        System.out.println("Limit: " + buffer.limit());
        // 1024
        System.out.println("Capacity: " + buffer.capacity());
        
        System.out.println("======================================================");
        byte[] buf = new byte[5];
        // 此方法将此缓冲区中的字节传输到给定的目标数组中。
        buffer.get(buf);
        System.out.println(new String(buf));
        // 5
        System.out.println("Position: " + buffer.position());
        // 11
        System.out.println("Limit: " + buffer.limit());
        // 1024
        System.out.println("Capacity: " + buffer.capacity());
        
    }
}
