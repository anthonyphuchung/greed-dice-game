import java.util.*;

public class GreedManager {
    public static final int DICE_COUNT = 5; // Number of dice we're using.
    public static final int SIDES = 6; // Number of sides on the dice.

    private List<Player> players;
    private Set<String> playerNames;

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

    public void put(String playerName, int[] diceValues) {
        if (isValidPlayer(playerName)) {
            throw new IllegalArgumentException("Given player does not exist in the game!");
        }
        int score = 0;
        int[] valueCount = new int[SIDES];
        for (int value : diceValues) {
            valueCount[value - 1]++;
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

    public int get(String playerName) {
        if (isValidPlayer(playerName)) {
            return -1;
        }
        int playerIndex = indexOf(playerName);
        Player player = players.get(playerIndex);
        return player.score;
    }

    public List<String> getWinner() {
        List<String> winners = new ArrayList<>();
        int highestScore = players.get(players.size() - 1).score;
        int i = players.size() - 1;
        while (i >= 0 && players.get(i).score == highestScore) {
            winners.add(players.get(i).name);
            i--;
        }
        return winners;
    }

    public int getPlayerCount() {
        return players.size();
    }

    public Set<String> getPlayerNames() {
        return this.playerNames;
    }

    public String getLeadboard() {
        return players.toString();
    }

    public boolean isValidPlayer(String playerName) {
        return playerNames.contains(playerName);
    }

    public void resetGame() {
        Set<String> playerNames = this.getPlayerNames();
        players = new ArrayList<>();
        for (String playerName : playerNames) {
            players.add(new Player(playerName, 0));
        }
    }

    private int indexOf(String playerName) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (playerName.equals(player.name)) {
                return i;
            }
        }
        return -1;
    }

    private static class Player implements Comparable<Player> {
        public String name;
        public int score;

        public Player(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public int compareTo(Player other) {
            if (this.score != other.score) {
                return this.score - other.score;
            } else {
                return this.name.compareTo(other.name);
            }
        }

        public String toString() {
            return name + " (" + score + ") \n";
        }
    }
}