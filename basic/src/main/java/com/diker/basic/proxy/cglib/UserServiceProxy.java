package com.diker.basic.proxy.cglib;

import com.diker.basic.proxy.UserService;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author diker
 * @since 2018/12/9
 */
public class UserServiceProxy implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if(Objects.equals(method.getName(), "queryAll")) {
            System.out.println("查询全部在数据量过大时存在内存溢出风险");
        }
        return methodProxy.invokeSuper(o, objects);
    }
}
