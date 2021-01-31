package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board defaultBoard;

    @BeforeEach
    void runBefore() {
        defaultBoard = new Board(new Player(1, null, "1"),
                new Player(-1, null, "-1"));
    }

    @Test
    void testToString() {
        assertEquals(" A B C D E F G H\n" +
                "----------------\n" +
                "| | | | | | | | |0\n" +
                "----------------\n" +
                "| | | | | | | | |1\n" +
                "----------------\n" +
                "| | | | |.| | | |2\n" +
                "----------------\n" +
                "| | | |X|O|.| | |3\n" +
                "----------------\n" +
                "| | |.|O|X| | | |4\n" +
                "----------------\n" +
                "| | | |.| | | | |5\n" +
                "----------------\n" +
                "| | | | | | | | |6\n" +
                "----------------\n" +
                "| | | | | | | | |7\n" +
                "----------------", defaultBoard.toString());
    }

    @Test
    void testGetCapturable() {
        ArrayList<int[]> twoFour = defaultBoard.getCapturable(2, 4);
        assertEquals(1, twoFour.size());
        assertTrue(twoFour.get(0)[0] == 3 && twoFour.get(0)[1] == 4);
    }

    @Test
    void testGetPossibleMoves() {
        assertEquals(4, defaultBoard.getPossibleMoves().size());
    }

    @Test
    void testIsPossibleMove() {
        assertFalse(defaultBoard.isPossibleMove(3, 3));
        assertFalse(defaultBoard.isPossibleMove(0, 0));
        assertTrue(defaultBoard.isPossibleMove(3, 5));
    }

}