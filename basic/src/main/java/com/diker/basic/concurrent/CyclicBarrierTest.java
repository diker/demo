package com.diker.basic.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 当等待的线程数达到maxWaiter之后，所有调用await()方法的线程开始执行
 * CyclicBarrier可重用
 * @author diker
 * @since 2018/12/18
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        int maxWaiter = 3;
        CyclicBarrier barrier = new CyclicBarrier(maxWaiter, ()->{
            System.out.println("所有线程已准备就绪，可以开始执行。");
        });

        //第一次使用
        for(int i=0; i<maxWaiter; i++) {
            Thread t = new Thread(()->{
                try {
                    Thread.sleep(2*1000L);
                    System.out.println("第一次准备就绪");
                    barrier.await();
                    System.out.println("第一次执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

        //重用
        for(int i=0; i<maxWaiter; i++) {
            Thread t = new Thread(()->{
                try {
                    Thread.sleep(2*1000L);
                    System.out.println("第二次准备就绪");
                    barrier.await();
                    System.out.println("第二次执行");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }
    }

}
