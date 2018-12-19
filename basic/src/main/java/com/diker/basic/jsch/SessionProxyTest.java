package com.diker.basic.jsch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @author diker
 * @since 2018/12/6
 */
public class SessionProxyTest {
    public static void main(String[] args) throws JSchException {
        SessionProxy<Session> sessionProxy = new SessionProxy<Session>();
        Session session = sessionProxy.createProxy(Session.class);
        session.disconnect();


    }
}
