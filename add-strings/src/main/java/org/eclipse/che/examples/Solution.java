package org.eclipse.che.examples;

/*
 * https://leetcode.com/problems/add-strings/description/
 *
 * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.
 *
 * Note:
 * - The length of both num1 and num2 is < 5100.
 * - Both num1 and num2 contains only digits 0-9.
 * - Both num1 and num2 does not contain any leading zero.
 * - You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
public class Solution {
    private static boolean IS_DEBUG = true;

    public static String addStrings(String num1, String num2) {
        int i1 = num1.length() - 1;
        int i2 = num2.length() - 1;
        int sum = 0;
        int digit = 0;
        int carry = 0;
        String result = "";

        while (true) {
            if ((i1 >= 0) && (i2 >= 0)) {
                sum = Character.getNumericValue(num1.charAt(i1)) + Character.getNumericValue(num2.charAt(i2)) + carry;
                digit = sum % 10;
                carry = sum / 10;
                result = digit + result;
                --i1;
                --i2;

                continue;
            }

            if ((i1 >= 0) && (i2 < 0)) {
                sum = Character.getNumericValue(num1.charAt(i1)) + carry;
                digit = sum % 10;
                carry = sum / 10;
                result = digit + result;
                --i1;

                continue;
            }

            if ((i1 < 0) && (i2 >= 0)) {
                sum = Character.getNumericValue(num2.charAt(i2)) + carry;
                digit = sum % 10;
                carry = sum / 10;
                result = digit + result;
                --i2;

                continue;
            }

            if (carry > 0)
                result = carry + result;

            break;
        }

        if (IS_DEBUG) {
            long result2 = Long.parseLong(num1) + Long.parseLong(num2);

            System.out.println();
            System.out.println("num1: " + num1);
            System.out.println("num2: " + num2);
            System.out.println("result: " + result + " (" + result2 + ")");
            System.out.println("match?: " + result.equals(Long.toString(result2)));
        }

        return result;
    }

    public static void main(String... argvs) {
        addStrings("0", "0");
        addStrings("1", "2");
        addStrings("3", "45");
        addStrings("67", "890");
        addStrings("717", "1225");
        addStrings("1234", "567890");
    }
}
