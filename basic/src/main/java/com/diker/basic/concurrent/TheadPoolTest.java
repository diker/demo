package com.diker.basic.concurrent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池测试
 * @author diker
 * @since 2019/2/23
 */
public class TheadPoolTest {

    public static void main(String[] args) throws IOException {
        testOuptLog();
    }

    public static void testOuptLog() throws IOException {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(5, 5,
                100, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue(16));

        for (int i=0; i<5; i++) {

            threadPoolExecutor.execute(() -> {
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream("D:\\tmp\\test.log");
                    outputStream.write("========\n".getBytes());
                    outputStream.flush();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (outputStream!=null) {
                            outputStream.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });


        }
    }
}
