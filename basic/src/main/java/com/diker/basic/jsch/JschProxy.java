package com.diker.basic.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author diker
 * @since 2018/12/6
 */
public class JschProxy<T> {

    public T createProxy(Class<T> objClass) {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(objClass);
        enhancer.setCallback(new SessionInterceptor());


        return (T)enhancer.create(new Class[]{JSch.class, String.class, String.class, int.class}, new Object[]{new JSch(), "test", "127.0.0.1", 22});
    }

}
