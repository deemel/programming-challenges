package org.eclipse.che.examples;

import java.util.Arrays;
import java.util.List;

/*
 * https://leetcode.com/problems/reverse-string/description/
 *
 * Write a function that takes a string as input and returns the string reversed.
 *
 * Example:
 * Given s = "hello", return "olleh".
 */
public class Solution {
    private static boolean IS_DEBUG = true;

    public static String reverseString(String s) {
        if ((s == null) || (s.isEmpty()))
            return s;

        char[] s2 = s.toCharArray();
        int length = s2.length;
        int lengthHalf = length / 2;
        int indexStop = (((length % 2) == 0) ? (lengthHalf - 1) : (lengthHalf));

        for (int i = 0; i <= indexStop; ++i) {
            char c = s2[i];
            int j = length - 1 - i;

            s2[i] = s2[j];
            s2[j] = c;
        }

        return String.valueOf(s2);
    }

    public static String reverseString2(String s) {
        if ((s == null) || (s.isEmpty()))
            return s;

        StringBuilder s2 = new StringBuilder(s);
        int length = s.length();
        int lengthHalf = length / 2;
        int indexStop = (((length % 2) == 0) ? (lengthHalf - 1) : (lengthHalf));

        for (int i = 0; i <= indexStop; ++i) {
            char c = s2.charAt(i);
            int j = length - 1 - i;

            s2.setCharAt(i, s2.charAt(j));
            s2.setCharAt(j, c);
        }

        return s2.toString();
    }

    private static String reverseStringInternal(String s, int indexLast, int index) {
        String c = Character.toString(s.charAt(index));

        if (index == indexLast)
            return c;

        return reverseStringInternal(s, indexLast, ++index) + c;
    }

    public static String reverseString1(String s) {
        if ((s == null) || (s.isEmpty()))
            return s;

        return reverseStringInternal(s, (s.length() - 1), 0);
    }

    public static String reverseString0(String s) {
        if ((s == null) || (s.isEmpty()))
            return s;

        if (s.length() == 1)
            return s;

        return reverseString(s.substring(1)) + s.charAt(0);
    }

    public static void main(String... argvs) {
        List<String> strings = Arrays.asList(null, "", "h", "hello", "codenvy");

        for (String i : strings) {
            String reversed = reverseString(i);

            System.out.println();
            System.out.println("string: " + i + "^");
            System.out.println("reversed: " + reversed + "^");

            reversed = reverseString(reversed);
            System.out.println("reversed2: " + reversed + "^");
            System.out.println("match?: " + ((i == null) ? (reversed == i) : (reversed.equals(i))));
        }
    }
}
