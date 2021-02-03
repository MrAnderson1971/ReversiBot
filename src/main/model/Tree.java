package model;

import java.util.*;

public class Tree {

    private Player player;
    private Player opponent;

    private int depth;

    private Node root;

    private Node leaf;

    public Tree(int depth, Board board, Player player, Player opponent) {
        this.depth = depth;
        this.player = player;
        this.opponent = opponent;
        this.root = new Node(board, null, player);
    }

    /*
    MODIFIES: this
    EFFECTS: trains the AI
     */
    public void train() {
        for (int i = 0; i < depth; i++) {
            selection();
            Player winner = expansion();
            if (winner == null) {
                winner = simulation();
            }
            backpropagation(winner);
        }
    }

    /*
    MODIFIES: this
    EFFECTS: returns best leaf node found in tree
     */
    public void selection() {
        Node current = root;
        while (!current.isLeaf()) {
            current = current.bestNode();
        }
        leaf = current;
    }

    /*
    REQUIRES: leaf should be a leaf node (without children)
    MODIFIES: this, leaf
    EFFECTS: add every possible next move as child to leaf
     */
    public Player expansion() {
        Board b = leaf.getBoard();
        ArrayList<Node> children = new ArrayList<>();
        Node winnerNode = null;
        for (int[] move : b.getPossibleMoves()) {
            Board copyOfBoard = b.clone();
            copyOfBoard.makeMove(move[0], move[1]);
            Node newNode = new Node(copyOfBoard, move, copyOfBoard.getCurrentPlayer());
            if (copyOfBoard.getWinner() != null) {
                winnerNode = newNode;
            }
            children.add(newNode);
        }
        leaf.addChildren(children);
        if (winnerNode != null) {
            leaf = winnerNode;
            return leaf.getBoard().getWinner();
        }
        leaf = leaf.randomChild();
        return null;
    }

    /*
    EFFECTS: returns which player won as a result of making moves at random
     */
    public Player simulation() {
        Board board = leaf.getBoard().clone();
        while (!board.isGameOver()) {
            ArrayList<int[]> moves = board.getPossibleMoves();
            int[] randomMove = moves.get(new Random().nextInt(moves.size()));
            board.makeMove(randomMove[0], randomMove[1]);
        }
        return board.getWinner();
    }

    /*
    MODIFIES: this
    EFFECTS: updates all parent/grandparent etc. nodes of leaf with information on how many wins
     */
    public void backpropagation(Player winner) {
        Node current = leaf;
        while (current.getParent() != null) {
            if (winner == current.getPlayer()) {
                current.setWins(current.getWins() + 1);
            } else if (winner != null) {
                current.setWins(current.getWins() - 1);
            }
            current = current.getParent();
        }
    }
}
