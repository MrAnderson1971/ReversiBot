package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writeable;

import java.util.*;

/*
Keeps track of all moves made in a game so far.
 */
public class MoveHistory implements Writeable {

    private ArrayList<int[]> moves;
    private ArrayList<String> names;

    private Player player1;
    private Player player2;

    /*
    REQUIRES: player1, player2 opponents of each other with opposite pieces
    EFFECTS: creates new MoveHistory object to store log of all moves made
     */
    public MoveHistory(Player player1, Player player2) {
        this.moves = new ArrayList<>();
        this.names = new ArrayList<>();

        this.player1 = player1.clone();
        this.player2 = player2.clone();
    }

    public ArrayList<int[]> getMoves() {
        return this.moves;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void set(ArrayList<String> names, ArrayList<int[]> moves) {
        this.names = names;
        this.moves = moves;
    }

    /*
    EFFECTS: returns string representation of last move made.
     */
    public String getLastMove() {
        return names.get(names.size() - 1) + " made the move " + moveToString(moves.get(moves.size() - 1)) + ".";
    }

    /*
    REQUIRES: move be a valid move
        name be the name of either Player 1 or Player 2
    MODIFIES: this
    EFFECTS: adds move to record
     */
    public void add(int[] move, String name) {
        moves.add(move);
        names.add(name);
    }

    /*
    REQUIRES: 0 <= i <= 7
    EFFECTS: takes integer returns uppercase letter corresponding to it.
        0 => A, 1 => B, etc.
     */
    private char intToChar(int i) {
        switch (i) {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
            default:
                return ' ';
        }
    }

    /*
    REQUIRES: move is a valid move
    EFFECTS: returns a string representation of move
     */
    public String moveToString(int[] move) {
        String s = String.valueOf(intToChar(move[0]));
        return s + move[1];
    }

    /*
    EFFECTS: returns string representation of this.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < moves.size(); i++) {
            s.append(i + 1).append(". ").append(names.get(i));
            s.append(" made the move ").append(moveToString(moves.get(i)));
            s.append("\n");
        }
        return s.toString();
    }

    /*
    REQUIRES: moveNumber is a >= 0 and less than the number of moves made in the game
    EFFECTS: returns moveNumber-th move as a string
     */
    public String getOneLine(int moveNumber) {
        StringBuilder s = new StringBuilder();
        s.append(moveNumber + 1).append(". ").append(names.get(moveNumber));
        s.append(" made the move ").append(moveToString(moves.get(moveNumber))).append(".");
        return s.toString();
    }

    /*
    EFFECTS: returns JSON representation of this
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        JSONArray moveArray = new JSONArray();
        JSONArray nameArray = new JSONArray();

        for (int[] move : moves) {
            moveArray.put(move);
        }

        for (String name : names) {
            nameArray.put(name);
        }

        json.put("moves", moveArray);
        json.put("names", nameArray);
        return json;
    }
}
