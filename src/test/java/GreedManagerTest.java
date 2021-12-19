import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GreedManagerTest {
    private GreedManager game;

    public GreedManagerTest() {
        Set<String> names = new HashSet<>();
        for (int i = 1; i <= 4; i++) {
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
}
