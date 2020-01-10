package com.diker.basic.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Integer原子并发操作测试
 */
public class AtomicIntegerTest {

    public static void main(String[] args) {
        AtomicInteger ai = new AtomicInteger(10);

        System.out.println(ai.accumulateAndGet(5, (ori, inc) -> ori + inc)); //第二个函数式参数定义原数据和第一个参数的操作结果值，并将结果保存

        System.out.println(ai.accumulateAndGet(2, (ori, inc) -> ori * inc));
    }
}
