import org.junit.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class GreedManagerTest {
    public static final int PLAYER_COUNT = 5;

    private Set<String> names;
    private GreedManager game;

    public GreedManagerTest() {
        names = new HashSet<>();
        for (int i = 1; i <= PLAYER_COUNT; i++) {
            names.add("player " + i);
        }
        game = new GreedManager(names);
    }

    @Test
    public void testScores1() {
        int[] scores = new int[] {1, 1, 1, 1, 1};
        game.put("player 1", scores);
        assertEquals(game.get("player 1"), 1200);
    }

    @Test
    public void testScores2() {
        int[] scores = new int[] {1, 1, 1, 5, 1};
        game.put("player 2", scores);
        assertEquals(game.get("player 2"), 1150);
    }

    @Test
    public void testScores3() {
        int[] scores = new int[] {2, 3, 4, 6, 2};
        game.put("player 3", scores);
        assertEquals(game.get("player 3"), 0);
    }

    @Test
    public void testScores4() {
        int[] scores = new int[] {3, 4, 5, 3, 3};
        game.put("player 4", scores);
        assertEquals(game.get("player 4"), 350);
    }

    @Test
    public void testScores5() {
        int[] scores = new int[] {6, 5, 6, 5, 4};
        game.put("player 5", scores);
        assertEquals(game.get("player 5"), 100);
    }

    @Test
    public void testScores6() {
        assertEquals(game.get("unknown player"), -1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testInvalidScores1() {
        int[] scores = new int[] {1, -2, 3, 4, 3};
        game.put("player 1", scores);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInvalidScores2() {
        int[] scores = new int[] {1};
        game.put("player 1", scores);
    }

    @Test
    public void testWinners1() {
        int[] scores = new int[] {1, 1, 1, 1, 1};
        game.put("player 1", scores);
        List<String> winners = game.getWinners();
        String winner = winners.get(0);
        System.out.println(game.getLeadboard());
        assertEquals("player 1", winner);
    }

    @Test
    public void testWinners2() {
        int[] scores = new int[] {1, 1, 1, 1, 1};
        game.put("player 1", scores);
        game.put("player 5", scores);
        List<String> winners = game.getWinners();
        String firstWinner = winners.get(0);
        String secondWinner = winners.get(1);
        System.out.println(game.getLeadboard());
        assertEquals("player 1", firstWinner);
        assertEquals("player 5", secondWinner);
    }

    @Test
    public void testPlayerNames() {
        Set<String> playerNames = game.getPlayerNames();
        for (String name : playerNames) {
            assertTrue(names.contains(name));
        }
    }

    @Test
    public void testLeaderboard() {
        int[] scores = new int[] {1, 1, 1, 1, 1};
        game.put("player 1", scores);
        game.put("player 5", scores);
        StringBuilder sb = new StringBuilder();
        sb.append("[player 1 (1200), player 5 (1200), player 2 (0), player 3 (0), player 4 (0)]");
        assertEquals(game.getLeadboard(), sb.toString());
    }

    @Test
    public void testPlayerCount() {
        assertEquals(game.getPlayerCount(), PLAYER_COUNT);
    }

    @Test
    public void testResetGame() {
        int[] scores = new int[] {1, 1, 1, 1, 1};
        game.put("player 1", scores);
        game.put("player 5", scores);
        game.resetGame();
        for (String name : names) {
            assertEquals(game.get(name), 0);
        }
    }

    @Test (expected = IllegalArgumentException.class)
    public void testisValidPlayer1() {
        int[] scores = new int[] {1, 1, 1, 1, 1};
        game.put("unknown player", scores);
    }

    @Test
    public void testisValidPlayer2() {
        assertTrue(game.isValidPlayer("player 1"));
    }

    @Test
    public void testisValidPlayer3() {
        assertFalse(game.isValidPlayer("unknown player"));
    }
}
