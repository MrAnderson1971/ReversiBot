package persistence;

import model.GameHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    String expected1;
    String expected2;

    @BeforeEach
    void runBefore() {
        expected1 = read("./data/expected1.txt");
        expected2 = read("./data/expected2.txt");
    }

    String read(String f) {
        try {
            StringBuilder s = new StringBuilder();
            Scanner scan = new Scanner(new File(f));
            while (scan.hasNextLine()) {
                s.append(scan.nextLine()).append("\n");
            }
            return s.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GameHistory wr = (GameHistory) reader.load();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyObject() {
        JsonReader reader = new JsonReader("./data/emptySave.json");
        try {
            GameHistory gh = (GameHistory) reader.load();
            assertEquals(0, gh.getDisplayMenu().size());
        } catch (IOException e) {
            fail("Error in file.");
        }
    }

    @Test
    void testReaderGeneral() {
        String f = "./data/testSave.json";
        try {
            JsonReader reader = new JsonReader(f);
            GameHistory gh = (GameHistory) reader.load();
            assertEquals(2, gh.getDisplayMenu().size());
            assertEquals("0. Wimpy War | Manos won\n" +
                    "1. Detroit: Become Skynet | RK800 won\n", gh.toString());
            assertEquals(expected1, gh.get(0).toString());
            assertEquals(expected2, gh.get(1).toString());
        } catch (IOException e) {
            fail(e);
        }
    }
}
