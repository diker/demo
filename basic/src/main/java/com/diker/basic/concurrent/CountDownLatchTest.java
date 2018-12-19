package com.diker.basic.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * 当maxWaiter值通过countDown()方法减少至0时，await()方法等待结束开始继续执行。
 * CountDownLatch是一次性的，不能重用，如果想重用看CyclicBarrier类是否适合。
 * @author diker
 * @since 2018/12/6
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws InterruptedException {
        int maxWaiter = 3;
        CountDownLatch latch = new CountDownLatch(maxWaiter);

        for(int i=0; i<maxWaiter; i++) {
            Thread t = new Thread(()->{
                try {
                    Thread.sleep(2*1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });
        }
        latch.await();
        System.out.println("所有子线程已执行结束");
    }
}
