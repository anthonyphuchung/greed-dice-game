# Greed (Farkle)
Greed is a press-your-luck dice rolling game. In the game, the player rolls the dice and tries to
earn as many points as possible from the result. For the purpose of this game, 
players are limited to a roll of five dice. The scoring method is as follows:
* A single one (100 points)
* A single five (500 points)
* Triple ones [1,1,1] (1000 points)
* Triple twos [2,2,2] (200 points)
* Triple threes [3,3,3] (300 points)
* Triple fours [4,4,4] (400 points)
* Triple fives [5,5,5] (500 points)
* Triple sixes [6,6,6] (600 points)

## Questions that I came up with
* Every time a player's score is updated, the leaderboard is also updated which can get computationally expensive if 
the game introduces a lot of players. If we know the number of rounds a user wants to play beforehand, we could utilize
bucket sort to make this computation more efficient since we know the largest possible score. 
I'm not entirely sure how I would implement this for an arbitrary number of rounds.
* Should users have to manually input their dice values or should the game randomly generate dice values for the user?

## Assumptions I made
* Dice values are valid (no negative, values exceeding the dice, and always integers).
* There are more than 2 players.
* Ties are allowed.
* There are an unlimited number of rounds.

## Different paths or solutions I contemplated
### Initial solution:
Initially, I approached this problem using a HashMap where the names of each player are mapped to their score.
The issue that I ran into early on was when I had to return the list of winners. Because I was using a Map,
the location of those who had the highest scores would need to be manually scanned for since Maps don't support
indexing.

### Final solution:
To resolve the previous issue, I decided to introduce a List where players in the front of the list
have higher scores and those with lower scores are near the end of the list. I also created a private inner class
to represent a single player in the game. The Player class also implements the Comparable interface so that when we sort
the list of players, the player with the higher scores are put in the front of the list.

### Other solutions I contemplated:
Another approach I considered was to use replace the List with a Doubly Linked List and use a HashMap
to map the names of each player to their Player object. The reason why I thought about this approach is because
when we update a player's score, it doesn't make sense to have to re-sort the entire leaderboard. Instead, we should
just put the player that we just updated in the correct position in the leaderboard which a Doubly Linked List would
be optimal for since it's add/remove operations are in constant time. Additionally, using a HashMap will allow us to
return the score in constant time since the player's names are mapped to their Player object.

### Next steps
Some next steps I would like to take this is to create a front-end application so that users can interact with
a GUI instead of the console.

## Acknowledgments
* [ardalis](https://github.com/ardalis/kata-catalog)