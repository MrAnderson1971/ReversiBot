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
        assertNull(empty.get(0));
        assertEquals(mh1, gh.get(1));
    }

    @Test
    void testAdd() {
        assertEquals(1, gh.getKeyset().size());
        gh.add("test", mh2, new Player(-1, null));
        assertEquals(2, gh.getKeyset().size());
    }

    @Test
    void testDelete() {
        assertEquals(1, gh.getKeyset().size());
        assertTrue(gh.delete(1));
        assertEquals(0, gh.getKeyset().size());
        gh.add("test", mh2, new Player(-1, null));
        assertEquals(1, gh.getKeyset().size());
        assertFalse(gh.getKeyset().contains(1));
        assertFalse(gh.delete(1));
        assertTrue(gh.delete(2));
        assertEquals(0, gh.getKeyset().size());
    }

    @Test
    void testToString() {
        assertEquals("", empty.toString());
        assertEquals("1. Infinity War | Manos won\n", gh.toString());
        gh.add("test", mh2, new Player(-1, null));
        assertEquals("1. Infinity War | Manos won\n2. test | null won\n", gh.toString());
    }

    @Test
    void testMax() {
        ArrayList<Integer> a = new ArrayList<>(Arrays.asList(1, 6, 2, 4, 6, 3, 7, 7, 333, 6));
        assertEquals(333, GameHistory.max(a));
    }
}
