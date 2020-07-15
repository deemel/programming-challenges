package org.eclipse.che.examples;

import java.util.Arrays;

/*
 * https://leetcode.com/problems/median-of-two-sorted-arrays/description/
 *
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 */
public class Solution {
    private static boolean IS_DEBUG = true;

    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length;
        int len2 = nums2.length;
        int lenTotal = len1 + len2;
        int lenHalf = lenTotal / 2;
        boolean isLenEven = (((lenTotal % 2) == 0) ? (true) : (false));
        int[] nums = new int[lenHalf + 1];
        int len = nums.length;
        int i = 0;
        int i1 = 0;
        int i2 = 0;

        while (true) {
            if (i >= len)
                break;

            if (i1 >= len1)
                i1 = -1;

            if (i2 >= len2)
                i2 = -1;

            if ((i1 < 0) && (i2 < 0))
                break;

            if ((i1 < 0) && (i2 >= 0)) {
                nums[i] = nums2[i2];
                ++i;
                ++i2;
                continue;
            }

            if ((i1 >= 0) && (i2 < 0)) {
                nums[i] = nums1[i1];
                ++i;
                ++i1;
                continue;
            }

            if (nums1[i1] <= nums2[i2]) {
                nums[i] = nums1[i1];
                ++i;
                ++i1;
                continue;
            }

            nums[i] = nums2[i2];
            ++i;
            ++i2;
        }

        double median = -1;
        if (lenTotal > 0)
            if (isLenEven)
                median = (nums[len - 1] + nums[len - 2]) / 2.0;
            else
                median = nums[len - 1];

        if (IS_DEBUG) {
            System.out.println();
            System.out.println("nums1: " + Arrays.toString(nums1));
            System.out.println("nums2: " + Arrays.toString(nums2));
            System.out.println("len1: " + len1);
            System.out.println("len2: " + len2);
            System.out.println("lenTotal: " + lenTotal);
            System.out.println("lenHalf: " + lenHalf);
            System.out.println("isLenEven: " + isLenEven);
            System.out.println("nums: " + Arrays.toString(nums));
            System.out.println("len: " + len);
            System.out.println("median: " + median);
        }

        return median;
    }

    public static void main(String... argvs) {
        findMedianSortedArrays(new int[]{1, 3}, new int[]{2});
        findMedianSortedArrays(new int[]{1, 2}, new int[]{3, 4});
        findMedianSortedArrays(new int[]{}, new int[]{});
        findMedianSortedArrays(new int[]{7}, new int[]{});
        findMedianSortedArrays(new int[]{}, new int[]{17});
        findMedianSortedArrays(new int[]{7}, new int[]{17});
        findMedianSortedArrays(new int[]{1, 2, 3, 4, 5}, new int[]{1, 2, 3, 4, 5});
    }
}
