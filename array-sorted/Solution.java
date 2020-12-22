package org.eclipse.che.examples;

import java.util.Arrays;

/*
 * https://www.geeksforgeeks.org/program-check-array-sorted-not-iterative-recursive/
 *
 * Given an array of size n, write a program to check if it is sorted in ascending order or not. Equal values are allowed in array and two consecutive equal values are considered sorted.
 *
 * Examples:
 *
 * Input: [20, 21, 45, 89, 89, 90]
 * Output: true
 *
 * Input: [20, 20, 45, 89, 89, 90]
 * Output: true
 *
 * Input: [20, 20, 78, 98, 99, 97]
 * Output: false
 */
public class Solution {
    public static boolean isSortedIterative(int[] input) {
        if ((input == null) || (input.length <= 1))
            return true;

        for (int i = 0; i <= (input.length - 2); ++i)
            if (input[i] > input[i + 1])
                return false;

        return true;
    }

    public static boolean isSortedRecursive(int[] input) {
        if ((input == null) || (input.length <= 1))
            return true;

        if (input[0] > input[1])
            return false;

        return isSortedRecursive(Arrays.copyOfRange(input, 1, input.length));
    }

    public static boolean isSortedRecursiveNoCopy(int[] input, int indexStart) {
        if ((input == null) || (input.length <= 1)
                || (indexStart >= (input.length - 1)))
            return true;

        if (input[indexStart] > input[indexStart + 1])
            return false;

        return isSortedRecursiveNoCopy(input, (indexStart + 1));
    }

    public static boolean isSorted(int[] input) {
        // return isSortedIterative(input);
        // return isSortedRecursive(input);
        return isSortedRecursiveNoCopy(input, 0);
    }

    public static void test(int[] input, boolean expected) {
        boolean output = isSorted(input);

        System.out.println();
        System.out.println("input: " + Arrays.toString(input));
        System.out.println("output: " + output);
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (output == expected));
    }

    public static void main(String... argvs) {
        test(new int[] { 20, 21, 45, 89, 89, 90 }, true);
        test(new int[] { 20, 20, 45, 89, 89, 90 }, true);
        test(new int[] { 20, 20, 78, 98, 99, 97 }, false);
        test(new int[] {}, true);
        test(new int[] { 20 }, true);
        test(new int[] { 20, 20 }, true);
        test(new int[] { 20, 0 }, false);
        test(new int[] { 20, 20, 21, 23, 22, 0, 7 }, false);
        test(new int[] { 20, 20, 21, 22, 23, 24, 1000 }, true);
        test(null, true);
    }
}
