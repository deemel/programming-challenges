package org.eclipse.che.examples;

import java.util.Arrays;

/*
 * https://leetcode.com/problems/trapping-rain-water/description/
 *
 * Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
 *
 * Example:
 * Input: [0,1,0,2,1,0,1,3,2,1,2,1]
 * Output: 6
 */
public class Solution {
    private static boolean IS_DEBUG = true;

    private static int trapInternal(int[] height, int wallLeft, int wallRight) {
        int rainWater = 0;

        if ((wallRight - wallLeft + 1) <= 2)
            return rainWater;

        for (int i = wallLeft; i <= wallRight; ++i) {
            if (height[i] > 0) {
                --height[i];
                continue;
            }

            ++rainWater;
        }

        int wallLeft2 = -1;
        for (int i = wallLeft; i <= wallRight; ++i) {
            if (height[i] <= 0)
                continue;

            wallLeft2 = i;
            break;
        }

        int wallRight2 = -1;
        for (int i = wallRight; i >= wallLeft; --i) {
            if (height[i] <= 0)
                continue;

            wallRight2 = i;
            break;
        }

        return rainWater + trapInternal(height, wallLeft2, wallRight2);
    }

    public static int trap0(int[] height) {
        if (height == null)
            return 0;

        if (IS_DEBUG) {
            System.out.println();
            System.out.println("height: " + Arrays.toString(height));

            for (int i = 0; i < height.length; ++i) {
                if (height[i] <= 0) {
                    System.out.println(".");
                    continue;
                }

                for (int j = 0; j < height[i]; ++j)
                    System.out.print("#");
                System.out.println();
            }
        }

        int wallLeft = -1;
        for (int i = 0; i < height.length; ++i) {
            if (height[i] <= 0)
                continue;

            wallLeft = i;
            break;
        }

        int wallRight = -1;
        for (int i = (height.length - 1); i >= 0; --i) {
            if (height[i] <= 0)
                continue;

            wallRight = i;
            break;
        }

        int rainWater = trapInternal(height, wallLeft, wallRight);

        if (IS_DEBUG) {
            System.out.println("rainWater: " + rainWater);
        }

        return rainWater;
    }

    public static int trap(int[] height) {
        int rainWater = 0;

        if ((height == null) || (height.length <= 2))
            return rainWater;

        int wallMark = -1;
        int wallLow = 0;

        // Add trapped rain water from beginning to highest wall, which will be marked.
        for (; wallLow < height.length; ++wallLow) {
            if (height[wallLow] <= 0)
                continue;

            // Found low wall.
            if (wallMark < 0)
                wallMark = wallLow;

            int wallHigh = (wallLow + 1);
            for (; wallHigh < height.length; ++wallHigh) {
                if (height[wallHigh] < height[wallLow])
                    continue;

                // Found high wall.
                for (int i = (wallLow + 1); i < wallHigh; ++i)
                    rainWater += (height[wallLow] - height[i]);

                wallLow = wallHigh - 1;
                break;
            }

            // Did not find high wall.
            if (wallHigh >= height.length)
                break;

            wallMark = wallHigh;
        }

        // Add trapped rain water from end to highest wall, which was marked.
        for (wallLow = (height.length - 1); wallLow >= wallMark; --wallLow) {
            if (height[wallLow] <= 0)
                continue;

            // Found low wall.
            int wallHigh = (wallLow - 1);
            for (; wallHigh >= wallMark; --wallHigh) {
                if (height[wallHigh] < height[wallLow])
                    continue;

                // Found high wall.
                for (int i = (wallLow - 1); i > wallHigh; --i)
                    rainWater += (height[wallLow] - height[i]);

                wallLow = wallHigh + 1;
                break;
            }
        }

        if (IS_DEBUG) {
            System.out.println();
            System.out.println("height: " + Arrays.toString(height));

            for (int i = 0; i < height.length; ++i) {
                if (height[i] <= 0) {
                    System.out.println(".");
                    continue;
                }

                for (int j = 0; j < height[i]; ++j)
                    System.out.print("#");
                System.out.println();
            }

            System.out.println("rainWater: " + rainWater);
        }

        return rainWater;
    }

    public static void main(String... argvs) {
        trap(new int[]{});
        trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1, 1, 1, 2});
        trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1, 1, 1, 3});
        trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1, 1, 1, 30});
        trap(new int[]{30, 1, 30});
        trap(new int[]{30, 30});
        trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 0, 0, 0, 1});
        trap(new int[]{0, 2, 0});
        trap(new int[]{4, 2, 3});
    }
}
