package com.diker.basic.netty.chapter2.sample1;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author diker
 * @since 2019/1/28
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);

        while (true) {
            Socket socket=server.accept();
            new Thread(new TimeServerHandler(socket)).start();
        }
    }
}
