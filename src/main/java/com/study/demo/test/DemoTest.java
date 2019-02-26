package com.study.demo.test;

import java.math.BigDecimal;
import java.util.Arrays;

public class DemoTest {

    private static int i = 0;
    private static int j = 0;
    private int k = 0;

    public void test(){
        i++;
        k++;
        System.out.println("i-->" + i + " j-->" + j++ + " k-->" + k);
        System.out.println(j);
    }
    public static void main(String[] args) {
//       new DemoTest().test();
        minChangeCount();
    }


    private static String str1 = "中国人";
    private static String str2 = "中建公司人真帅气";
    private static String str2_= "ofailing";

    public static void minChangeCount(){

        Integer editlenght = edit(str1.length()-1, str2.length()-1);
        System.out.println(editlenght);
        int max = Math.max(str1.length(), str2.length());
        BigDecimal b1 = new BigDecimal(editlenght);
        BigDecimal b2 = new BigDecimal(max);
        BigDecimal divide = b1.divide(b2, 2, BigDecimal.ROUND_DOWN);
        System.out.println(1d - divide.doubleValue());

        int minDistance = minDistance(str1, str2);
        System.out.println(minDistance);
    }

    private static Integer compare(int i , int j){
        char c1 = str1.charAt(i);
        char c2 = str2.charAt(j);
        if(c1 == c2){
            return 0;
        }else{
            return 1;
        }
    }

    /**
     * 它表示第一个字符串的长度为i的子串到第二个字符串的长度为j的子串的编辑距离
     * @param i
     * @param j
     * @return
     */
    private static Integer edit(Integer i ,Integer j){
        int length;
        if(i == 0 && j == 0){
            length = 0;
        }else if(i > 0 && j == 0){
            length = i;
        }else if(i == 0 && j > 0){
            length = j;
        }else{
            int length_1 = edit(i-1, j) + 1;
            int length_2 = edit(i, j-1) + 1;
            int length_3 = edit(i-1, j-1) + compare(i, j);
            int min_1 = Math.min(length_1,length_2);
            length = Math.min(min_1,length_3);
        }
        return length;
    }

    public static int minDistance(String word1, String word2) {
        if (word1.length() == 0 || word2.length() == 0)
            return word1.length() == 0 ? word2.length() : word1.length();
        int[][] arr = new int[word1.length() + 1][word2.length() + 1];
        for (int i = 0; i <= word1.length(); i++) {
            arr[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            arr[0][j] = j;
        }
        for (int i = 0; i < word1.length() - 1; i++) {
            for (int j = 0; j < word2.length() - 1; j++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    arr[j + 1][i + 1] = arr[j][i];
                } else {
                    int replace = arr[i][j]+1 ;
                    int insert = arr[i][j + 1]+1 ;
                    int delete = arr[i + 1][j]+1 ;

                    int min = replace > insert ? insert : replace;
                    min = delete > min ? min : delete;
                    arr[i + 1][j + 1] = min;
                }
            }
        }
        return arr[word1.length()][word2.length()];
    }
}
