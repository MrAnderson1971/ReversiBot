package model;

import java.util.*;

/*
Keeps track of all moves made in a game so far.
 */
public class MoveHistory {

    private ArrayList<int[]> moves;
    private ArrayList<String> names;

    private Player player1;
    private Player player2;

    // EFFECTS: instantiate
    public MoveHistory(Player player1, Player player2) {
        this.moves = new ArrayList<>();
        this.names = new ArrayList<>();

        this.player1 = player1;
        this.player2 = player2;
    }

    public ArrayList<int[]> getMoves() {
        return this.moves;
    }

    public ArrayList<String> getNames() {
        return this.names;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    /*
    EFFECTS: returns string representation of last move made.
     */
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
