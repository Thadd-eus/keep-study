package com.study.demo.redis;

import com.study.demo.util.DemoUtil;
import com.study.demo.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * 登录会话demo
 */
public class SessionFuncDemo {

    private static final Jedis jedis = DemoUtil.getJedis();

    private static final String USER_INFO_KEY = "login:token" + DemoUtil.Constant.SPLIT_SEM;
    private static final String VIEWS_KEY = "brows:goods" + DemoUtil.Constant.SPLIT_SEM;
    private static final String USER_LOGIN_TIME = "login:lastTime" + DemoUtil.Constant.SPLIT_SEM;
    private static final Integer BROWS_GOODS_SIZE = 25;//保留浏览商品的数据
    private static final Integer SESSION_SIZE = 10000;
    
    public static void main(String[] args) {

//        for(int i = 0; i < 30; i++){
//            jedis.zadd("goodsTest",i,"test" + i);
//        }
        String key = "goodsTest";
        Set<String> zrange = jedis.zrange(key, 0, 5);
        String[] strings = zrange.toArray(new String[zrange.size()]);
        System.out.println(strings);
        jedis.del(strings);

        zrange = jedis.zrange(key, 0, 5);
        System.out.println(zrange);
    }

    /**
     * 登录，记录token
     */
    public static void login(String token,Map<String,String> user){
        //获取cache key
        Long tokenCount = jedis.zcard(USER_LOGIN_TIME);
        if(SESSION_SIZE < tokenCount){
            Set<String> historySet = jedis.zrange(USER_LOGIN_TIME, 0, tokenCount - SESSION_SIZE);
            String[] tokens = historySet.toArray(new String[historySet.size()]);
            //删除登录时间
            jedis.zrem(USER_LOGIN_TIME,tokens);
            //删除用户信息
            List<String> tokenValuesList = jedis.mget(tokens);
            String[] users = tokenValuesList.toArray(new String[tokenValuesList.size()]);
            jedis.del(users);
            //删除token
            jedis.del(tokens);
        }
        String userInfoKey = DemoUtil.md5(USER_INFO_KEY + user.get("id"));
        jedis.set(token,userInfoKey);
        jedis.set(userInfoKey,JsonUtil.toJson(user));
        jedis.zadd(USER_LOGIN_TIME,(double)new Date().getTime(),token);
    }

    /**
     * 检查token存不存在
     */
    private static String checkToken(String token){
        String entityKey = jedis.get(token);
        return entityKey;
    }

    /**
     * 修改token
     * @param user
     * @param token
     */
    public static void updateToken(Map<String,String> user,String token){
        String entity = checkToken(token);
        if(StringUtils.isBlank(entity)){
            login(token,user);
        }else{
            //获取cache key
            String id = user.get("id");
            String mdValue = DemoUtil.md5(USER_INFO_KEY + id);
            String jsonValue = JsonUtil.toJson(user);
            jedis.set(mdValue,jsonValue);
        }
    }

    /**
     * 记录用户最近浏览过的商品，最多25个
     * @param token
     * @param goodsList
     */
    public static void saveBrowseGoods(String token, List<Map<String,String>> goodsList){
        String cacheKey = VIEWS_KEY + token;
        jedis.zadd(cacheKey, new Date().getTime(), JsonUtil.toJson(goodsList));
        Long count = jedis.zcard(cacheKey);
        if(count > BROWS_GOODS_SIZE){
            jedis.zremrangeByRank(cacheKey,0,count - BROWS_GOODS_SIZE);
        }
    }
}
