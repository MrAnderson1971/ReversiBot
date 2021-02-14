package model;

import java.util.*;

/*
One node on the MCTS search tree
 */
public class Node {

    private Board board;
    private int[] move;
    private Player player;

    private double wins;
    private double games;

    private ArrayList<Node> children;
    private Node parent;

    /*
    REQUIRES: move is a valid move
        board is a valid board and results from making move to board of parent node
        player either player 1 or player 2, should alternate between parent/children nodes
    EFFECTS: instantiates new Node for MCTS
     */
    public Node(Board board, int[] move, Player player) {
        this.board = board;
        this.move = move;
        this.player = player;

        this.wins = 0;
        this.games = 1;
        this.children = new ArrayList<>();
    }

    public Board getBoard() {
        return this.board;
    }

    public Node getParent() {
        return this.parent;
    }

    public Player getPlayer() {
        return this.player;
    }

    public double getWins() {
        return this.wins;
    }

    public int[] getMove() {
        return this.move;
    }

    public ArrayList<Node> getChildren() {
        return this.children;
    }

    public void setWins(double wins) {
        this.wins = wins;
    }

    public double getGames() {
        return this.games;
    }

    /*
    MODIFIES: this
    EFFECTS: increase the number of games explored by 1
     */
    public void addGame() {
        games++;
    }

    /*
    EFFECTS: returns a value useful for selecting nodes in the tree search according to a formula.
        The formula is called UCT (Upper Confidence Bound applied to trees)
        uct = wins / games + sqrt((2ln parentWins) / games)
     */
    public double uct() {
        return wins / games + Math.sqrt((2 * Math.log(parent.games)) / games);
    }

    /*
    REQUIRES: this.children is not empty
    EFFECTS: returns child node with highest UCT value
     */
    public Node bestNode() {
        double maxValue = children.get(0).uct();
        Node maxNode = children.get(0);
        for (Node n : children) {
            if (n.uct() > maxValue) {
                maxValue = n.uct();
                maxNode = n;
            }
        }
        return maxNode;
    }

    /*
    EFFECTS: true if this node is a leaf node
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /*
    MODIFIES: this
    EFFECTS: adds children to this node
     */
    public void addChildren(ArrayList<Node> children) {
        for (Node child : children) {
            this.children.add(child);
            child.parent = this;
            child.parent.board = null; // save memory
        }
    }

    /*
    EFFECTS: returns a random child node
     */
    public Node randomChild() {
        return children.get(new Random().nextInt(children.size()));
    }

    /*
    EFFECTS: returns node that has the highest win rate.
     */
    public Node bestMove() {
        Node bestChild = children.get(0);
        double bestWinRate = bestChild.wins / bestChild.games;
        for (Node child : children) {
            if (child.wins / child.games < bestWinRate) {
                // Maximize chances of this winning by maximizing chances of opponent losing
                bestWinRate = child.wins / child.games;
                bestChild = child;
            }
        }

        return bestChild;
    }
}
