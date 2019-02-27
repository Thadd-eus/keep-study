package com.study.demo.test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 词语相似度工具类,最小编辑距离算法
 */
public class cosUtil {

    private static final String[] tables = {"nat_top_cly","nat_first_cly","nat_second_cly"};
    private static final double STANDARD = 0.10;

    /**
     * [建筑, 幕墙, 工程, 专业, 承包, 铝合金]
     * [铝合金, 建筑, 型材, 铝合金]
     * {铝合金=1, 建筑=1, 型材=0, 幕墙=1, 工程=1, 专业=1, 承包=1}
     * {铝合金=2, 建筑=1, 型材=1, 幕墙=0, 工程=0, 专业=0, 承包=0}
     * {铝合金=0, 建筑=0, 型材=0, 幕墙=0, 工程=0, 专业=0, 承包=0}
     * @param args
     */
    public static void main(String[] args) {
        String source = "建筑幕墙工程专业承包铝合金";
        String target = "铝合金建筑型材铝合金";
//        System.out.println(similarity(source,target));
        List<String> strings = segmentText(source);
        List<String> strings2 = segmentText(target);
        System.out.println(strings);
        System.out.println(strings2);
        Map<String, int[]> stringMap = mergeSegmentWord(strings, strings2);
        for (Map.Entry<String,int[]> entry : stringMap.entrySet()){
            System.out.println(entry.getKey() + ":" + Arrays.toString(entry.getValue()));
        }
    }

    /**
     * 余弦算法比较相似度
     * 1.对要比较的词句分词
     * 2.算出各自语句中的词出现的次数
     * 3.计算余弦值，越大代表越相似
     * @param grade
     * @return
     */
    public static List<Map<String, Object>> start(int grade){

      return null;
    }

    /**
     * 1.合并两个分类字段的分词
     * 2.统计分类字段的词语在分词组出现的频率
     * 3.计算余弦 http://www.ruanyifeng.com/blog/2013/03/cosine_similarity.html
     * @param categoryList
     * @param clyNameList
     * @return
     */
    private static Map<String,int[]> mergeSegmentWord(List<String> categoryList, List<String> clyNameList) {
        Map<String,int[]> totalSegmentMap = Maps.newLinkedHashMap();
        for(String category : categoryList){
            if(totalSegmentMap.containsKey(category)){
                totalSegmentMap.get(category)[0]++;
            }else{
                int[] initCount = new int[2];
                initCount[0] = 1;
                totalSegmentMap.put(category,initCount);
            }
        }
        for(String clyName : clyNameList){
            if(totalSegmentMap.containsKey(clyName)){
                totalSegmentMap.get(clyName)[1]++;
            }else{
                int[] initCount = new int[2];
                initCount[1] = 1;
                totalSegmentMap.put(clyName,initCount);
            }
        }
        //计算向量
        double xy;
        for(Map.Entry<String,int[]> entry : totalSegmentMap.entrySet()){
            int[] wordCountArr = entry.getValue();

        }
        return totalSegmentMap;
    }

    /**
     * 分词
     * @param text
     * @return
     */
    private static List<String> segmentText(String text) {
        List<String> list = Lists.newArrayList();
        StringReader re = new StringReader(text);
        IKSegmenter ikAnalyzer = new IKSegmenter(re,true);
        Lexeme lex;
        try{
            while ((lex = ikAnalyzer.next()) != null) {
                list.add(lex.getLexemeText());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

}
