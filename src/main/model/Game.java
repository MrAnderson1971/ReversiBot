package model;

import ui.Othello;

import java.util.Arrays;

/*
Represents one game
 */
public class Game {

    private Player player1;
    private Player player2;

    private Player currentPlayer;

    private boolean over;

    private Board board;

    private MoveHistory moveHistory;

    /*
    REQUIRES: player1, player2 opponents of each other with opposite pieces
    EFFECTS: creates new Game object
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = this.player1;

        this.over = false;
        this.board = new Board(player1, player2);
        this.moveHistory = new MoveHistory(player1, player2);
    }

    public MoveHistory getMoveHistory() {
        return moveHistory;
    }

    /*
    MODIFIES: this
    EFFECTS: runs the game
     */
    /*
    public void update() {
        try {
            if (board.getCurrentPlayer().getAgent() == null) { // get player move
                int[] move = Othello.getMove(board.getPossibleMoves());

                moveHistory.add(move, board.getCurrentPlayer().toString());
                board.makeMove(move[0], move[1]);

                if (board.getCurrentPlayer().getAgent() != null) {
                    // If opponent is computer, update computer AI tree.
                    //board.getCurrentPlayer().getAgent().train();
                    board.getCurrentPlayer().getAgent().updateMove(move);
                    board.getCurrentPlayer().getAgent().train();
                }

            } else { // get computer move
                //board.getCurrentPlayer().getAgent().train();
                int[] move = board.getCurrentPlayer().getAgent().bestMove();
                board.getCurrentPlayer().getAgent().train();
                moveHistory.add(move, board.getCurrentPlayer().toString());
                board.makeMove(move[0], move[1]);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println(moveHistory);
            e.printStackTrace();
            throw e;
        }
        if (board.isGameOver()) {
            over = true;
        }
    }*/

    public Board getBoard() {
        return board;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }
}
