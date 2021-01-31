package ui;

import model.Game;
import model.Player;
import model.Tree;
import model.Utils;

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
            } while (!(0 < difficulty && difficulty <= 10));

            game = new Game(new Player(1, null, name),
                    new Player(-1, new Tree(difficulty), "Computer"));

        } else {
            game = new Game(new Player(1, null, name),
                    new Player(-1, null, "Player 2"));
        }
        runGame(game);
    }

    private void runGame(Game game) {
        while (!game.isOver()) {
            System.out.println(game.getBoard());
            System.out.println(game.getBoard().getCurrentPlayer().getName() + "'s turn.");
            game.update();
        }
        System.out.println(game.getBoard().getWinner().getName() + " won!");
    }

    /*
    REQUIRES: Scanner object that takes input
    EFFECTS: returns # of human players playing
     */
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

    /*
    REQUIRES: validMoves be list of valid moves.
    EFFECTS: takes user input and converts it into a square to place piece in
     */
    public static int[] getMove(ArrayList<int[]> validMoves) {
        Scanner scan = new Scanner(System.in);
        int[] move = new int[2];
        int x = -1;
        int y = -1;
        do {
            System.out.println("Enter a move (eg: A0):");
            String input = scan.nextLine().toUpperCase();
            if (input.length() < 2) {
                continue;
            }
            try {
                y = Integer.parseInt(String.valueOf(input.charAt(1)));
            } catch (NumberFormatException a) {
                continue;
            }

            x = letterToNumber(input.charAt(0));
            move = new int[]{x, y};
        } while (!(Utils.listContainsArray(validMoves, move)));

        return move;
    }

    /*
    EFFECTS: takes char A-H and converts it into number, -1 if not valid input
     */
    private static int letterToNumber(char c) {
        switch (c) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            default:
                return -1;
        }
    }

}
