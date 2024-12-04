package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board defaultBoard;
    Board player1WinBoard;
    Board player2WinBoard;

    Board player1WinBoardClone;

    @BeforeEach
    void runBefore() {
        defaultBoard = new Board(new Player(1, "1"),
                new Player(-1, "-1"));

        player1WinBoard = new Board(new Player(1, "Gregory"),
                new Player(-1, "Manny"));

        player2WinBoard = new Board(new Player(1, "Loser"),
                new Player(-1, "Winner"));

        /*1. Gregory made the move C3
2. Manny made the move C2
3. Gregory made the move C1
4. Manny made the move B3
5. Gregory made the move A3
6. Manny made the move C4
7. Gregory made the move C5
8. Manny made the move B1
9. Gregory made the move E5
10. Manny made the move A4
11. Gregory made the move A0
12. Manny made the move A2*/
        player1WinBoard.makeMove(2, 3);
        player1WinBoard.makeMove(2, 2);
        player1WinBoard.makeMove(2, 1);
        player1WinBoard.makeMove(1, 3);
        player1WinBoard.makeMove(0, 3);
        player1WinBoard.makeMove(2, 4);
        player1WinBoard.makeMove(2, 5);
        player1WinBoard.makeMove(1, 1);
        player1WinBoard.makeMove(4, 5);
        player1WinBoard.makeMove(0, 4);
        player1WinBoard.makeMove(0, 0);
        player1WinBoard.makeMove(0, 2);

        player2WinBoard.makeMove(3, 2);
        player2WinBoard.makeMove(2, 4);
        player2WinBoard.makeMove(5, 5);
        player2WinBoard.makeMove(3, 1);

        player1WinBoardClone = player1WinBoard.clone();
    }

    @Test
    void testToString() {
        assertEquals(" A B C D E F G H\n" +
                "----------------\n" +
                "| | | | | | | | |0\n" +
                "----------------\n" +
                "| | | | | | | | |1\n" +
                "----------------\n" +
                "| | | |.| | | | |2\n" +
                "----------------\n" +
                "| | |.|O|X| | | |3\n" +
                "----------------\n" +
                "| | | |X|O|.| | |4\n" +
                "----------------\n" +
                "| | | | |.| | | |5\n" +
                "----------------\n" +
                "| | | | | | | | |6\n" +
                "----------------\n" +
                "| | | | | | | | |7\n" +
                "----------------", defaultBoard.toString());
    }

    @Test
    void testGetCapturable() {
        ArrayList<int[]> twoFour = defaultBoard.getCapturable(3, 2);
        assertEquals(1, twoFour.size());
        assertTrue(twoFour.get(0)[0] == 3 && twoFour.get(0)[1] == 3);
    }

    @Test
    void testGetPossibleMoves() {
        assertEquals(4, defaultBoard.getPossibleMoves().size());
        assertEquals(0, player1WinBoard.getPossibleMoves().size());
        assertEquals(0, player1WinBoardClone.getPossibleMoves().size());
    }

    @Test
    void testIsPossibleMove() {
        assertFalse(defaultBoard.isPossibleMove(3, 3));
        assertFalse(defaultBoard.isPossibleMove(0, 0));
        assertTrue(defaultBoard.isPossibleMove(3, 2));
    }

    @Test
    void testGetWinner() {
        assertNull(defaultBoard.getWinner());
        assertEquals("Gregory", player1WinBoard.getWinner().toString());

        // Game technically not over here, but one player has a higher score right now.
        assertEquals("Winner", player2WinBoard.getWinner().toString());
    }

    @Test
    void testSwitchPlayers() {
        defaultBoard.switchPlayers();
        assertEquals("-1", defaultBoard.getCurrentPlayer().toString());

        assertEquals("Gregory", player1WinBoard.getCurrentPlayer().toString());
        player1WinBoard.switchPlayers();
        assertEquals("Manny", player1WinBoard.getCurrentPlayer().toString());

        assertEquals("Gregory", player1WinBoardClone.getCurrentPlayer().toString());
        player1WinBoardClone.switchPlayers();
        assertEquals("Manny", player1WinBoardClone.getCurrentPlayer().toString());
    }

    @Test
    void testGameOver() {
        assertFalse(defaultBoard.isGameOver());
        assertTrue(player1WinBoard.isGameOver());
        assertFalse(player2WinBoard.isGameOver());
    }

    @Test
    void testRepeatString() {
        assertEquals("hihihi", Board.repeatString("hi", 3));
        assertEquals("-", Board.repeatString("-", 1));
    }

    @Test
    void testCountObjects() {
        int[][] array = new int[8][8];

        for (int i = 0; i < 8; i++) {
            array[0][i] = 1;
        }

        for (int i = 0; i < 8; i++) {
            array[i][0] = 2;
        }

        array[5][5] = 999;

        assertEquals(7, Board.countObjects(array, 1));
        assertEquals(8, Board.countObjects(array,2));
        assertEquals(1, Board.countObjects(array, 999));
        assertEquals(48, Board.countObjects(array, 0));
    }

    @Test
    void testGetPiece() {
        assertEquals(0, defaultBoard.getPiece(0, 0));
    }

}
