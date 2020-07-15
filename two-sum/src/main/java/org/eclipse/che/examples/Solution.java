package org.eclipse.che.examples;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
 * https://leetcode.com/problems/two-sum/description/
 *
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 * Given nums = [2, 7, 11, 15], target = 9,
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class Solution {
    private static boolean IS_DEBUG = true;

    public static int[] twoSum(int[] nums, int target) {
        if (IS_DEBUG) {
            System.out.println();
            System.out.println("nums: " + Arrays.toString(nums));
            System.out.println("target: " + target);
        }

        HashMap<Integer, List<Integer>> map = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < nums.length; ++i) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], new LinkedList<Integer>(Arrays.asList(i)));
                continue;
            }

            List<Integer> list = map.get(nums[i]);
            list.add(i);

            map.put(nums[i], list);
        }

        int[] indices = new int[]{-1, -1};
        for (int i = 0; i < nums.length; ++i) {
            int addend = target - nums[i];

            if (!map.containsKey(addend))
                continue;

            List<Integer> list = map.get(addend);

            if ((addend == nums[i]) && (list.size() <= 1))
                continue;

            int j = list.get(0);

            indices[0] = i;
            indices[1] = ((j != i) ? (j) : (list.get(1)));

            break;
        }

        if (IS_DEBUG) {
            int sum = nums[indices[0]] + nums[indices[1]];

            System.out.println("indices: " + Arrays.toString(indices));
            System.out.println("nums[indices[0]]: " + nums[indices[0]]);
            System.out.println("nums[indices[1]]: " + nums[indices[1]]);
            System.out.println("nums[indices[0]] + nums[indices[1]]: " + sum);
            System.out.println("match?: " + (sum == target));
        }

        return indices;
    }

    public static int[] twoSum0(int[] nums, int target) {
        if (IS_DEBUG) {
            System.out.println();
            System.out.println("nums: " + Arrays.toString(nums));
            System.out.println("target: " + target);
        }

        int[] indices = new int[]{-1, -1};
        for (int i = 0; i < nums.length; ++i) {
            int addend = target - nums[i];

            for (int j = 0; j < nums.length; ++j) {
                if (j == i)
                    continue;

                if (nums[j] != addend)
                    continue;

                indices[0] = i;
                indices[1] = j;
                break;
            }

            if ((indices[0] < 0) || (indices[1] < 0))
                continue;

            break;
        }

        if (IS_DEBUG) {
            int sum = nums[indices[0]] + nums[indices[1]];

            System.out.println("indices: " + Arrays.toString(indices));
            System.out.println("nums[indices[0]]: " + nums[indices[0]]);
            System.out.println("nums[indices[1]]: " + nums[indices[1]]);
            System.out.println("nums[indices[0]] + nums[indices[1]]: " + sum);
            System.out.println("match?: " + (sum == target));
        }

        return indices;
    }

    public static void main(String... argvs) {
        twoSum(new int[]{2, 7, 11, 15}, 9);
        twoSum(new int[]{15, 11, 7, 2}, 9);
        twoSum(new int[]{15, 7, 11, 2}, 17);
        twoSum(new int[]{11, 7, -2, 15}, 5);
        twoSum(new int[]{7, 2, 11, 2, 15}, 4);
    }
}
