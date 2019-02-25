package com.study.demo.redis;

import com.study.demo.util.DemoUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 登录会话demo
 */
public class SessionFuncDemo {

    private static final Jedis jedis = DemoUtil.getJedis();

    private static final String TOKEN_KEY = "login:token" + DemoUtil.Constant.SPLIT_SEM;
    private static final String USER_LOGIN_TIME = "login:lastTime" + DemoUtil.Constant.SPLIT_SEM;

    public static void main(String[] args) {
//        Map<String,String> map = new HashMap<>();
//        map.put("key","val");
//        System.out.println(map);
//        System.out.println(map.remove("key"));
//        System.out.println(map);
        for(int i = 1; i < 10; i++){
            jedis.set("test:key:" + i,"value" + i);
        }
        jedis.set("test:key:keys","testValue" );
        Set<String> keys = jedis.keys("test:key*");
        System.out.println(keys);
    }

    /**
     * 检查token存不存在
     */
    public String checkToken(String token){
        String entityKey = jedis.get(token);
        return entityKey;
    }

    public void updateToken(Map<String,String> user,String token){
        String entity = checkToken(token);
        if(StringUtils.isBlank(entity)){
            System.err.println("登录过期了，请重新登录");
        }else{
            //获取cache key
            String id = user.get("id");

            jedis.zadd(USER_LOGIN_TIME + token,(double)new Date().getTime(),token);

        }

    }
}
