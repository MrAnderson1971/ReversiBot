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
    EFFECTS: returns string representation of this.
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < moves.size(); i++) {
            s.append(i).append(". ").append(names.get(i));
            s.append(" made the move ").append(Arrays.toString(moves.get(i)));
        }
        return s.toString();
    }
}
