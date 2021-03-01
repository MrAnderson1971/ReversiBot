package persistence;

import model.GameHistory;
import model.MoveHistory;
import model.Player;
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

    /*
    EFFECTS: loads GameHistory object from file.
     */
    private GameHistory loadObject(JSONObject jsonObject) {
        TreeMap<Integer, ArrayList<String>> menus = getDisplayMenu(jsonObject);
        HashMap<Integer, MoveHistory> games = getAllGames(jsonObject);
        GameHistory gh = new GameHistory();
        gh.set(menus, games);
        return gh;
    }

    /*
    EFFECTS: gets the displayMenu property of GameHistory
     */
    private TreeMap<Integer, ArrayList<String>> getDisplayMenu(JSONObject jsonObject) {
        JSONArray menus = jsonObject.getJSONArray("menus");
        TreeMap<Integer, ArrayList<String>> menu = new TreeMap<>();
        for (Object obj : menus) {
            JSONObject json = (JSONObject) obj;
            int index = json.getInt("id");
            String gameName = json.getString("name");
            String winner = json.getString("winner");

            menu.put(index, new ArrayList<String>(Arrays.asList(gameName, winner)));
        }

        return menu;
    }

    /*
    EFFECTS: gets the allGames property of GameHistory
     */
    private HashMap<Integer, MoveHistory> getAllGames(JSONObject jsonObject) {
        HashMap<Integer, MoveHistory> map = new HashMap<>();
        JSONArray games = jsonObject.getJSONArray("games");
        for (Object obj : games) {
            JSONObject json = (JSONObject) obj;
            int id = json.getInt("id");
            JSONObject game = json.getJSONObject("game");
            ArrayList<String> names = new ArrayList<>();
            for (Object j : game.getJSONArray("names")) {
                names.add((String) j);
            }
            ArrayList<int[]> moves = new ArrayList<>();
            for (Object j : game.getJSONArray("moves")) {
                JSONArray k = (JSONArray) j;
                moves.add(new int[] {(int) k.get(0), (int) k.get(1)});
            }
            MoveHistory m = new MoveHistory(new Player(1, names.get(0)), new Player(-1, names.get(1)));
            m.set(names, moves);
            map.put(id, m);
        }
        return map;
    }

    /*
    EFFECTS: returns contents of fileName as string
     */
    public String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }
}
