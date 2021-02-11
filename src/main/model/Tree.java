package model;

import java.util.*;

/*
Represents the MCTS search tree for the AI
 */
public class Tree {

    private Player player;
    private Player opponent;

    private int depth;

    private Node root;

    private Node leaf;

    private Node currentMove;

    /*
    REQUIRES: player should be whoever is making the next move in board
        opponent should be player's opponent
    EFFECTS: creates new MCTS search tree
     */
    public Tree(int depth, Board board, Player player, Player opponent) {
        this.depth = depth;
        this.player = player;
        this.opponent = opponent;
        this.root = new Node(board, null, player);
        this.currentMove = this.root;
    }

    public Node getCurrentMove() {
        return currentMove;
    }

    /*
    MODIFIES: this
    EFFECTS: "trains" the AI using Monte Carlo Tree Search algorithm
        Step 1: find a leaf node
        Step 2: unless leaf node found has a game over, extend the leaf node
            by all possible next moves
        Step 3: Simulate random playout starting from the position from the previous step
        Step 4: Update all parent nodes from the leaf to the root about who won the random playout.
    */
    public void train() {
        double searchTimes = Math.pow(2, depth);
        for (int i = 0; i < searchTimes; i++) {
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
        by traversing the tree and selecting the one with highest UCT's
     */
    private void selection() {
        Node current = currentMove;
        while (!current.isLeaf()) {
            current = current.bestNode();
        }
        leaf = current;
    }

    /*
    REQUIRES: leaf should be a leaf node (without children)
    MODIFIES: this, leaf
    EFFECTS: add every possible next move as child to leaf
        if one of the nodes wins the game, return the winner
        else return null if no winner yet
     */
    private Player expansion() {
        Board b = leaf.getBoard();
        ArrayList<Node> children = new ArrayList<>();
        ArrayList<int[]> possibleMoves = b.getPossibleMoves();
        for (int[] move : possibleMoves) {
            Board copyOfBoard = b.clone();
            copyOfBoard.makeMove(move[0], move[1]);
            Node newNode = new Node(copyOfBoard, move, copyOfBoard.getCurrentPlayer());
            children.add(newNode);
        }
        if (children.size() == 0) { // no possible moves found; game over
            return leaf.getBoard().getWinner();
        }
        leaf.addChildren(children);
        leaf = leaf.randomChild();
        return null;
    }

    /*
    EFFECTS: returns which player won as a result of making moves at random
     */
    private Player simulation() {
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
        as well as how many times it has been visited (ie: # of games played)
     */
    private void backpropagation(Player winner) {
        Node current = leaf;
        while (current.getParent() != null) {
            current.addGame();
            if (winner == current.getPlayer()) {
                // this node won
                current.setWins(current.getWins() + 1);
            } else if (winner != null) {
                // this node lost
                current.setWins(current.getWins() - 1);
            }
            current = current.getParent();
        }
    }

    /*
    MODIFIES: this
    EFFECTS: returns the most promising move, then updates the tree
        most promising move = one that maximizes chances of opponent losing
     */
    public int[] bestMove() {
        Node bestNode = currentMove.bestMove();
        int[] bestMove = bestNode.getMove();
        currentMove = bestNode;
        return bestMove;
    }

    /*
    REQUIRES: move is a valid move
    MODIFIES: this
    EFFECTS: updates tree so that current move is the move that was just made
        if no corresponding node found, throws an exception
     */
    public void updateMove(int[] move) {
        for (Node child : currentMove.getChildren()) {
            if (Arrays.equals(child.getMove(), move)) {
                currentMove = child;
                return;
            }
        }
        throw new IllegalArgumentException("Node not found.");
    }
}
