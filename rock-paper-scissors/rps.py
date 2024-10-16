#!/usr/bin/env python3
 
from enum import IntEnum
import random
 
class RPS():
    OUTCOMES = [[0, 2, 1],
                [1, 0, 2],
                [2, 1, 0]]
    RESULTS = ["draw", "hand 1 wins", "hand 2 wins"]
 
class Hand(IntEnum):
    ROCK = 0
    PAPER = 1
    SCISSOR = 2
 
    def getOutcome(self, opponent: "Hand") -> int:
        return RPS.OUTCOMES[self][opponent]
 
    def compete(self, opponent: "Hand") -> str:
        return f"{self.name:>{len(self.SCISSOR.name)}} vs {opponent.name:>{len(self.SCISSOR.name)}}: {RPS.RESULTS[self.getOutcome(opponent)]}"
 
def generateHands(n):
    # https://www.geeksforgeeks.org/python-which-is-faster-to-initialize-lists/
    # return zip([Hand(random.randint(Hand.ROCK, Hand.SCISSOR)) for _ in range(n)], [Hand(random.randint(Hand.ROCK, Hand.SCISSOR)) for _ in range(n)])

    # https://stackoverflow.com/questions/231767/what-does-the-yield-keyword-do
    for _ in range(n):
        yield Hand(random.randint(Hand.ROCK, Hand.SCISSOR)), Hand(random.randint(Hand.ROCK, Hand.SCISSOR))
 
if __name__ == "__main__":
    for hand1, hand2 in generateHands(20):
        print(hand1.compete(hand2))
