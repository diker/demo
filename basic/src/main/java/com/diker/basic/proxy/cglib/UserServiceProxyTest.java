package com.diker.basic.proxy.cglib;

import com.diker.basic.proxy.UserService;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author diker
 * @since 2018/12/9
 */
public class UserServiceProxyTest {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserService.class);
        enhancer.setCallback(new UserServiceProxy());

        UserService userService = (UserService) enhancer.create();
        userService.addUser("test1", "123");
        userService.addUser("test2", "123");
        userService.addUser("test3", "123");
        userService.addUser("test4", "123");

        System.out.println(userService.queryAll());

        System.out.println(userService.queryByUsername("test2"));

        userService.deleteByUsername("test2");
        System.out.println(userService.queryAll());
    }
}
