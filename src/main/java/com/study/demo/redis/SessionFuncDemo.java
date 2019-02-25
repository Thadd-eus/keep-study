package com.study.demo.redis;

import com.study.demo.util.DemoUtil;
import com.study.demo.util.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import sun.security.provider.MD5;

import java.util.*;

/**
 * 登录会话demo
 */
public class SessionFuncDemo {

    private static final Jedis jedis = DemoUtil.getJedis();

    private static final String TOKEN_KEY = "login:token" + DemoUtil.Constant.SPLIT_SEM;
    private static final String VIEWS_KEY = "brows:goods" + DemoUtil.Constant.SPLIT_SEM;
    private static final String USER_LOGIN_TIME = "login:lastTime" + DemoUtil.Constant.SPLIT_SEM;
    private static final Integer browsGoodsSize = 25;//保留浏览商品的数据

    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        map.put("key","val");
        map.put("key2","val2");
        map.put("key3","val3");
        List<Map> list = new ArrayList<>();
        list.add(map);
        System.out.println(JsonUtil.toJson(list));
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
            String mdValue = DemoUtil.md5(TOKEN_KEY + id);
            String jsonValue = JsonUtil.toJson(user);
            jedis.set(mdValue,jsonValue);
            jedis.zadd(USER_LOGIN_TIME + token,(double)new Date().getTime(),token);
        }
    }

    /**
     * 记录用户最近浏览过的商品，最多25个
     * @param token
     * @param goodsList
     */
    public void saveBrowseGoods(String token, List<Map<String,String>> goodsList){
        String cacheKey = VIEWS_KEY + token;
        jedis.zadd(cacheKey, new Date().getTime(), JsonUtil.toJson(goodsList));
        Long count = jedis.zcard(cacheKey);
        if(count > browsGoodsSize){
            jedis.zremrangeByRank(cacheKey,0,count - browsGoodsSize);
        }
    }
}
