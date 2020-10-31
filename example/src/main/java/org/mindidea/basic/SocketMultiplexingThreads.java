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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 多路复用：多线程
 */
public class SocketMultiplexingThreads {

    private ServerSocketChannel server = null;
    private Selector selector1 = null;
    private Selector selector2 = null;
    private Selector selector3 = null;
    int port = 9090;

    public void initServer() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.bind(new InetSocketAddress(port));

            selector1 = Selector.open();
            selector2 = Selector.open();
            selector3 = Selector.open();
            server.register(selector1, SelectionKey.OP_ACCEPT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SocketMultiplexingThreads service = new SocketMultiplexingThreads();
        service.initServer();

        NioThread t1 = new NioThread(service.selector1, 2);

        NioThread t2 = new NioThread(service.selector2);
        NioThread t3 = new NioThread(service.selector3);

        t1.start();
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        t2.start();
        t3.start();
        System.out.println("服务器启动成功......");

        try {
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class NioThread extends Thread {
    Selector selector = null;
    static int selectors = 0;

    int id = 0;
    boolean boos = false;

    static BlockingQueue<SocketChannel>[] queue;
    static AtomicInteger idx = new AtomicInteger();

    public NioThread(Selector selector) {
        this.selector = selector;
        id = idx.getAndIncrement() % selectors;
        System.out.println("worker: " + id + "启动成功");
    }

    public NioThread(Selector selector, int i) {
        this.selector = selector;
        this.selectors = i;
        boos = true;

        queue = new LinkedBlockingDeque[selectors];
        for (int j = 0; j < i; j++) {
            queue[j] = new LinkedBlockingDeque<>();
        }
        System.out.println("Boss 启动");
    }

    @Override
    public void run() {
        try {
            while (true) {
                while (selector.select(10) > 0) {
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isAcceptable()) {
                            acceptHandler(key);
                        } else if (key.isReadable()) {
                            readHandler(key);
                        }
                    }
                }
                if (!boos && !queue[id].isEmpty()) {
                    ByteBuffer buffer = ByteBuffer.allocate(8192);
                    SocketChannel client = queue[id].take();
                    client.register(selector, SelectionKey.OP_READ, buffer);
                    System.out.println("--------------------------");
                    System.out.println("new client: " + client.socket().getPort() + "分配到worker: " + (id));
                    System.out.println("--------------------------");
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

            int num = idx.getAndIncrement() % selectors;
            queue[num].add(client);

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
}
