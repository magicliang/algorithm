package algorithm.selectedtopics.leetcode.stringpermutation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Solution {

    /**
     * 假设字符串深度为 12，递归深度有限，可以用递归解
     *
     * @param str 原始字符串
     * @return 转置过的字符串数组
     */
    public List<String> letterCasePermutation(String str) {
        // 空字符串、递归到尾部，直接返回
        if (null == str || str.length() == 0) {
            return Collections.singletonList(str);
        }
        List<String> result = new ArrayList<>();
        int strLength = str.length();
        char ch = str.charAt(0);
        boolean isLetter = Character.isLetter(ch);
        // 先向下递归得到子串
        List<String> tmpResult = letterCasePermutation(str.substring(1, strLength));
        // 把当前问题的解和子问题的解-也就是子串 merge 起来
        if (isLetter) {
        	// 如果当前字符是字母，则转化为双结果
            char upperCase = Character.toUpperCase(ch);
            char lowerCase = Character.toLowerCase(upperCase);
            // 注意 i、character、i+1 这三个区间的分隔
            if (tmpResult.size() > 0) {
                for (String tmpStr : tmpResult) {
                    result.add(upperCase + tmpStr);
                    result.add(lowerCase + tmpStr);
                }
            } else {
                result.add(upperCase + "");
                result.add(lowerCase + "");
            }
            return result;
        } else {
        	// 否则转化为单结果
            if (tmpResult.size() > 0) {
                for (String tmpStr : tmpResult) {
                    result.add(ch + tmpStr);
                }
            } else {
                result.add(ch + "");
            }
            return result;
        }
        // 没有遇到字母则继续循环，让本字符串变长
    }

}
