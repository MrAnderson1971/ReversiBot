package model;

import java.util.*;

public class GameHistory {

    // Maps a number to game name for display in menus
    // TreeMap because I need them to print in order
    private TreeMap<Integer, String> displayMenu;

    // Maps same number to moveHistory for fetching
    private HashMap<Integer, MoveHistory> allGames;

    private int currentNumber;

    public GameHistory() {
        displayMenu = new TreeMap<>();
        allGames = new HashMap<>();
        currentNumber = 1;
    }

    /*
    MODIFIES: this
    EFFECTS: adds a game to the record, increments id for next game
     */
    public void add(String name, MoveHistory game) {
        displayMenu.put(currentNumber, name);
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
    EFFECTS: returns string representation of this to display as menu
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i : displayMenu.keySet()) {
            s.append(i).append(". ").append(displayMenu.get(i)).append("\n");
        }
        return s.toString();
    }
}
