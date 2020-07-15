package org.eclipse.che.examples;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 * https://leetcode.com/problems/the-skyline-problem/description/
 *
 * A city's skyline is the outer contour of the silhouette formed by all the buildings in that city when viewed from a distance. Now suppose you are given the locations and height of all the buildings as shown on a cityscape photo (Figure A), write a program to output the skyline formed by these buildings collectively (Figure B).
 *
 * The geometric information of each building is represented by a triplet of integers [Li, Ri, Hi], where Li and Ri are the x coordinates of the left and right edge of the ith building, respectively, and Hi is its height. It is guaranteed that 0 ≤ Li, Ri ≤ INT_MAX, 0 < Hi ≤ INT_MAX, and Ri - Li > 0. You may assume all buildings are perfect rectangles grounded on an absolutely flat surface at height 0.
 *
 * For instance, the dimensions of all buildings in Figure A are recorded as: [ [2 9 10], [3 7 15], [5 12 12], [15 20 10], [19 24 8] ] .
 *
 * The output is a list of "key points" (red dots in Figure B) in the format of [ [x1,y1], [x2, y2], [x3, y3], ... ] that uniquely defines a skyline. A key point is the left endpoint of a horizontal line segment. Note that the last key point, where the rightmost building ends, is merely used to mark the termination of the skyline, and always has zero height. Also, the ground in between any two adjacent buildings should be considered part of the skyline contour.
 *
 * For instance, the skyline in Figure B should be represented as:[ [2 10], [3 15], [7 12], [12 0], [15 10], [20 8], [24, 0] ].
 *
 * Notes:
 * - The number of buildings in any input list is guaranteed to be in the range [0, 10000].
 * - The input list is already sorted in ascending order by the left x position Li.
 * - The output list must be sorted by the x position.
 * - There must be no consecutive horizontal lines of equal height in the output skyline. For instance, [...[2 3], [4 5], [7 5], [11 5], [12 7]...] is not acceptable; the three lines of height 5 should be merged into one in the final output as such: [...[2 3], [4 5], [12 7], ...]
 */
public class Solution {
    private static boolean IS_DEBUG   = true;
    private static int     IDX_LEFT   = 0;
    private static int     IDX_RIGHT  = 1;
    private static int     IDX_HEIGHT = 2;

    private static LinkedList<int[]> buildingsMatrixToLinkedList(int[][] buildings) {
        LinkedList<int[]> buildingsList = new LinkedList<int[]>();

        if (buildings == null)
            return buildingsList;

        for (int i = 0; i < buildings.length; ++i)
            buildingsList.add(buildings[i]);

        return buildingsList;
    }

    private static LinkedList<int[]> buildingsToSkyline(LinkedList<int[]> buildings) {
        LinkedList<int[]> skyline = new LinkedList<int[]>();

        if ((buildings == null) || (buildings.isEmpty()))
            return skyline;

        int[] building = null;
        for (int i = 0; i < buildings.size(); ++i) {
            building = buildings.get(i);
            skyline.add(new int[]{building[IDX_LEFT], building[IDX_HEIGHT]});
        }

        skyline.add(new int[]{building[IDX_RIGHT], 0});

        return skyline;
    }

    private static boolean isScenario(int[] buildingToAdd, LinkedList<int[]> buildingsNoOverlap) {
        if (isScenario1(buildingToAdd, buildingsNoOverlap))
            return true;

        if (isScenario2(buildingToAdd, buildingsNoOverlap))
            return true;

        if (isScenario3(buildingToAdd, buildingsNoOverlap))
            return true;

        if (isScenario4(buildingToAdd, buildingsNoOverlap))
            return true;

        if (isScenario5(buildingToAdd, buildingsNoOverlap))
            return true;

        if (IS_DEBUG) {
            System.out.println("Unexpected scenario.");
            System.out.println("  buildingToAdd: " + Arrays.toString(buildingToAdd));
            System.out.println("  buildingsNoOverlap: "
                               + Arrays.deepToString(buildingsNoOverlap.toArray(new int[buildingsNoOverlap.size()][])));
        }

        return false;
    }

