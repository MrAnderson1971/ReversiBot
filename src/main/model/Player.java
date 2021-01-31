package model;

/*
Represents one player for Othello game.
 */
public class Player {

    private int number;
    private Tree agent;
    private String name;

    // integer that represents this player's piece on the board
    // should be 1 or -1
    private int piece;

    /*
    EFFECTS: instantiates a Player
     */
    public Player(int number, Tree agent, String name) {
        this.number = number;
        this.agent = agent;
        this.name = name;
    }

    public int getPiece() {
        return piece;
    }

    /*
    EFFECTS: true if other is equal to this.
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Player)) {
            return false;
        }
        Player p = (Player) other;
        return this.number == p.number;
    }
}
