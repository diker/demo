package com.diker.basic.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author diker
 * @since 2018/12/6
 */
public class SessionProxy<T> {

    public T createProxy(Class<T> objClass) throws JSchException {

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Session.class);

        enhancer.setCallback(new SessionInterceptor());


//        JSch jSch = new JSch();
//        Session session = jSch.getSession("127.0.0.1");
        return (T)enhancer.create(new Class[]{JSch.class, String.class, String.class, int.class}, new Object[]{new JSch(), "test", "127.0.0.1", 22});
    }

}
