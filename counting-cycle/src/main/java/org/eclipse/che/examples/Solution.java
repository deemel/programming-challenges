package org.eclipse.che.examples;

import java.util.Arrays;

/*
 * https://codereview.stackexchange.com/questions/77152/counting-a-cycle
 *
 * Each element of an int array points to the another element, eventually creating a cycle. Starting at array[0], find the length of the cycle.
 *
 * Constraints:
 * - Must be in Java 7
 * - Elements will always be positive or 0 and never point outside the array
 * - Elements will never point to themselves
 * - There will always be a cycle
 * - The cycle will be at least length 2
 */
public class Solution {
    private static boolean IS_DEBUG = true;

    public static int findCycleLength(int[] nums) {
        int i = 0;
        while (true) {
            if (nums[i] < 0)
                break;

            nums[i] = -nums[i];
            i = -nums[i];
        }

        int j = -nums[i];
        int len = 1;
        while (true) {
            if (j == i)
                break;

            j = -nums[j];
            ++len;
        }

        if (IS_DEBUG) {
            System.out.println();
            System.out.println("nums: " + Arrays.toString(nums));
            System.out.println("i: " + i);
            System.out.println("len: " + len);
        }

        return len;
    }

    public static void main(String... argvs) {
        findCycleLength(new int[]{1, 0});
        findCycleLength(new int[]{1, 2, 1});
        findCycleLength(new int[]{1, 3, 0, 4, 1});
        findCycleLength(new int[]{1, 3, 0, 4, 0});
    }
}
