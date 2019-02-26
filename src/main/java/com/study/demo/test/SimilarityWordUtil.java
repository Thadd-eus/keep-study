package com.study.demo.test;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

/**
 * 词语相似度工具类,最小编辑距离算法
 */
public class SimilarityWordUtil {

    public static void main(String[] args) {
        String source = "建筑幕墙工程专业承包";
        String target = "铝合金建筑型材";
        System.out.println(similarity(source,target));
    }

    public static double similarity(String source,String target){
        int compare = compare(source, target);
        int max = Math.max(source.length(), target.length());
        BigDecimal differ = new BigDecimal(compare);
        BigDecimal maxLength = new BigDecimal(max);
        BigDecimal divide = differ.divide(maxLength, 2, BigDecimal.ROUND_HALF_UP);
        BigDecimal sum = new BigDecimal(1);
        BigDecimal subtract = sum.subtract(divide);
        System.out.println(compare + "---->" + divide);
        return subtract.doubleValue();
    }

    /**
     *
     * @param source 源字段
     * @param target 目标字段
     */
    public static int compare(String source,String target){
        int differCount;
        //为空直接返回
        if(StringUtils.isBlank(source) && StringUtils.isBlank(target)){
            differCount = 0;
        }else if(StringUtils.isBlank(source) && StringUtils.isNotBlank(target)){
            differCount = target.length();
        }else if(StringUtils.isNotBlank(source) && StringUtils.isBlank(target)){
            differCount = source.length();
        }else{
            int[][] distanceArr = new int[source.length() + 1][target.length() + 1];
            for (int i = 0; i <= source.length(); i++) {
                distanceArr[i][0] = i;
            }
            for (int j = 0; j <= target.length(); j++) {
                distanceArr[0][j] = j;
            }
            for (int i = 1; i <= source.length(); i++) {
                for (int j = 1; j <= target.length(); j++) {
                    int replace = distanceArr[i-1][j-1];
                    if(source.charAt(i - 1) != target.charAt(j - 1)){
                        replace += 1;
                    }
                    int insert = distanceArr[i-1][j] + 1;
                    int delete = distanceArr[i][j-1] + 1;
                    int min = replace > insert? insert : replace;
                    min = min > delete ? delete : min;
                    distanceArr[i][j] = min;
                }
            }
            differCount = distanceArr[source.length()][target.length()];
        }
        return differCount;
    }

}
