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

        assertEquals(7, Utils.countObjects(array, 1));
        assertEquals(8, Utils.countObjects(array,2));
        assertEquals(1, Utils.countObjects(array, 999));
        assertEquals(48, Utils.countObjects(array, 0));
    }
}
