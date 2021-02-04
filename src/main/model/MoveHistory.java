package model;

import java.util.*;

public class MoveHistory {

    private ArrayList<int[]> moves;
    private ArrayList<String> names;

    // EFFECTS: instantiate
    public MoveHistory() {
        this.moves = new ArrayList<>();
        this.names = new ArrayList<>();
    }

    public String getLastMove() {
        return names.get(names.size() - 1) + " made the move " + moveToString(moves.get(moves.size() - 1));
    }

    /*
    REQUIRES: be a valid move
    MODIFIES: this
    EFFECTS: adds move to record
     */
    public void add(int[] move, String name) {
        moves.add(move);
        names.add(name);
    }

    /*
    EFFECTS: takes integer returns uppercase letter corresponding to it.
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

    public String moveToString(int[] move) {
        String s = String.valueOf(intToChar(move[0]));
        return s + Integer.toString(move[1]);
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
}
