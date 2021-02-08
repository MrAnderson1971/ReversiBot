package model;

import java.util.*;

import static ui.Othello.listContainsArray;

/*
Represents the board for Othello game
 */
public class Board implements Cloneable {

    private int[][] board;

    private Player currentPlayer;

    private Player player1;
    private Player player2;

    public Board(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = this.player1;

        this.board = new int[8][8];
        this.board[3][3] = player2.getPiece();
        this.board[4][4] = player2.getPiece();
        this.board[3][4] = player1.getPiece();
        this.board[4][3] = player1.getPiece();
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /*
    REQUIRES: x, y < 7
    EFFECTS: true if spot at coordinates is 0

    public boolean spotIsEmpty(int x, int y) {
        return board[x][y] == 0;
    }/*

    /*
    REQUIRES: move be valid move
    MODIFIERS: this
    EFFECTS: places piece corresponding to currentPlayer at spot, then swaps players
     */
    public void makeMove(int x, int y) {
        board[x][y] = currentPlayer.getPiece();
        for (int[] coord : getCapturable(x, y)) {
            board[coord[0]][coord[1]] *= -1; // capture pieces by flipping them
        }
        switchPlayers();
    }

    /*
    EFFECTS: returns player that's opponent to currentPlayer
     */
    public Player getOpponent() {
        return (currentPlayer == player1) ? player2 : player1;
    }

    /*
    MODIFIERS: this
    EFFECTS: changes currentPlayer to opponent
     */
    public void switchPlayers() {
        currentPlayer = getOpponent();
    }

    /*
    EFFECTS: returns Player that won, null if tie.
     */
    public Player getWinner() {
        int player1count = countObjects(board, player1.getPiece());
        int player2count = countObjects(board, player2.getPiece());

        // player with most pieces at the end of the game wins
        if (player2count > player1count) {
            return player2;
        } else if (player1count > player2count) {
            return player1;
        }

        // tie if same amount of pieces
        return null;
    }

    /*
    EFFECTS: true if game over
     */
    public boolean isGameOver() {
        return getPossibleMoves().size() == 0; // game over if no possible moves left
    }

    /*
    EFFECTS: returns list of all possible moves for currentPlayer
     */
    public ArrayList<int[]> getPossibleMoves() {
        ArrayList<int[]> possibleMoves = new ArrayList<>();

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (isPossibleMove(x, y)) {
                    possibleMoves.add(new int[]{x, y});
                }
            }
        }

        return possibleMoves;
    }

    /*
    REQUIRES: 0 <= x, y < 8
    EFFECTS: true if valid move for currentPlayer
     */
    public boolean isPossibleMove(int x, int y) {
        return board[x][y] == 0 && getCapturable(x, y).size() > 0;
    }

    /*
    REQUIRES: 0 <= x, y < 8
    EFFECTS: returns list of pieces capturable if currentPlayer makes this move
     */
    public ArrayList<int[]> getCapturable(int x, int y) {
        ArrayList<int[]> capturable = new ArrayList<>();

        capturable.addAll(searchOneDirection(x + 1, y + 1, 1, 1)); // search +x +y direction
        capturable.addAll(searchOneDirection(x - 1, y + 1, -1, 1)); // search -x +y direction
        capturable.addAll(searchOneDirection(x - 1, y - 1, -1, -1)); // search -x -y direction
        capturable.addAll(searchOneDirection(x + 1, y - 1, 1, -1)); // search +x -y direction
        capturable.addAll(searchOneDirection(x + 1, y, 1, 0)); // search +x horizontal
        capturable.addAll(searchOneDirection(x - 1, y, -1, 0)); // search -x horizontal
        capturable.addAll(searchOneDirection(x, y + 1, 0, 1)); // search +y vertical
        capturable.addAll(searchOneDirection(x, y - 1, 0, -1)); // search -y vertical

        return capturable;
    }

    /*
    REQUIRES: dx, dy either +/- 1
    EFFECTS: searches one direction for capturable pieces
     */
    private ArrayList<int[]> searchOneDirection(int x, int y, int dx, int dy) {
        ArrayList<int[]> temp = new ArrayList<>();
        while (x < 8 && y < 8 && x >= 0 && y >= 0) {
            if (board[x][y] == currentPlayer.getPiece()) { // found own piece
                return temp;
            } else if (board[x][y] == currentPlayer.getPiece() * -1) { // found opponent piece
                temp.add(new int[]{x, y});
                x += dx;
                y += dy;
            } else { // found empty square
                return new ArrayList<>();
            }
        }
        return new ArrayList<>(); // if hit an edge
    }

    /*
    EFFECTS: returns printable string version of Board
     */
    public String toString() {
        ArrayList<int[]> possibleMoves = getPossibleMoves();

        StringBuilder s = new StringBuilder();
        s.append(" A B C D E F G H\n");
        s.append(repeatString("-", 16));
        int i = 0;
        for (int y = 0; y < 8; y++) {
            s.append("\n");
            for (int x = 0; x < 8; x++) {
                if (listContainsArray(possibleMoves, new int[]{x, y})) {
                    s.append("|.");
                } else if (board[x][y] == player1.getPiece()) {
                    s.append("|X");
                } else if (board[x][y] == player2.getPiece()) {
                    s.append("|O");
                } else { // empty space
                    s.append("| ");
                }
            }
            s.append("|").append(i++).append("\n").append(repeatString("-", 16));
        }
        return s.toString();
    }

    @Override
    public Board clone() {
        int[][] newArray = new int[8][8];
        for (int x = 0; x < 8; x++) {
            System.arraycopy(board[x], 0, newArray[x], 0, 8);
        }
        Board newBoard = new Board(player1, player2);
        newBoard.board = newArray;
        newBoard.currentPlayer = currentPlayer;
        return newBoard;
    }

    /*
    REQUIRES: times be positive
    EFFECTS: repeats str n times
     */
    public static String repeatString(String str, int times) {
        if (times == 1) {
            return str;
        }
        return str + repeatString(str, times - 1);
    }

    /*
        EFFECTS: counts the number of times i shows up in 2d array.
    */
    public static int countObjects(int[][] array, int i) {
        int count = 0;
        for (int[] x : array) {
            for (int y : x) {
                if (y == i) {
                    count++;
                }
            }
        }
        return count;
    }
}