    /*
     * Scenario 1. Code may be refactored, but left as is for readability.
     *
     *                       buildingNoOverlapLast[IDX_LEFT]
     *                       v
     *                       +-----+
     *                       |     |
     *                       |     |
     *                       |     |
     *                       |     |
     *                  -----|--|--|--|--
     *                       ^  a  b  c
     * buildingToAdd[IDX_LEFT]
     *
     */
    private static boolean isScenario1(int[] buildingToAdd, LinkedList<int[]> buildingsNoOverlap) {
        int[] buildingNoOverlapLast = buildingsNoOverlap.getLast();

        if (!(buildingToAdd[IDX_LEFT] < buildingNoOverlapLast[IDX_LEFT]))
            return false;

        int[] buildingToAddRight = null;
        int[] buildingToAddLeft = null;
        int[] buildingNoOverlapLastRemoved = null;
        boolean rv;

        /*
         * Scenario 1a.
         */
        if (buildingToAdd[IDX_RIGHT] < buildingNoOverlapLast[IDX_LEFT]) {
            buildingToAddLeft = buildingToAdd;
            buildingNoOverlapLastRemoved = buildingsNoOverlap.removeLast();
            rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

            buildingsNoOverlap.add(buildingNoOverlapLastRemoved);
            return rv;
        }

        /*
         * Scenario 1b.
         */
        if (buildingToAdd[IDX_RIGHT] == buildingNoOverlapLast[IDX_LEFT]) {
            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_LEFT];

                buildingToAddLeft = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                return rv;
            }

            buildingToAddLeft = buildingToAdd;
            buildingNoOverlapLastRemoved = buildingsNoOverlap.removeLast();
            rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

