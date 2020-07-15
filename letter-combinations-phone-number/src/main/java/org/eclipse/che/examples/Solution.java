package org.eclipse.che.examples;

import java.util.LinkedList;
import java.util.List;

/*
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/description/
 *
 * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent.
 *
 * A mapping of digit to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
 *
 * Example:
 * Input: "23"
 * Output: ["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 * Note:
 * Although the above answer is in lexicographical order, your answer could be in any order you want.
 */
public class Solution {
    private static boolean  IS_DEBUG         = true;
    private static String[] DIGIT_TO_LETTERS = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};

    private static LinkedList<String> getLetterCombinationsRecursive(String digits, LinkedList<String> combinations, String combination) {
        if ((digits == null) || (digits.isEmpty()))
            return combinations;

        int level = combination.length();
        if (level >= digits.length()) {
            combinations.add(combination);

            if (IS_DEBUG) {
                System.out.println("1) returning: " + combinations);
            }

            return combinations;
        }

        String letters = DIGIT_TO_LETTERS[digits.charAt(level) - '0'];
        for (int i = 0; i < letters.length(); ++i)
            getLetterCombinationsRecursive(digits, combinations, (combination + letters.charAt(i)));

        if (IS_DEBUG) {
            System.out.println("2) returning: " + combinations);
        }

        return combinations;
    }

    private static LinkedList<String> getLetterCombinationsIterative(String digits) {
        LinkedList<String> combinations = new LinkedList<String>();

        if ((digits == null) || (digits.isEmpty()))
            return combinations;

        int[] i = new int[digits.length()];
        int level = digits.length() - 1;
        boolean isFirst = true;

        while (true) {
            if (!isFirst) {
                boolean isBreak = false;

                while (true) {
                    ++i[level];
                    if (i[level] >= DIGIT_TO_LETTERS[digits.charAt(level) - '0'].length()) {
                        i[level] = 0;
                        --level;

                        if (level < 0) {
                            isBreak = true;
                            break;
                        }

                        continue;
                    }

                    level = digits.length() - 1;
                    break;
                }

                if (isBreak)
                    break;
            }

            isFirst = false;

            String combination = "";
            for (int j = 0; j < digits.length(); ++j)
                combination += DIGIT_TO_LETTERS[digits.charAt(j) - '0'].charAt(i[j]);

            combinations.add(combination);
        }

        if (IS_DEBUG) {
            System.out.println("returning: " + combinations);
        }

        return combinations;
    }

    public static List<String> letterCombinations(String digits) {
        if (IS_DEBUG) {
            System.out.println();
            System.out.println("digits: " + digits);
        }

        LinkedList<String> combinations = getLetterCombinationsRecursive(digits, new LinkedList<String>(), "");

        if (IS_DEBUG) {
            System.out.println("combinations (recursive): " + combinations);
        }

        combinations = getLetterCombinationsIterative(digits);

        if (IS_DEBUG) {
            System.out.println("combinations (iterative): " + combinations);
        }

        return combinations;
    }

    public static void main(String... argvs) {
        List<String> combinations = null;
        String expected = null;

        combinations = letterCombinations(null);
        expected = "[]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (combinations.toString().equals(expected)));

        combinations = letterCombinations("");
        expected = "[]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (combinations.toString().equals(expected)));

        combinations = letterCombinations("23");
        expected = "[ad, ae, af, bd, be, bf, cd, ce, cf]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (combinations.toString().equals(expected)));

        combinations = letterCombinations("837");
        expected =
                   "[tdp, tdq, tdr, tds, tep, teq, ter, tes, tfp, tfq, tfr, tfs, udp, udq, udr, uds, uep, ueq, uer, ues, ufp, ufq, ufr, ufs, vdp, vdq, vdr, vds, vep, veq, ver, ves, vfp, vfq, vfr, vfs]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (combinations.toString().equals(expected)));
    }
}
