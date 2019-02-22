package com.study.demo.redis;

import com.study.demo.util.DemoUtil;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * 登录会话demo
 */
public class SessionFuncDemo {

    private static final Jedis jedis = DemoUtil.getJedis();

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("key","val");
        System.out.println(map);
        System.out.println(map.remove("key"));
        System.out.println(map);
    }
    /**
     * 检查token存不存在
     */
    public void checkToken(String token){

    }
}
