from enum import IntEnum
import random

OUTCOMES = [[0, 2, 1],
            [1, 0, 2],
            [2, 1, 0]]
RESULTS = ['draw', 'player 1 wins', 'player 2 wins']


class Hand(IntEnum):
    ROCK = 0
    PAPER = 1
    SCISSOR = 2

    def compete(self, opponent: 'Hand') -> int:
        return OUTCOMES[self][opponent]

    def __str__(self):
        return self.name


def match(item1, item2):
    print(f'{item1.name} vs {item2.name}: {RESULTS[item1.compete(item2)]}')


def generateitems(n):
    # https://www.geeksforgeeks.org/python-which-is-faster-to-initialize-lists/
    # return zip([Hand(random.randint(Hand.ROCK, Hand.SCISSOR)) for _ in range(n)], [Hand(random.randint(Hand.ROCK, Hand.SCISSOR)) for _ in range(n)])

    # https://stackoverflow.com/questions/231767/what-does-the-yield-keyword-do
    for _ in range(n):
        yield Hand(random.randint(Hand.ROCK, Hand.SCISSOR)), Hand(random.randint(Hand.ROCK, Hand.SCISSOR))


if __name__ == '__main__':
    for i1, i2 in generateitems(20):
        match(i1, i2)
