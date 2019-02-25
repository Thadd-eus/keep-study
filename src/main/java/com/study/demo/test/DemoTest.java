package com.study.demo.test;

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
       new DemoTest().test();
    }

}
