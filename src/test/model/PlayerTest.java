package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {
    Player manos;
    Player thanny;

    @BeforeEach
    void runBefore() {
        manos = new Player(1, "Manos");
        thanny = new Player(-1, "Thanny");
    }

    @Test
    void testToString() {
        assertEquals("Manos", manos.toString());
        assertEquals("Thanny", thanny.toString());
    }

    @Test
    void testSetAgent() {
        assertNull(thanny.getAgent());
        Tree agent = new Tree(1, new Board(thanny, manos), thanny, manos);
        thanny.setAgent(agent);
        assertEquals(agent, thanny.getAgent());
    }

    @Test
    void testEquals() {
        assertNotEquals(thanny, manos);
        assertNotEquals(manos, "lol");

        Player thanos1 = new Player(-1, "Thanos");
        Player thanos2 = new Player(1, "Thanos");
        assertEquals(thanos1, thanny);
        assertNotEquals(thanos2, thanny);
    }
}
