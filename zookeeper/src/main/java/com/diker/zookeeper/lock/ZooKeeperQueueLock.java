package com.diker.zookeeper.lock;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.shaded.com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.data.Stat;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于InterProcessSemaphoreV2做并发控制，InterProcessSemaphoreV2内部采用InterProcessMutex可重入锁+leases租约的形式控制并发大小
 * 其中leases的个数即代表并发的大小，而每个租约是保存在zookeeper中/xxxxx/leases下的一个子节点,每个线程中租约的获取和释放需要先获得InterProcessMutex锁
 * @author diker
 * @since 2018/11/28
 */
public class ZooKeeperQueueLock {

    private static final String CONNECTION_URL = "127.0.0.1:2181";
    private static final int BASE_SLEEP_TIME_MS = 1000;
    private static final int MAX_RETRY = 10;
    private static final String ROOT_PATH = "/fsos";
    private static final String LEASES_PATH = ROOT_PATH + "/leases";
    private static final int MAX_LEASES = 1;
    private static final int QUEUE_SIZE = 10;
    private static final AtomicInteger counter = new AtomicInteger(0);

    public static void main(String[] args) throws Exception {

        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CONNECTION_URL)
                .retryPolicy(new ExponentialBackoffRetry(BASE_SLEEP_TIME_MS, MAX_RETRY)).build();
        client.start();

        try {
            client.create().creatingParentContainersIfNeeded().forPath(LEASES_PATH);
        } catch (KeeperException.NodeExistsException e) {
            e.printStackTrace();
        }


        Stat stat = client.checkExists().forPath(LEASES_PATH);
        if(stat == null) {
            System.out.println(" 节点不存在, 开始创建节点：{}" + LEASES_PATH);
            client.create().creatingParentContainersIfNeeded().forPath(LEASES_PATH);
            System.out.println(" 节点创建成功：{}" + LEASES_PATH);
        } else {
            System.out.println(" 节点已存在：{}" + LEASES_PATH);
        }

        final InterProcessSemaphoreV2 semaphore = new InterProcessSemaphoreV2(client, ROOT_PATH, MAX_LEASES);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("async-task-thread-%d").build();
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());

        for (int i = 0; i < 3; i++) {
            threadPoolExecutor.execute(() -> {
                Lease lease = null;
                try {
                    int currentQueueCount = counter.getAndIncrement();
                    if(currentQueueCount>=QUEUE_SIZE) {
                        System.out.println(" 队列大小：{}, 队列已满。");
                    } else {
                        System.out.println(" 队列大小：{}, 队列未满。" + currentQueueCount);
                        lease = semaphore.acquire(5*1000, TimeUnit.MILLISECONDS);
                        if (lease == null) {
                            System.out.println("锁等待超时");
                        } else {
                            System.out.println("拿到锁");
                        }
                        Thread.sleep(5 * 1000);
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    semaphore.returnLease(lease);
                    counter.decrementAndGet();
                }
            });
        }

        while(true) {
            Collection<String> currentNodes = semaphore.getParticipantNodes();
            System.out.println(currentNodes.size());
            Thread.sleep(1*1000L);
        }
    }

}
