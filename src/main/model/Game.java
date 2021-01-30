package model;

/*
Represents one game
 */
public class Game {

    private Player player1;
    private Player player2;

    private Player currentPlayer;

    private boolean over;

    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;

        this.currentPlayer = this.player1;

        this.over = false;
    }
}