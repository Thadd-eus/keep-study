
package com.study.demo;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.Map;

/**
 * redis demo
 */
public class RedisDemo{

    /**
     * 评分表：文章ID：分数
     * 时间表：文章ID：创建时间
     * 投票表：文章ID：用户ID集合
     * 文章表：文章ID：文章信息
     */
    public static void main(String[] args) {
        RedisDemo redisDemo = new RedisDemo();
        Jedis jedis = redisDemo.pool();
        Long zadd = jedis.zadd("score:", 100.00, "article:1");
        System.out.println(zadd);
    }

    /**
     * 发表文章
     * @param articleInfo title,desc,vote
     */
    public void publishArticle(Map<String,String> articleInfo){

    }

    public Jedis pool(){
        JedisShardInfo jedisShardInfo = new JedisShardInfo("127.0.0.1",6379);
        jedisShardInfo.setPassword("123456");
        Jedis jedis = jedisShardInfo.createResource();
        return jedis;
    }
}

