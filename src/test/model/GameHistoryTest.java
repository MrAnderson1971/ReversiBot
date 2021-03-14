package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class GameHistoryTest {

    GameHistory empty;
    GameHistory gh;

    MoveHistory mh1 = new MoveHistory(new Player(1, "Manos"),
            new Player(-1, "Thanny"));
    MoveHistory mh2 = new MoveHistory(new Player(1, "Player 1"),
            new Player(-1, "Player2"));

    @BeforeEach
    void runBefore() {
        empty = new GameHistory();
        gh = new GameHistory();

        gh.add("Infinity War", mh1, new Player(1, "Manos"));
    }

    @Test
    void testIsEmpty() {
        assertTrue(empty.isEmpty());
        assertFalse(gh.isEmpty());
    }

    @Test
    void testGet() {
        assertEquals(mh1, gh.get(0));
    }

    @Test
    void testAdd() {
        assertEquals(1, gh.getDisplayMenu().size());
        gh.add("test", mh2, new Player(-1, null));
        assertEquals(2, gh.getDisplayMenu().size());
    }

    @Test
    void testDelete() {
        assertEquals(1, gh.getDisplayMenu().size());
        assertTrue(gh.delete(0));
        assertEquals(0, gh.getDisplayMenu().size());
        gh.add("test", mh2, new Player(-1, null));
        assertEquals(1, gh.getDisplayMenu().size());
        assertFalse(gh.delete(1));
        assertFalse(gh.delete(2));
        //assertEquals(0, gh.getDisplayMenu().size());
    }

    @Test
    void testToString() {
        assertEquals("", empty.toString());
        assertEquals("0. Infinity War | Manos won\n", gh.toString());
        gh.add("test", mh2, new Player(-1, null));
        assertEquals("0. Infinity War | Manos won\n1. test | null won\n", gh.toString());
    }

    @Test
    void testMax() {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 6, 2, 4, 6, 3, 7, 7, 333, 6));
        assertEquals(333, GameHistory.max(a));
    }

    @Test
    void testOptionToString() {
        assertEquals("Infinity War | Manos", gh.optionToString(0));
    }
}
