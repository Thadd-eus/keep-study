package com.study.demo.util;

import java.io.UnsupportedEncodingException;
import java.util.Random;

/**
 * 工具类
 */
public class DemoUtil {

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
}
