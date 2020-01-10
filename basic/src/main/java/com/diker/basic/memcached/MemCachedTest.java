package com.diker.basic.memcached;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;

import java.util.Date;
import java.util.concurrent.Future;

/**
 * @author diker
 * @since 2019/3/11
 */
public class MemCachedTest {

    protected static MemCachedClient mcc = new MemCachedClient();

    static {
        // 服务器列表和其权重
        String[] servers = {"127.0.0.1:11211"};
        Integer[] weights = {3};

        // 获取socke连接池的实例对象
        SockIOPool pool = SockIOPool.getInstance();

        // 设置服务器信息
        pool.setServers( servers );
        pool.setWeights( weights );

        // 设置初始连接数、最小和最大连接数以及最大处理时间
        pool.setInitConn( 5 );
        pool.setMinConn( 5 );
        pool.setMaxConn( 5000 );
        pool.setMaxIdle( 1000 * 60 * 60 * 6 );

        // 设置主线程的睡眠时间
        pool.setMaintSleep( 30 );

        // 设置TCP的参数，连接超时等
        pool.setNagle( false );
        pool.setSocketTO( 3000 );
        pool.setSocketConnectTO( 0 );

        // 初始化连接池
        pool.initialize();
    }

    public static void main(String[] args) {
        boolean result = mcc.set("test2", "22222");
        System.out.println("set result: " + result);
        System.out.println(mcc.get("test2"));


        result = mcc.set("testadd", "qqqqqqqqq");
        System.out.println("add result: " + result);
        System.out.println(mcc.get("testadd"));

        result = mcc.set("testadd", "1111111");
        System.out.println("add result: " + result);
        System.out.println(mcc.get("testadd"));
    }
}
