package org.mindidea.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class socketNIO {

    public static void main(String[] args) throws IOException, InterruptedException {
        LinkedList<SocketChannel> clients = new LinkedList<SocketChannel>();
        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(9090));
        ss.configureBlocking(false);
        while (true) {
            Thread.sleep(1000);
            // 不会阻塞
            SocketChannel client = ss.accept();
            if (client == null) {
                System.out.println("1. null.....");
            } else {
                client.configureBlocking(false);
                int port = client.socket().getPort();
                System.out.println("2. client...port: " + port);
                clients.add(client);
            }
            // 可以在堆内或堆外分配
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            for (SocketChannel c: clients) {
                int num = c.read(buffer);
                if (num > 0) {
                    buffer.flip();
                    byte[] aaa = new byte[buffer.limit()];
                    buffer.get(aaa);

                    String b = new String(aaa);
                    System.out.println("3. " + c.socket().getPort() + ": " + b);
                    buffer.clear();
                }
            }
        }
    }
}