            buildingsNoOverlap.add(buildingNoOverlapLastRemoved);
            return rv;
        }

        /*
         * Scenario 1c.
         */
        if (buildingToAdd[IDX_RIGHT] < buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingToAdd[IDX_RIGHT] = buildingNoOverlapLast[IDX_LEFT];

                buildingToAddLeft = buildingToAdd;
                buildingNoOverlapLastRemoved = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                buildingsNoOverlap.add(buildingNoOverlapLastRemoved);
                return rv;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_LEFT];

                buildingToAddLeft = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                return rv;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_RIGHT];

                buildingToAddLeft = buildingToAdd;
                buildingNoOverlapLastRemoved = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                buildingsNoOverlap.add(buildingNoOverlapLastRemoved);
                return rv;
            }
        }

        /*
         * Scenario 1d.
         */
        if (buildingToAdd[IDX_RIGHT] == buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingToAdd[IDX_RIGHT] = buildingNoOverlapLast[IDX_LEFT];

                buildingToAddLeft = buildingToAdd;
                buildingNoOverlapLastRemoved = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                buildingsNoOverlap.add(buildingNoOverlapLastRemoved);
                return rv;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_LEFT];

                buildingToAddLeft = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                return rv;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_LEFT];
                buildingNoOverlapLast[IDX_HEIGHT] = buildingToAdd[IDX_HEIGHT];

                buildingToAddLeft = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                return rv;
            }
        }

        /*
         * Scenario 1e.
         */
        if (buildingToAdd[IDX_RIGHT] > buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingToAddRight = new int[]{buildingNoOverlapLast[IDX_RIGHT], buildingToAdd[IDX_RIGHT], buildingToAdd[IDX_HEIGHT]};
                buildingToAdd[IDX_RIGHT] = buildingNoOverlapLast[IDX_LEFT];

                buildingToAddLeft = buildingToAdd;
                buildingNoOverlapLastRemoved = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                buildingsNoOverlap.add(buildingNoOverlapLastRemoved);
                buildingsNoOverlap.add(buildingToAddRight);

                return rv;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_LEFT];
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_RIGHT];

                buildingToAddLeft = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                return rv;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_LEFT];
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_RIGHT];
                buildingNoOverlapLast[IDX_HEIGHT] = buildingToAdd[IDX_HEIGHT];

                buildingToAddLeft = buildingsNoOverlap.removeLast();
                rv = isScenario(buildingToAddLeft, buildingsNoOverlap);

                return rv;
            }
        }

        return false; // Should never reach here.
    }

    /*
     * Scenario 2. Code may be refactored, but left as is for readability.
     *
     *                       buildingNoOverlapLast[IDX_LEFT]
     *                       v
     *                       +-----+
     *                       |     |
     *                       |     |
     *                       |     |
     *                       |     |
     *                  -----|--|--|--|--
     *                       ^  a  b  c
     * buildingToAdd[IDX_LEFT]
     *
     */
    private static boolean isScenario2(int[] buildingToAdd, LinkedList<int[]> buildingsNoOverlap) {
        int[] buildingNoOverlapLast = buildingsNoOverlap.getLast();

        if (!(buildingToAdd[IDX_LEFT] == buildingNoOverlapLast[IDX_LEFT]))
            return false;

        /*
         * Scenario 2a.
         */
        if (buildingToAdd[IDX_RIGHT] < buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingsNoOverlap.add((buildingsNoOverlap.size() - 1), buildingToAdd);
                buildingNoOverlapLast[IDX_LEFT] = buildingToAdd[IDX_RIGHT];

                return true;
            }
        }

        /*
         * Scenario 2b.
         */
        if (buildingToAdd[IDX_RIGHT] == buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_HEIGHT] = buildingToAdd[IDX_HEIGHT];
                return true;
            }
        }

        /*
         * Scenario 2c.
         */
        if (buildingToAdd[IDX_RIGHT] > buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingToAdd[IDX_LEFT] = buildingNoOverlapLast[IDX_RIGHT];
                buildingsNoOverlap.add(buildingToAdd);

                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_RIGHT];
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_RIGHT];
                buildingNoOverlapLast[IDX_HEIGHT] = buildingToAdd[IDX_HEIGHT];

                return true;
            }
        }

        return false; // Should never reach here.
    }

    /*
     * Scenario 3. Code may be refactored, but left as is for readability.
     *
     *                      buildingNoOverlapLast[IDX_LEFT]
     *                      v
     *                      +-----+
     *                      |     |
     *                      |     |
     *                      |     |
     *                      |     |
     *                 -----||-|--|--|--
     *                       ^ a  b  c
     * buildingToAdd[IDX_LEFT]
     *
     */
    private static boolean isScenario3(int[] buildingToAdd, LinkedList<int[]> buildingsNoOverlap) {
        int[] buildingNoOverlapLast = buildingsNoOverlap.getLast();

        if (!(buildingToAdd[IDX_LEFT] < buildingNoOverlapLast[IDX_RIGHT]))
            return false;

        /*
         * Scenario 3a.
         */
        if (buildingToAdd[IDX_RIGHT] < buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                int[] buildingNoOverlapLastSplit =
                                                   new int[]{buildingToAdd[IDX_RIGHT], buildingNoOverlapLast[IDX_RIGHT],
                                                           buildingNoOverlapLast[IDX_HEIGHT]};

                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_LEFT];
                buildingsNoOverlap.add(buildingToAdd);
                buildingsNoOverlap.add(buildingNoOverlapLastSplit);

                return true;
            }
        }

        /*
         * Scenario 3b.
         */
        if (buildingToAdd[IDX_RIGHT] == buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_LEFT];
                buildingsNoOverlap.add(buildingToAdd);

                return true;
            }
        }

        /*
         * Scenario 3c.
         */
        if (buildingToAdd[IDX_RIGHT] > buildingNoOverlapLast[IDX_RIGHT]) {
            if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingToAdd[IDX_LEFT] = buildingNoOverlapLast[IDX_RIGHT];
                buildingsNoOverlap.add(buildingToAdd);

                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_RIGHT];
                return true;
            }

            if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
                buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_LEFT];
                buildingsNoOverlap.add(buildingToAdd);

                return true;
            }
        }

        return false; // Should never reach here.
    }

    /*
     * Scenario 4. Code may be refactored, but left as is for readability.
     *
     *                 buildingNoOverlapLast[IDX_LEFT]
     *                 v
     *                 +-----+
     *                 |     |
     *                 |     |
     *                 |     |
     *                 |     |
     *            -----|-----|--|--
     *                       ^  a
     * buildingToAdd[IDX_LEFT]
     *
     */
    private static boolean isScenario4(int[] buildingToAdd, LinkedList<int[]> buildingsNoOverlap) {
        int[] buildingNoOverlapLast = buildingsNoOverlap.getLast();

        if (!(buildingToAdd[IDX_LEFT] == buildingNoOverlapLast[IDX_RIGHT]))
            return false;

        /*
         * Scenario 4a.
         */
        if (buildingToAdd[IDX_HEIGHT] < buildingNoOverlapLast[IDX_HEIGHT]) {
            buildingsNoOverlap.add(buildingToAdd);
            return true;
        }

        if (buildingToAdd[IDX_HEIGHT] == buildingNoOverlapLast[IDX_HEIGHT]) {
            buildingNoOverlapLast[IDX_RIGHT] = buildingToAdd[IDX_RIGHT];
            return true;
        }

        if (buildingToAdd[IDX_HEIGHT] > buildingNoOverlapLast[IDX_HEIGHT]) {
            buildingsNoOverlap.add(buildingToAdd);
            return true;
        }

        return false; // Should never reach here.
    }

    /*
     * Scenario 5. Code may be refactored, but left as is for readability.
     *
     *                buildingNoOverlapLast[IDX_LEFT]
     *                v
     *                +-----+
     *                |     |
     *                |     |
     *                |     |
     *                |     |
     *           -----|-----||-|--
     *                       ^ a
     * buildingToAdd[IDX_LEFT]
     *
     */
    private static boolean isScenario5(int[] buildingToAdd, LinkedList<int[]> buildingsNoOverlap) {
        int[] buildingNoOverlapLast = buildingsNoOverlap.getLast();

        if (!(buildingToAdd[IDX_LEFT] > buildingNoOverlapLast[IDX_RIGHT]))
            return false;

        /*
         * Scenario 5a.
         */
        buildingsNoOverlap.add(new int[]{buildingNoOverlapLast[IDX_RIGHT], buildingToAdd[IDX_LEFT], 0});
        buildingsNoOverlap.add(buildingToAdd);

        return true;
    }

    private static LinkedList<int[]> createBuildingsNoOverlap(LinkedList<int[]> buildings) {
        LinkedList<int[]> buildingsNoOverlap = new LinkedList<int[]>();

        for (int[] buildingToAdd : buildings) {
            if (buildingsNoOverlap.isEmpty()) {
                buildingsNoOverlap.add(buildingToAdd);
                continue;
            }

            if (isScenario(buildingToAdd, buildingsNoOverlap))
                continue;

            if (IS_DEBUG)
                return new LinkedList<int[]>();
        }

        return buildingsNoOverlap;
    }

    public static List<int[]> getSkyline(int[][] buildings) {
        if (IS_DEBUG) {
            System.out.println();
            System.out.println("buildings: " + Arrays.deepToString(buildings));
        }

        LinkedList<int[]> buildingsList = buildingsMatrixToLinkedList(buildings);
        LinkedList<int[]> buildingsNoOverlap = createBuildingsNoOverlap(buildingsList);
        LinkedList<int[]> skyline = buildingsToSkyline(buildingsNoOverlap);

        if (IS_DEBUG) {
            System.out.println("buildingsNoOverlap: "
                               + Arrays.deepToString(buildingsNoOverlap.toArray(new int[buildingsNoOverlap.size()][])));
            System.out.println("skyline: " + Arrays.deepToString(skyline.toArray(new int[skyline.size()][])));
        }

        return skyline;
    }

    public static void main(String... argvs) {
        List<int[]> skyline = null;
        String actual = null;
        String expected = null;

        skyline = getSkyline(null);
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected = "[]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));

        skyline = getSkyline(new int[][]{});
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected = "[]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));

        skyline = getSkyline(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}});
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected = "[[2, 10], [3, 15], [7, 12], [12, 0], [15, 10], [20, 8], [24, 0]]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));

        skyline = getSkyline(new int[][]{{3, 7, 8}, {3, 8, 7}, {3, 9, 6}, {3, 10, 5}, {3, 11, 4}, {3, 12, 3}, {3, 13, 2}, {3, 14, 1}});
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected = "[[3, 8], [7, 7], [8, 6], [9, 5], [10, 4], [11, 3], [12, 2], [13, 1], [14, 0]]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));

        skyline =
                  getSkyline(new int[][]{{2, 4, 70}, {3, 8, 30}, {6, 100, 41}, {7, 15, 70}, {10, 30, 102}, {15, 25, 76}, {60, 80, 91},
                          {70, 90, 72}, {85, 120, 59}});
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected = "[[2, 70], [4, 30], [6, 41], [7, 70], [10, 102], [30, 41], [60, 91], [80, 72], [90, 59], [120, 0]]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));

        skyline = getSkyline(new int[][]{{1, 11, 50}, {11, 21, 30}, {11, 31, 10}, {16, 51, 40}});
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected = "[[1, 50], [11, 30], [16, 40], [51, 0]]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));

        skyline =
                  getSkyline(new int[][]{{6765, 184288, 53874}, {13769, 607194, 451649}, {43325, 568099, 982005}, {47356, 933141, 123943},
                          {59810, 561434, 119381}, {75382, 594625, 738524}, {111895, 617442, 587304}, {143767, 869128, 471633},
                          {195676, 285251, 107127}, {218793, 772827, 229219}, {316837, 802148, 899966}, {329669, 790525, 416754},
                          {364886, 882642, 535852}, {368825, 651379, 6209}, {382318, 992082, 300642}, {397203, 478094, 436894},
                          {436174, 442141, 612149}, {502967, 704582, 918199}, {503084, 561197, 625737}, {533311, 958802, 705998},
                          {565945, 674881, 149834}, {615397, 704261, 746064}, {624917, 909316, 831007}, {788731, 924868, 633726},
                          {791965, 912123, 438310}});
        actual = Arrays.deepToString(skyline.toArray(new int[skyline.size()][]));
        expected =
                   "[[6765, 53874], [13769, 451649], [43325, 982005], [568099, 918199], [704582, 899966], [802148, 831007], [909316, 705998], [958802, 300642], [992082, 0]]";
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (actual.equals(expected)));
    }
}
