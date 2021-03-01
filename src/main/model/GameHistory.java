package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.*;

/*
Keeps tracks of all games played so far
 */
public class GameHistory implements Writeable {

    // Maps a number to game name for display in menus
    // TreeMap because I need them to print in order
    private TreeMap<Integer, ArrayList<String>> displayMenu;

    // Maps same number to moveHistory for fetching
    private HashMap<Integer, MoveHistory> allGames;

    private int currentNumber;

    /*
    EFFECTS: creates new GameHistory object to store replays in
    Only needs to be instantiated once.
     */
    public GameHistory() {
        displayMenu = new TreeMap<>();
        allGames = new HashMap<>();
        currentNumber = 1;
    }

    /*
    MODIFIES: this
    EFFECTS: adds a game to the record, increments id for next game
     */
    public void add(String name, MoveHistory game, Player winner) {
        displayMenu.put(currentNumber, new ArrayList<>(Arrays.asList(name, "" + winner)));
        allGames.put(currentNumber, game);
        currentNumber++;
    }

    public void set(TreeMap<Integer, ArrayList<String>> displayMenu, HashMap<Integer, MoveHistory> allGames) {
        this.displayMenu = displayMenu;
        this.allGames = allGames;
        currentNumber = max(this.displayMenu.keySet()) + 1;
    }

    /*
    REQUIRES: i be valid key of allGames
    EFFECTS: returns value (game) corresponding to i
     */
    public MoveHistory get(int i) {
        return allGames.get(i);
    }

    /*
    EFFECTS: true if no games in record
     */
    public boolean isEmpty() {
        return displayMenu.isEmpty();
    }

    /*
    EFFECTS: returns a list of all keys in displayMenu
     */
    public Set<Integer> getKeyset() {
        return displayMenu.keySet();
    }

    /*
    MODIFIES: this
    EFFECTS: deletes selection from both the menu and the record.
    Returns true if successful, else returns false.
     */
    public boolean delete(int selection) {
        if (displayMenu.containsKey(selection)) {
            displayMenu.remove(selection);
            allGames.remove(selection);
            return true;
        }
        return false;
    }

    /*
    EFFECTS: returns string representation of this to display as menu
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : displayMenu.keySet()) {
            s.append(i).append(". ").append(displayMenu.get(i).get(0));
            s.append(" | ").append(displayMenu.get(i).get(1)).append(" won").append("\n");
        }
        return s.toString();
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray gamesArray = new JSONArray();
        JSONArray menusArray = new JSONArray();

        for (int mh : allGames.keySet()) {
            JSONObject obj = new JSONObject();
            obj.put("id", mh);
            obj.put("game", allGames.get(mh).toJson());
            gamesArray.put(obj);
        }

        for (int mh : displayMenu.keySet()) {
            JSONArray a = new JSONArray();
            JSONObject obj = new JSONObject();
            obj.put("id", mh);
            obj.put("name", displayMenu.get(mh).get(0));
            obj.put("winner", displayMenu.get(mh).get(1));
            a.put(obj);
            menusArray.put(obj);
        }

        json.put("games", gamesArray);
        json.put("menus", menusArray);
        return json;
    }

    /*
    EFFECTS: returns the maximum value of a collection of integers
     */
    public static int max(Collection<Integer> c) {
        int max = Integer.MIN_VALUE;
        for (int i : c) {
            if (i > max) {
                max = i;
            }
        }
        return max;
    }

}
