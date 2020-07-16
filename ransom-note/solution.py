#
# https://leetcode.com/problems/ransom-note/
#
# Given an arbitrary ransom note string and another string containing
# letters from all the magazines, write a function that will return
# true if the ransom note can be constructed from the magazines;
# otherwise, it will return false.
#
# Each letter in the magazine string can only be used once in your ransom note.
#

import array
from collections import defaultdict
from collections import Counter

ORD_A = ord('a')


class Solution:
    # solution using array, not list
    def canConstruct(self, ransomNote: str, magazine: str) -> bool:
        # https://docs.python.org/3/library/array.html
        alphabet = array.array('i', (0 for i in range(0, 26)))
        for i in magazine:
            alphabet[ord(i) - ORD_A] += 1

        for i in ransomNote:
            j = ord(i) - ORD_A
            if (alphabet[j] <= 0):
                return False

            alphabet[j] -= 1

        return True

    # solution using in-place search twice
    def canConstruct_inPlaceSearch(self, ransomNote: str, magazine: str) -> bool:
        for i in ransomNote:
            if (i not in magazine):
                return False

            magazine = magazine.replace(i, '', 1)

        return True

    # solution using in-place search once
    def canConstruct_inPlaceSearch2(self, ransomNote: str, magazine: str) -> bool:
        for i in ransomNote:
            j = magazine.find(i)
            if (j < 0):
                return False

            # strings are immutable; create new string using slice & concatenation
            magazine = magazine[:j] + magazine[(j + 1):]

        return True

    # solution using dictionary
    def canConstruct_dict(self, ransomNote: str, magazine: str) -> bool:
        alphabet = defaultdict(int)
        for i in magazine:
            alphabet[i] += 1

        for i in ransomNote:
            if (alphabet[i] <= 0):
                return False

            alphabet[i] -= 1

        return True

    # solution using counter
    def canConstruct_counter(self, ransomNote: str, magazine: str) -> bool:
        # https://www.geeksforgeeks.org/python-count-occurrences-of-a-character-in-string/
        alphabet = Counter(magazine)
        for i in ransomNote:
            if (alphabet[i] <= 0):
                return False

            alphabet[i] -= 1

        return True


if __name__ == '__main__':
    solution = Solution()

    print(solution.canConstruct("a", "b"))
    print(solution.canConstruct("aa", "ab"))
    print(solution.canConstruct("aa", "aab"))
