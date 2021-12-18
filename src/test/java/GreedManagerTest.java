import org.junit.*;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

public class GreedManagerTest {
    @Test
    public void testScores1() {
        Set<String> names = new HashSet<>();
        names.add("Anthony");
        names.add("TestName");
        names.add("123");
        GreedManager game = new GreedManager(names);
        int[] scores = new int[] {2, 3, 4, 6, 2};
        game.put("Anthony", scores);
        assertEquals(1, 1);
        assertEquals((long) game.playerScores.get("Anthony"), 0);
    }
}
