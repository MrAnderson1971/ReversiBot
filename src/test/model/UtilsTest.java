package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class UtilsTest {

    @BeforeEach
    void runBefore() {

    }

    @Test
    void testRepeatString() {
        assertEquals("hihihi", Utils.repeatString("hi", 3));
        assertEquals("-", Utils.repeatString("-", 1));

    }

    @Test
    void testListContainsArray() {
        ArrayList<int[]> one = new ArrayList<>();

        one.add(new int[] {1, 2});
        one.add(new int[] {3, 4});
        one.add(new int[] {999, 9999});

        assertTrue(Utils.listContainsArray(one, new int[]{3, 4}));
        assertFalse(Utils.listContainsArray(one, new int[] {999, 9998}));
    }
}
