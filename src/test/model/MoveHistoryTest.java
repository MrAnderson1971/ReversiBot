package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class MoveHistoryTest {

    MoveHistory defaultHistory;
    MoveHistory mh2;
    MoveHistory error;

    @BeforeEach
    void runBefore() {
        defaultHistory = new MoveHistory(new Player(1, "Player 1"),
                new Player(-1, "Player 2"));
        mh2 = new MoveHistory(new Player(1, "Player 1"),
                new Player(-1, "Player2"));
        error = new MoveHistory(new Player(1, "1"),
                new Player(-1, "2"));

        mh2.add(new int[]{1, 1}, "Player 1");
        mh2.add(new int[]{2, 2}, "Player 2");
        mh2.add(new int[]{3, 4}, "Player 1");
        mh2.add(new int[]{4, 4}, "Player 2");
        mh2.add(new int[]{5, 5}, "Player 1");
        mh2.add(new int[]{6, 6}, "Player 2");
        mh2.add(new int[]{7, 2}, "Player 1");
        mh2.add(new int[]{0, 0}, "Player 2");

        error.add(new int[]{9, 9}, "1");
    }

    @Test
    void testToString() {
        assertEquals("", defaultHistory.toString());
        assertEquals("1. Player 1 made the move B1\n" +
                        "2. Player 2 made the move C2\n" +
                        "3. Player 1 made the move D4\n" +
                        "4. Player 2 made the move E4\n" +
                        "5. Player 1 made the move F5\n" +
                        "6. Player 2 made the move G6\n" +
                        "7. Player 1 made the move H2\n" +
                        "8. Player 2 made the move A0\n"
                , mh2.toString());
    }

    @Test
    void testGetOneLine() {
        assertEquals("1. Player 1 made the move B1.", mh2.getOneLine(0));
    }

    @Test
    void testGetLastMove() {
        assertEquals("Player 2 made the move A0.", mh2.getLastMove());
        assertEquals("1 made the move  9.", error.getLastMove());
    }

    @Test
    void testGetMoves() {
        ArrayList<int[]> moves = mh2.getMoves();

        assertTrue(Arrays.equals(moves.get(0), new int[]{1, 1}));
        assertTrue(Arrays.equals(moves.get(1), new int[]{2, 2}));
        assertTrue(Arrays.equals(moves.get(2), new int[]{3, 4}));
        assertTrue(Arrays.equals(moves.get(3), new int[]{4, 4}));
        assertTrue(Arrays.equals(moves.get(4), new int[]{5, 5}));
        assertTrue(Arrays.equals(moves.get(5), new int[]{6, 6}));
        assertTrue(Arrays.equals(moves.get(6), new int[]{7, 2}));
        assertTrue(Arrays.equals(moves.get(7), new int[]{0, 0}));
    }

    /*
    @Test
    void testGetPlayer1() {
        assertEquals("Player 1", defaultHistory.getPlayer1().toString());
    }

    @Test
    void testGetPlayer2() {
        assertEquals("Player 2", defaultHistory.getPlayer2().toString());
    }*/

}
