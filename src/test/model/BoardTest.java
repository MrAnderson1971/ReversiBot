package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    Board defaultBoard;

    @BeforeEach
    void runBefore() {
        defaultBoard = new Board(new Player(1, null, "1"), new Player(-1, null, "-1"));
    }

    @Test
    void testToString() {
        assertEquals("-----------------\n" +
                "| | | | | | | | |\n" +
                "-----------------\n" +
                "| | | | | | | | |\n" +
                "-----------------\n" +
                "| | | | | | | | |\n" +
                "-----------------\n" +
                "| | | |X|O| | | |\n" +
                "-----------------\n" +
                "| | | |O|X| | | |\n" +
                "-----------------\n" +
                "| | | | | | | | |\n" +
                "-----------------\n" +
                "| | | | | | | | |\n" +
                "-----------------\n" +
                "| | | | | | | | |\n" +
                "-----------------", defaultBoard.toString());
    }


}