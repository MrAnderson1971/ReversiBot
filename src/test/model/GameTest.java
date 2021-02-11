package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    Game g;

    Player player1;
    Player player2;

    Board b;

    @BeforeEach
    void runBefore() {
        player1 = new Player(1, "1");
        player2 = new Player(-1, "-1");

        g = new Game(player1, player2);
        b = new Board(player1, player2);
    }

    @Test
    void testGetMoveHistory() {
        assertEquals(0, g.getMoveHistory().getMoves().size());
    }

    @Test
    void testIsOver() {
        assertFalse(g.isOver());
        g.setOver(true);
        assertTrue(g.isOver());
    }

    @Test
    void testGetBoard() {
        assertEquals("1", g.getBoard().getCurrentPlayer().toString());
    }

}
