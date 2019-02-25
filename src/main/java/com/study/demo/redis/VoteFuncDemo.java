
package com.study.demo.redis;


import com.study.demo.util.DemoUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * redis demo
 */
public class VoteFuncDemo{

    private static Jedis jedis = DemoUtil.getJedis();
    private static final List<String> BOOK_NAMES = Arrays.asList("小王子","围城","活着","追风筝的人","性学报告","生命中不能承受之轻","麦田里的守望者",
            "飞鸟集","爱恋中的女人","婚姻的镜子","失乐园","男人这东西","婚姻的镜子","挪威的森林");
    private static final String SPLIT = ":";
    private static final String ARTICLE_INFO_KEY = "article" + SPLIT;
    private static final String TIME_KEY = "time" + SPLIT;
    private static final String SCORE_KEY = "score" + SPLIT;
    private static final String VOTED_KEY = "voted" + SPLIT;
    private static final String USER_KEY = "user" + SPLIT;
    private static final Double SCORE_STEP = 50.00;

    /**
     * 评分表：文章ID：分数 利用有序集合，方便后续获取前x的排名
     * 时间表：文章ID：创建时间 利用有序集合，方便后续获取前x的排名
     * 投票表：文章ID：用户ID集合
     * 文章表：文章ID：文章信息
     */
    public static void main(String[] args) {
        String articleId = DemoUtil.getRandomInt(6);
        String userId = DemoUtil.getRandomInt(6);
        publishArticle(articleId);
        System.out.println(articleId);
        userVoted(articleId,userId);
    }

    public static void userVoted(String articleId,String userId){
        String cacheKey = VOTED_KEY + articleId;
        String value = USER_KEY + userId;
        Boolean sismember = jedis.sismember(cacheKey, value);
        if(!sismember){
            //记录投票人
            jedis.sadd(cacheKey,value);
            //增加分数
            jedis.zincrby(SCORE_KEY,SCORE_STEP,ARTICLE_INFO_KEY + articleId);
            //修改文章信息
            jedis.hincrBy(ARTICLE_INFO_KEY + articleId,"voted",1L);
        }else{
            System.err.println("========你已经投过票，请勿重复投票========");
        }
    }

    //发布文章
    public static void publishArticle(String articleId){
        //1记录文章信息
        rememberArticle(articleId);
        //2记录文章创建时间
        rememberTime(articleId,new Date());
        //3记录文章分数
        rememberScore(articleId,(double)new Date().getTime());
    }

    //记录分数
    private static void rememberScore(String articleId,double score){
        jedis.zadd(SCORE_KEY,score,ARTICLE_INFO_KEY + articleId);
    }

    //记录创建时间
    private static void rememberTime(String articleId,Date date){
        jedis.zadd(TIME_KEY,(double)date.getTime(),ARTICLE_INFO_KEY + articleId);
    }

    //记录文章信息
    private static void rememberArticle(String articleId){
        jedis.hset(ARTICLE_INFO_KEY + articleId,"title","title " + DemoUtil.getRandomStr(6));
        jedis.hset(ARTICLE_INFO_KEY + articleId,"creator","user:" + DemoUtil.getRandomInt(6));
        jedis.hset(ARTICLE_INFO_KEY + articleId,"create_time",new Date().getTime() + "");
        jedis.hset(ARTICLE_INFO_KEY + articleId,"desc",DemoUtil.getRandomStr(10));
        jedis.hset(ARTICLE_INFO_KEY + articleId,"voted","0");
    }


}

