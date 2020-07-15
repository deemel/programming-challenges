package org.eclipse.che.examples;

/*
 * Return true if binary representation of integer contains consecutives 1s, else return false.
 */
public class Solution {
    private static boolean   IS_DEBUG = true;
    private static final int MASK_11  = 0x3;

    public static boolean hasConsecutiveOnes(int num) {
        int num2 = num;
        boolean result = false;

        while (num2 != 0) {
            if ((num2 & MASK_11) == MASK_11) {
                result = true;
                break;
            }

            num2 >>= 1;
        }

        if (IS_DEBUG) {
            System.out.println();
            System.out.println("num: " + num + " (" + Integer.toBinaryString(num) + ")");
            System.out.println("result: " + result);
        }

        return result;
    }

    public static void main(String... argvs) {
        hasConsecutiveOnes(0);
        hasConsecutiveOnes(3);
        hasConsecutiveOnes(16);
        hasConsecutiveOnes(69);
        hasConsecutiveOnes(77);
    }
}
