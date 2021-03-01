package persistence;

import jdk.nashorn.internal.ir.debug.JSONWriter;
import model.GameHistory;
import model.MoveHistory;
import model.Player;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
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
        } catch (FileNotFoundException e) {
            fail("nope");
        } catch (IOException e) {
            fail("also nope");
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
            String out = reader.readFile(f);
            assertEquals("{    \"games\": [{       " +
                    " \"game\": {            \"names\": [             " +
                    "   \"Player 1\",             " +
                    "   \"Player 2\",         " +
                    "       \"Player 1\",          " +
                    "      \"Player 2\",          " +
                    "      \"Player 1\",          " +
                    "      \"Player 2\",       " +
                    "         \"Player 1\",           " +
                    "     \"Player 2\"      " +
                    "      ],            \"moves\": [     " +
                    "           [              " +
                    "      1,               " +
                    "     1                " +
                    "],                [       " +
                    "             2,          " +
                    "          2                ],         " +
                    "       [                    3,            " +
                    "        4                ],             " +
                    "   [                    4,                " +
                    "    4                ],                [    " +
                    "                5,                    5                ],    " +
                    "            [                    6,                    6         " +
                    "       ],                [     " +
                    "               7,             " +
                    "       2                ],        " +
                    "        [                    0,   " +
                    "                 0           " +
                    "     ]            ]        },  " +
                    "      \"id\": 1    }],    \"menus\": [{    " +
                    "    \"winner\": \"Player 1\",        \"name\": \"Test\",    " +
                    "    \"id\": 1    }]}", out);
        } catch (IOException e) {
            fail(e);
        }
    }
}
