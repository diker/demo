package com.diker.basic.netty.chapter2.sample2;

import java.io.*;
import java.net.Socket;

/**
 * @author diker
 * @since 2019/1/28
 */
public class TimeClient {

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 8888);
        OutputStream os = socket.getOutputStream();
        os.write("EchoTime\n".getBytes());
        os.flush();


        String line = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        while ((line=br.readLine())!=null) {
            System.out.println(line);
        }

        br.close();
        os.close();
        socket.close();
    }
}