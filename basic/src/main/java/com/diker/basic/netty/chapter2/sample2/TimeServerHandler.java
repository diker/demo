package com.diker.basic.netty.chapter2.sample2;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author diker
 * @since 2019/1/28
 */
public class TimeServerHandler implements Runnable {

    private Socket socket;

    public TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        BufferedReader br = null;
        BufferedWriter bw = null;
        try {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line = null;
            boolean echoTimeFlag = false;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if ("EchoTime".equalsIgnoreCase(line)) {
                    echoTimeFlag = true;
                    break;
                }
            }

            bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            if (echoTimeFlag) {
                bw.write(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)+"\n");
            } else {
                bw.write("error input data!!+\n");
            }
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
                if (br != null) {
                    br.close();
                }

                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
