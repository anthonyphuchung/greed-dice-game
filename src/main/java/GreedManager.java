/**
 * Author: Anthony Chung
 *
 * GreedManager will manage a game of Greed, a press-your-luck dice rolling game.
 * GreedManager will provide the following features:
 * - Defining the number of dice the players will be using.
 * - Defining the number of sides on the dice.
 * - Updating the score of a player.
 * - Retrieving the score of a specific player.
 * - Retrieving the list of players with the highest scores.
 * - Retrieving the list of all players.
 * - Retrieving the leaderboard.
 * -
 */

import java.util.*;

public class GreedManager {
    public static final int DICE_COUNT = 5; // Number of dice we're using.
    public static final int SIDES = 6; // Number of sides on the dice.

    private List<Player> players;
    private Set<String> playerNames;

    /**
     * Initializes a GreedManager object. The scores of each player in the given set of player name will be
     * set to 0 initially.
     *
     * @param playerNames - the set of player names who will be playing in the game of Greed.
     */
    public GreedManager(Set<String> playerNames) {
        if (playerNames.size() < 2) {
            throw new IllegalArgumentException("Greed can only be played with 2 or more players!");
        }

        this.playerNames = playerNames;
        players = new ArrayList<>();
        for (String playerName : playerNames) {
            players.add(new Player(playerName, 0));
        }
    }

    /**
     * Updates the score of the given player using the given diceValues. Leaderboard is also updated
     * after the player's score is updated.
     *
     * @param playerName - the name of the given player.
     * @param diceValues - the dice values which the player rolled.
     * @throws IllegalArgumentException if the given playerName does not exist in the game.
     * @throws IllegalArgumentException if the length of diceValues does not equal to the number of dice the players
     * are using.
     * @throws IndexOutOfBoundsException if any of the values in diceValues is not a valid dice roll.
     */
    public void put(String playerName, int[] diceValues) {
        if (!isValidPlayer(playerName)) {
            throw new IllegalArgumentException("Given player does not exist in the game!");
        } else if (diceValues.length != DICE_COUNT) {
            throw new IllegalArgumentException("Given diceValues did not roll the correct amount of dices!");
        }
        int score = 0;
        int[] valueCount = new int[SIDES];
        for (int diceValue : diceValues) {
            if (diceValue <= 0 || SIDES < diceValue) {
                throw new IndexOutOfBoundsException("Given diceValues contains an illegal dice value!");
            }
            valueCount[diceValue - 1]++;
        }

        for (int i = 0; i < SIDES; i++) {
            int numOfTriples = valueCount[i] / 3;
            int remainder = valueCount[i] - (numOfTriples * 3);
            if (i == 0) { // Roll of one
                score += (1000 * numOfTriples) + (100 * remainder);
            } else if (i == 1) { // Roll of two
                score += (200 * numOfTriples);
            } else if (i == 2) { // Roll of three
                score += (300 * numOfTriples);
            } else if (i == 3) { // Roll of four
                score += (400 * numOfTriples);
            } else if (i == 4) { // Roll of five
                score += (500 * numOfTriples) + (50 * remainder);
            } else { // Roll of 6
                score += (600 * numOfTriples);
            }
        }

        int playerIndex = indexOf(playerName);
        Player player = players.get(playerIndex);
        player.score += score;
        Collections.sort(players);
    }

    /**
     * Returns the score of the given player if they exist within the game.
     * Otherwise, the player does not exist in the game, so return -1.
     * @param playerName - the name of the given player.
     * @return the score of the given player if they exist within the game. Otherwise, return -1.
     */
    public int getScore(String playerName) {
        if (!isValidPlayer(playerName)) {
            return -1;
        }
        int playerIndex = indexOf(playerName);
        Player player = players.get(playerIndex);
        return player.score;
    }

    /**
     * Returns the list of players who have the highest scores in the game.
     * @return - the list of players who have the highest scores in the game.
     */
    public List<String> getWinners() {
        List<String> winners = new ArrayList<>();
        int highestScore = players.get(0).score;
        int i = 0;
        while (i < players.size() && players.get(i).score == highestScore) {
            winners.add(players.get(i).name);
            i++;
        }
        return winners;
    }

    /**
     * Returns the set of player names who are currently in the game.
     * @return the set of player names who are currently in the game.
     */
    public Set<String> getPlayerNames() {
        return this.playerNames;
    }

    /**
     * Returns a String representation of the leaderboard.
     * The leaderboard will be formatted like a list where players with higher scores are
     * located near the front of the list and players with lower scores are near the end of the list.
     * One example may include:
     * "[playerOne (highScore), playerTwo (lowerScore), playerThree (2), playerFour (0)]
     * @return - a String represented of the leaderboard.
     */
    public String getLeadboard() {
        return players.toString();
    }

    /**
     * Returns the number of players in the game.
     * @return - the number of players in the game.
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Returns true if playerName exists within the game. Otherwise, return false.
     * @param playerName - the name of the given player.
     * @return - true if playerName exists within the game. Otherwise, return false.
     */
    public boolean isValidPlayer(String playerName) {
        return playerNames.contains(playerName);
    }

    /**
     * Resets game by resetting the scores of each player down to zero.
     */
    public void resetGame() {
        Set<String> playerNames = this.getPlayerNames();
        players = new ArrayList<>();
        for (String playerName : playerNames) {
            players.add(new Player(playerName, 0));
        }
    }

    /**
     * Returns the index in which the given player is located in this.players.
     * @param playerName - the name of the given player.
     * @return - the index in which the given player is located in this.players.
     */
    private int indexOf(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (playerName.equals(player.name)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Inner class which stores information for a single player.
     * A Player is defined by their name and their score.
     */
    private static class Player implements Comparable<Player> {
        public String name;
        public int score;

        /**
         * Constructs a new Player with the given name and given score
         * @param name - name of the Player
         * @param score - score of the Player
         */
        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        /**
         * If the score of this Player and the other Player are not equal:
         * Return a negative integer or a positive integer as the score of this Player is greater than
         * or less than the other Player.
         *
         * Otherwise, return a negative integer, zero, or a positive integer as the name of this Player
         * is lexicographically less than, equal to, or greater than the other Player.
         * @param other - the other Player.
         * @return - if the scores are not equal, return a negative integer or a positive integer as the
         * score of this Player is greater than or less than the other Player. Otherwise, return a negative integer,
         * zero, or a positive integer as the name of this Player is lexicographically less than, equal to, or greater
         * than the other Player.
         */
        public int compareTo(Player other) {
            if (this.score != other.score) {
                return other.score - this.score;
            } else {
                return this.name.compareTo(other.name);
            }
        }

        /**
         * Returns a String representation of this Player. A Player will have their name and
         * their score attached to them.
         * One example may include:
         * "playerName (score)"
         * @return - a String representation of this Player.
         */
        public String toString() {
            return name + " (" + score + ")";
        }
    }
}