package com.diker.basic.concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.locks.Condition;

/**
 * 
 * @author diker
 * @since 2018/12/18
 */
public class MutexTest {

    public static void main(String[] args) {
        test1();
        test2();
        test3();
    }

    private static void test1() {
        Mutex mutex = new Mutex();

        new Thread(()->{
            mutex.lock();
            try {
                Thread.sleep(5*1000L);
                System.out.println("==1==" + LocalDateTime.now());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                mutex.unlock();
            }
        }).start();


        new Thread(()->{
            try {
                Thread.sleep(2*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mutex.lock();
            System.out.println("==2==" + LocalDateTime.now());
            mutex.unlock();

        }).start();
    }

    private static void test2() {
        Mutex mutex = new Mutex();
        mutex.lock();
        mutex.lock();

        System.out.println("重入性测试");

        mutex.unlock();
        mutex.unlock();
    }

    private static void test3() {
        Mutex mutex = new Mutex();
        Condition condition = mutex.newCondition();
        Thread t1 = new Thread(()->{
            mutex.lock();
            try {
                System.out.println("t1 await 等待开始");
                condition.await();
                System.out.println("t1 await 等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mutex.unlock();
        });
        t1.start();

        Thread t2 = new Thread(()->{
            mutex.lock();
            try {
                System.out.println("t2 await 等待开始");
                condition.await();
                System.out.println("t2 await 等待结束");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mutex.unlock();
        });
        t2.start();

        Thread t3 = new Thread(()->{
            try {
                Thread.sleep(1*1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mutex.lock();
            System.out.println("发出唤醒信号开始");
            condition.signalAll();
            System.out.println("发出唤醒信号结束");
            mutex.unlock();
        });

        t3.start();
    }
}
