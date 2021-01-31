package model;

import java.util.*;

public class MoveHistory {

    private Player player1;
    private Player player2;

    private ArrayList<int[]> moves;

    // EFFECTS: instantiate
    public MoveHistory(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.moves = new ArrayList<>();
    }

    /*
    REQUIRES: x, y be valid moves
    MODIFIES: this
    EFFECTS: adds move to record
     */
    public void add(int x, int y) {
        moves.add(new int[] {x, y});
    }

    @Override
    public String toString() {
        return "";
    }
}
