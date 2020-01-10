package com.diker.basic.netty.chapter2.sample2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @author diker
 * @since 2019/1/28
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 10, 10*1000L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(20), new ThreadPoolExecutor.AbortPolicy());
        while (true) {
            Socket socket=server.accept();
            poolExecutor.execute(new TimeServerHandler(socket));
        }
    }
}
