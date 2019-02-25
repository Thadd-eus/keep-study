package com.study.demo.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 工具类
 */
public class DemoUtil {

    public static class Constant{
        public static final String SPLIT_SEM = ":";
    }

    public static Jedis getJedis(){
        JedisShardInfo jedisShardInfo = new JedisShardInfo("127.0.0.1",6379);
//        jedisShardInfo.setPassword("123456");
        return jedisShardInfo.createResource();
    }
    /**
     * 随机生成数字字符串
     */
    public static String getRandomInt(int length){
        Random random = new Random();
        String userId = "";
        for(int i = 0; i < length; i++){
            int anInt = random.nextInt(9);
            userId += anInt;
        }
        return userId;
    }

    /**
     *随机生成中文
     */
    public static String getRandomStr(){
        String str = "";
        Random random = new Random();
        int hightPos = (176 + Math.abs(random.nextInt(39)));
        int lowPos = (161 + Math.abs(random.nextInt(93)));
        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();
        try {
            str = new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     *随机生成中文
     */
    public static String getRandomStr(int length) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i <= length; i++){
            builder.append(getRandomStr());
        }
        return builder.toString();
    }

    /**
     * MD5加密
     */
    public static String md5(String plainText) {
        //定义一个字节数组
        byte[] secretBytes;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
