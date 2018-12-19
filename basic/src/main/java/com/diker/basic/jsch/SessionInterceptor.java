package com.diker.basic.jsch;

import com.jcraft.jsch.Session;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author diker
 * @since 2018/12/6
 */
public class SessionInterceptor implements MethodInterceptor {

    private static String DISCONNECT_VAL = "disconnect";

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        if(DISCONNECT_VAL.equals(method.getName())) {
            System.out.println("disconnect method intercept");
            return null;
        } else {
            return methodProxy.invokeSuper(obj, args);
        }
    }
}
