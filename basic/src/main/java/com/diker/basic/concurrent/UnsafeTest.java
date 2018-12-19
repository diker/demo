package com.diker.basic.concurrent;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

/**
 *
 * @author diker
 * @since 2018/12/18
 */
public class UnsafeTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);

        /**
         * 第一个参数：true表示是绝对时间，false表示相对时间
         * 第二个参数表示时间
         *   当第一个参数为true时该参数表示绝对时间，可使用当前时间的毫秒加上等待的毫秒数
         *   当第一个参数为false时该参数表示相对时间，表示需要等待的纳秒数，值为零时表示一直挂起直到unpark恢复，纳秒(1秒=1000毫秒，1毫秒=1000微秒，1微秒=1000纳秒)
         */
        unsafe.park(false, 1*1000*1000*1000L);
        System.out.println("第一次等待结束" + LocalDateTime.now());

        unsafe.park(true, System.currentTimeMillis() + 1*1000L);
        System.out.println("第二次等待结束" + LocalDateTime.now());

        Thread t = new Thread(()->{
            unsafe.park(false, 0L);
            System.out.println(Thread.currentThread().getName() + "结束挂起状态，开始执行");
        });
        t.start();
        Thread.sleep(1*1000L);
        unsafe.unpark(t);

        //CAS机制
        long stateOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("state"));
        long descOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("desc"));
        Node node = new Node(0, "初始状态");
        unsafe.compareAndSwapInt(node, stateOffset,0, 1);
        unsafe.compareAndSwapObject(node, descOffset, "初始状态", "执行状态");
        System.out.println(node.toString());
        System.out.println(unsafe.getInt(node,stateOffset));
        System.out.println(unsafe.getObject(node,descOffset));
    }

    static class Node {
        private int state;
        private String desc;
        public Node(int state, String desc) {
            this.state = state;
            this.desc = desc;
        }

        public int getState() {
            return state;
        }

        public String getDesc() {
            return desc;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "state=" + state +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }
}
