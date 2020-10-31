package org.mindidea.basic;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 多路复用：单线程
 */
public class SocketMultiplexingSingleThreadV1 {

    private ServerSocketChannel server = null;
    private Selector selector = null;
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));
            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        initServer();
        System.out.println("server is start ....");
        try {
            while (true) {
                while (selector.select(5) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            System.out.println("接受到用户发送的数据");
                            readHandler(key);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void acceptHandler(SelectionKey key) {
        try {
            ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
            SocketChannel client = ssc.accept();
            client.configureBlocking(false);

            ByteBuffer buffer = ByteBuffer.allocate(8192);
            client.register(selector, SelectionKey.OP_READ, buffer);

            System.out.println("----------------------------------------");
            System.out.println("new client: " + client.getRemoteAddress());
            System.out.println("----------------------------------------");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readHandler(SelectionKey key) {
        SocketChannel client = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.clear();
        int read = 0;
        try {
            while (true) {
                read = client.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        client.write(buffer);
                    }
                    buffer.clear();
                } else if (read == 0) {
                    break;
                } else {
                    client.close();
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketMultiplexingSingleThreadV1 smst = new SocketMultiplexingSingleThreadV1();
        smst.start();
    }
}
