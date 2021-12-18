import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GreedManager {
    public static final int DICE_COUNT = 5; // Number of dice we're using.
    public static final int SIDES = 6; // Number of sides on the dice.

    public Map<String, Integer> playerScores;

    public GreedManager(Set<String> playerNames) {
        if (playerNames.size() < 2) {
            throw new IllegalArgumentException("Greed can only be played with 2 or more players!");
        }
        playerScores = new HashMap<>();
        for (String playerName : playerNames) {
            playerScores.put(playerName, 0);
        }
    }

    public void put(String playerName, int[] diceValues) {
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
            } else if (i == 2) {
                score += (300 * numOfTriples);
            } else if (i == 3) {
                score += (400 * numOfTriples);
            } else if (i == 4) {
                score += (500 * numOfTriples) + (50 * remainder);
            } else {
                score += (600 * numOfTriples);
            }
        }

        playerScores.put(playerName, playerScores.get(playerName) + score);
    }

    public void getWinner() {

    }

    public int getPlayerCount() {
        return playerScores.size();
    }

    public Set<String> getPlayerNames() {
        return playerScores.keySet();
    }

    public boolean isValidPlayer(String playerName) {
        return playerScores.containsKey(playerName);
    }


}