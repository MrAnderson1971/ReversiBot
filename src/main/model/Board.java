package model;

import java.util.*;

/*
Represents the board for Othello game
 */
public class Board {

    private int[][] board;

    private Player currentPlayer;

    private Player player1;
    private Player player2;

    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = this.player1;

        this.board = new int[8][8];
    }

    /*
    REQUIRES: x, y < 7
    EFFECTS: true if spot at coordinates is 0
     */
    public boolean spotIsEmpty(int x, int y) {
        return board[x][y] == 0;
    }

    /*
    REQUIRES: move be valid move
    MODIFIERS: this
    EFFECTS: places piece corresponding to currentPlayer at spot
     */
    public void makeMove(int x, int y) {
        board[x][y] = currentPlayer.getPiece();
    }

    /*
    EFFECTS: returns list of all possible moves for currentPlayer
     */
    public ArrayList<int[]> getPossibleMoves() {
        return null;
    }

    /*
    REQUIRES: 0 <= x, y < 8
    EFFECTS: true if valid move for currentPlayer
     */
    public boolean isPossibleMove(int x, int y) {
        return false;
    }
}
