package com.diker.basic.proxy;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author diker
 * @since 2018/12/9
 */
public class UserService {

    private List<Map> list = Collections.synchronizedList(new ArrayList<>(16));

    public void addUser(String username, String password) {
        Map userMap = new HashMap();
        userMap.put("username", username);
        userMap.put("password", password);

        list.add(userMap);
    }

    public List queryAll() {
        return list;
    }

    public Map queryByUsername(String username) {
        return list.stream().filter(item -> Objects.equals(username,item.get("username"))).findFirst().get();
    }

    public void deleteByUsername(String username) {
        list  = list.stream().filter(item -> !Objects.equals(username,item.get("username"))).collect(Collectors.toList());
    }
}
