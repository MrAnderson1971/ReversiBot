package persistence;

import model.GameHistory;
import model.MoveHistory;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class JsonReader {

    private String fileName;

    /*
    REQUIRES: fileName exists and is readable
    EFFECTS: creates JsonReader to read fileName
     */
    public JsonReader(String fileName) {
        this.fileName = fileName;
    }

    /* EFFECTS: reads workroom from file and returns it;
     throws IOException if an error occurs reading data from file */
    public Writeable load() throws IOException {
        String jsonData = readFile(fileName);
        JSONObject jsonObject = new JSONObject(jsonData);
        return loadObject(jsonObject);
    }

    public GameHistory loadObject(JSONObject jsonObject) {
        TreeMap<Integer, ArrayList<String>> menus = getMenus(jsonObject);
        return null;
    }

    public TreeMap<Integer, ArrayList<String>> getMenus(JSONObject jsonObject) {
        JSONArray menus = jsonObject.getJSONArray("menus");
        TreeMap<Integer, ArrayList<String>> menu = new TreeMap<>();
        for (Object obj : menus) {
            JSONObject json = (JSONObject) obj;
            int index = Integer.parseInt(json.getString("id"));
            String gameName = json.getString("name");
            String winner = json.getString("winner");

            menu.put(index, new ArrayList<String>(Arrays.asList(gameName, winner)));
        }

        return menu;
    }

    public MoveHistory getMoveHistory(JSONObject jsonObject) {
        MoveHistory mh = new MoveHistory(null, null);
        return mh;
    }

    /*
    EFFECTS: returns contents of fileName as string
     */
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }
}
