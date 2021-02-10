package model;

import java.util.*;

/*
Keeps tracks of all games played so far
 */
public class GameHistory {

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
        displayMenu.put(currentNumber, new ArrayList<>(Arrays.asList(name, winner.toString())));
        allGames.put(currentNumber, game);
        currentNumber++;
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
}
