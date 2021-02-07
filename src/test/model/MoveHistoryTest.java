package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MoveHistoryTest {

    MoveHistory defaultHistory;
    MoveHistory mh2;

    @BeforeEach
    void runBefore() {
        defaultHistory = new MoveHistory(new Player(1, null, "Player 1"),
                new Player(-1, null, "Player2"));
        mh2 = new MoveHistory(new Player(1, null, "Player 1"),
                new Player(-1, null, "Player2"));

        mh2.add(new int[] {1, 2}, "Player 1");
        mh2.add(new int[] {3, 4}, "Player 2");
        mh2.add(new int[] {7, 2}, "Player 1");
    }

    @Test
    void testToString() {
        assertEquals("", defaultHistory.toString());
        assertEquals("1. Player 1 made the move B2\n" +
        "2. Player 2 made the move D4\n" +
        "3. Player 1 made the move H2\n", mh2.toString());
    }
}
