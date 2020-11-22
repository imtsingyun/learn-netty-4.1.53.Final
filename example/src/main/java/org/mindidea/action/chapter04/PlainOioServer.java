/*
 * @class PlainOioServer
 * @package org.mindidea.action.chapter04
 * @date 2020/11/10 20:31
 * Copyright (c) 2020 MindIdea.org, All Rights Reserved.
 */
package org.mindidea.action.chapter04;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Tsingyun(青雲)
 * @version V1.0
 * @createTime 2020/11/10 20:31
 * @blog https://mindidea.org
 */
public class PlainOioServer {

    public static void main(String[] args) throws IOException {
        PlainOioServer server = new PlainOioServer();
        server.serve(8080);
    }

    public void serve(int port) throws IOException {
        final ServerSocket socket = new ServerSocket(port);
        try {
            for (;;) {
                final Socket client = socket.accept();
                System.out.println("Accepted connection from " + client);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OutputStream out;
                        try {
                            out = client.getOutputStream();
                            out.write("Hi!\n".getBytes(CharsetUtil.UTF_8));
                            out.flush();
                            client.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                client.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
