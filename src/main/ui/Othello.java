package ui;

import model.Game;
import model.Player;
import model.Tree;

import java.util.*;

/*
Othello board game.
 */
public class Othello {

    Game game;

    // EFFECTS: runs the game
    public Othello() {
        start();
    }

    private void start() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Hello human. Please enter your name:");
        String name = scan.nextLine();

        int numPlayers = getNumPlayers(scan);

        if (numPlayers == 1) {

            int difficulty = -1;
            do {
                System.out.println("Select difficulty: (1 - 10)");
                try {
                    difficulty = Integer.parseInt(scan.nextLine());
                } catch (NumberFormatException e) {
                    continue;
                }
            } while (! (0 < difficulty && difficulty <= 10));

            game = new Game(new Player(1, null, name),
                    new Player(-1, new Tree(difficulty), "Computer"));

        } else {
            game = new Game(new Player(1, null, name),
                    new Player(-1, null, "Player 2"));
        }
        System.out.println(game.getBoard());
    }

    private int getNumPlayers(Scanner scan) {
        int numPlayers = -1;
        do {
            System.out.println("One player or two players?");
            try {
                numPlayers = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                continue;
            }
        } while (numPlayers != 1 && numPlayers != 2);
        return numPlayers;
    }

}
