package org.eclipse.che.examples;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/*
 * https://leetcode.com/problems/valid-parentheses/description/
 *
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
 *
 * An input string is valid if:
 *
 * 1. Open brackets must be closed by the same type of brackets.
 * 2. Open brackets must be closed in the correct order.
 *
 * Note that an empty string is also considered valid.
 * 
 * Example 1:
 *   Input: "()"
 *   Output: true
 *
 * Example 2:
 *   Input: "()[]{}"
 *   Output: true
 *
 * Example 3:
 *   Input: "(]"
 *   Output: false
 *
 * Example 4:
 *   Input: "([)]"
 *   Output: false
 *
 * Example 5:
 *   Input: "{[]}"
 *   Output: true
 */
public class Solution {
    static char QUOTE = '"';
    static Map<Character, Character> BRACKETS = new HashMap<Character, Character>() {
        {
            put(')', '(');
            put('}', '{');
            put(']', '[');
        }
    };

    public static boolean isValid(String input) {
        if ((input == null) || (input.isEmpty()))
            return true;

        Stack<Character> stack = new Stack<>();
        boolean isFoundOpenQuote = false;

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (c == QUOTE) {
                isFoundOpenQuote = !isFoundOpenQuote;
                continue;
            }

            if (isFoundOpenQuote)
                continue;

            if (!BRACKETS.containsKey(c)) {
                stack.push(c);
                continue;
            }

            if (stack.isEmpty())
                return false;

            if (stack.pop() == BRACKETS.get(c))
                continue;

            return false;
        }

        if (isFoundOpenQuote)
            return false;

        return stack.isEmpty();
    }

    public static void test(String input, boolean expected) {
        boolean output = isValid(input);

        System.out.println();
        System.out.println("input: " + input);
        System.out.println("output: " + output);
        System.out.println("expected: " + expected);
        System.out.println("match?: " + (output == expected));
    }

    public static void main(String... argvs) {
        test("()", true);
        test("()[]{}", true);
        test("(]", false);
        test("([)]", false);
        test("{[]}", true);
        test("", true);
        test(null, true);
        test("(", false);
        test("}", false);
        test("([)", false);
        test("\"", false);
        test("\"\"", true);
        test("\"\"\"", false);
        test("\"\"\"({[\"", true);
        test("\"\"\"({[\"(", false);
        test("\"\"\"({[\"({[]})", true);
        test("\"\"\"({[\"({[\"\"]})", true);
        test("\"{)(}{)([){}[\"", true);
        test("\"]\"", true);
    }
}
