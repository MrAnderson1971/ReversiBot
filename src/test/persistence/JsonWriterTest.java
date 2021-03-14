package persistence;

import model.GameHistory;
import model.MoveHistory;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        JsonWriter writer = new JsonWriter("./-data/my!@#$$%^^&&*(()?\\/.json");
        try {
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyObject() {
        String f = "./data/emptyWrite.json";
        JsonWriter writer = new JsonWriter(f);
        try {
            GameHistory gh = new GameHistory();
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader(f);
            assertEquals("{    \"games\": [],    \"menus\": []}",
                    reader.readFile(f));
        } catch (IOException e) {
            fail(e);
        }
    }

    @Test
    void testWriterGeneral() {
        GameHistory gh = new GameHistory();
        MoveHistory mh2 = new MoveHistory(new Player(1, "Player 1"), new Player(-1, "Player 2"));
        mh2.add(new int[]{1, 1}, "Player 1");
        mh2.add(new int[]{2, 2}, "Player 2");
        mh2.add(new int[]{3, 4}, "Player 1");
        mh2.add(new int[]{4, 4}, "Player 2");
        mh2.add(new int[]{5, 5}, "Player 1");
        mh2.add(new int[]{6, 6}, "Player 2");
        mh2.add(new int[]{7, 2}, "Player 1");
        mh2.add(new int[]{0, 0}, "Player 2");
        gh.add("Test", mh2, new Player(1, "Player 1"));

        String f = "./data/testSave2.json";

        try {
            JsonWriter writer = new JsonWriter(f);
            writer.open();
            writer.write(gh);
            writer.close();

            JsonReader reader = new JsonReader(f);
            GameHistory gh2 = (GameHistory) reader.load();
            assertEquals(1, gh2.getDisplayMenu().size());
            assertEquals("1. Player 1 made the move B1\n" +
                    "2. Player 2 made the move C2\n" +
                    "3. Player 1 made the move D4\n" +
                    "4. Player 2 made the move E4\n" +
                    "5. Player 1 made the move F5\n" +
                    "6. Player 2 made the move G6\n" +
                    "7. Player 1 made the move H2\n" +
                    "8. Player 2 made the move A0\n", gh2.get(0).toString());
        } catch (IOException e) {
            fail(e);
        }
    }
}
