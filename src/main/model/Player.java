package model;

/*
Represents one player for Othello game.
 */
public class Player {

    private Tree agent;
    private String name;

    // integer that represents this player's piece on the board
    // should be 1 or -1
    private int piece;

    /*
    EFFECTS: instantiates a Player
     */
    public Player(int piece, String name) {
        this.piece = piece;
        //this.agent = agent;
        this.name = name;
    }

    public int getPiece() {
        return piece;
    }

    public Tree getAgent() {
        return agent;
    }

    @Override
    public String toString() {
        return name;
    }

    /*
    REQUIRES: agent is not null
    MODIFIES: this, agent
    EFFECTS: trains the AI
     */
    public void setAgent(Tree agent) {
        this.agent = agent;
        this.agent.train();
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
        return this.piece == p.piece;
    }
}
