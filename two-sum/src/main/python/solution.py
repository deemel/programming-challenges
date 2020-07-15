#
# https://leetcode.com/problems/two-sum/description/
#
# Given an array of integers, return indices of the two numbers such that they add up to a specific target.
#
# You may assume that each input would have exactly one solution, and you may not use the same element twice.
#
# Example:
# Given nums = [2, 7, 11, 15], target = 9,
# Because nums[0] + nums[1] = 2 + 7 = 9, return [0, 1].
#

from typing import List

class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        map = {}
        for i in range(len(nums)):
            if nums[i] not in map:
                map[nums[i]] = [i]
            else:
                map[nums[i]].append(i)

        for i in range(len(nums)):
            addend = target - nums[i]

            if addend not in map:
                continue

            list = map[addend]

            if ((addend == nums[i]) and (len(list) <= 1)):
                continue;

            return [i, ((list[0]) if (list[0] != i) else (list[1]))]

        return [-1, -1]

    def twoSum0(self, nums: List[int], target: int) -> List[int]:
        for i in range(len(nums)):
            addend = target - nums[i]

            for j in range(len(nums)):
                if j == i:
                    continue

                if nums[j] != addend:
                    continue

                return [i, j]

        return [-1, -1]

def callTwoSum(nums: List[int], target: int):
    print()
    print('nums:', nums, ', target:', target)
    print(Solution().twoSum(nums, target))

def main():
    callTwoSum([2, 7, 11, 15], 9)
    callTwoSum([2, 7, 11, 15], 4)
    callTwoSum([2, 7, 11, 15, 2], 4)

if __name__ == "__main__":
    main()
