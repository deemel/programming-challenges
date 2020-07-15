#!/bin/bash

#
# https://leetcode.com/problems/transpose-file/description/
#
# Given a text file file.txt, transpose its content.
#
# You may assume that each row has the same number of columns and each field is separated by the ' ' character.
#
# Example:
#
# If file.txt has the following content:
# name age
# alice 21
# ryan 30
#
# Output the following:
# name alice ryan
# age 21 30
#

FILE_INPUT="file.txt"

awk '
  {
    for (col = 1; col <= NF; ++col)
      array[col] = ((array[col] == "") ? ($col) : (array[col]" "$col))
  }

  END {
    for (col = 1; col <= NF; ++col)
      print array[col]
  }
' $FILE_INPUT

exit $?
