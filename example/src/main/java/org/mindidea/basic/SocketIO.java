package org.mindidea.basic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketIO {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(9090);
        System.out.println("1. new ServerSocket(9090)");
        // 阻塞
        Socket client = server.accept();
        System.out.println("2. client \t" + client.getPort());
        InputStream in = client.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        // 阻塞
        System.out.println(reader.readLine());

        /*
         * 命令行输入: nc 127.0.0.1 9090 进行连接测试
         */
        while (true) {}
    }
}
