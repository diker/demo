package com.diker.basic.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 可重入排他锁（非公平锁）
 * @author diker
 * @since 2018/12/18
 */
public class Mutex implements Lock {

    static final class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean isHeldExclusively() {
            return Thread.currentThread() == getExclusiveOwnerThread();
        }

        @Override
        protected boolean tryAcquire(int acquires) {
            final Thread current = Thread.currentThread();
            int c = getState();
            if (c == 0) {
                if(compareAndSetState(0, acquires)) {
                    setExclusiveOwnerThread(current);
                    return true;
                }
            } else if (current == getExclusiveOwnerThread()) {
                setState(c + acquires);
                return true;
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int releases) {
            final Thread current = Thread.currentThread();
            int c = getState() - releases;
            if(current != getExclusiveOwnerThread()) {
                throw new IllegalMonitorStateException();
            }

            setState(c);
            if (c == 0) {
                setExclusiveOwnerThread(null);
                return true;
            }
            return false;
        }

        final Condition newCondition() {
            return new ConditionObject();
        }
    }

    //基于AQS的同步器
    private Sync sync;

    public Mutex() {
        sync = new Sync();
    }

    @Override
    public void lock() {
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return sync.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return sync.tryAcquireNanos(1, unit.toNanos(time));
    }

    @Override
    public void unlock() {
        sync.release(1);
    }

    @Override
    public Condition newCondition() {
        return sync.newCondition();
    }
}
