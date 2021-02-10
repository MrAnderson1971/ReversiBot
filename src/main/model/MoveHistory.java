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

    /*
    EFFECTS: creates new MoveHistory object to store log of all moves made
     */
    public MoveHistory(Player player1, Player player2) {
        this.moves = new ArrayList<>();
        this.names = new ArrayList<>();

        this.player1 = player1;
        this.player2 = player2;
    }

    public ArrayList<int[]> getMoves() {
        return this.moves;
    }

    /*
    public ArrayList<String> getNames() {
        return this.names;
    }*/

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
}
