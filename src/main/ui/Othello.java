package ui;

import model.*;

import java.util.*;

/*
Othello board game.
 */
public class Othello {

    Game game;

    private GameHistory gameHistory;
    private Scanner scan = new Scanner(System.in);

    // EFFECTS: runs the game
    public Othello() {
        gameHistory = new GameHistory();
        while (true) {
            menu();
            start();
        }
    }

    // EFFECTS: Displays main menu
    private void menu() {
        String selection = "";

        do {
            System.out.println("Press p to play, or v to view past games.");
            selection = scan.nextLine().toLowerCase();
        } while (!selection.equals("p") && !selection.equals("v"));
        if (selection.equals("p")) {
            return;
        }
        viewRecord();
    }

    /*
    EFFECTS: Displays record of past games
     */
    private void viewRecord() {
        if (gameHistory.isEmpty()) {
            System.out.println("No games played yet.");
            return;
        }

        while (true) {
            System.out.println(gameHistory);
            int selection = -1;
            do {
                System.out.println("Select a game to view replay, or B to go back:");
                System.out.println("Type \"D\" + game to delete it from the record (eg: D1)");
                String input = scan.nextLine().toUpperCase();
                if (input.equals("B")) {
                    return;
                }
                try {
                    selection = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number.");
                }
            } while (!gameHistory.getKeyset().contains(selection));

            viewReplay(selection);
        }
    }

    /*
    EFFECTS: allows user to view a replay of a game
     */
    public void viewReplay(int selection) {
        MoveHistory mh = gameHistory.get(selection);
        System.out.println(mh);
        System.out.println("At any time, press B to go back, D to delete from record, or enter to continue");
        Board board = new Board(mh.getPlayer1(), mh.getPlayer2());
        ArrayList<int[]> moves = mh.getMoves();
        for (int[] move : moves) {
            System.out.println(board);
            board.makeMove(move[0], move[1]);
            String input = scan.nextLine().toUpperCase();
            switch (input) {
                case "D":
                    gameHistory.delete(selection);
                    return;
                case "B":
                    return;
            }
        }
    }

    /*
    EFFECTS: sets up 1 or 2 player game
     */
    private void start() {
        System.out.println("Hello Player 1. Please enter your name:");
        String player1name = scan.nextLine();
        System.out.println("Hello Player 2. Please enter your name:");
        String player2name = scan.nextLine();

        int numPlayers = getNumPlayers(scan);

        if (numPlayers == 1) {

            int difficulty = getDifficulty();

            Player player1 = new Player(1, null, player1name);
            Player player2 = new Player(-1, null, player2name);
            game = new Game(player1, player2);
            //player2.setAgent(new Tree(difficulty, game.getBoard(), player1, player2));
            player1.setAgent(new Tree(difficulty, game.getBoard(), player2, player1));

        } else {
            game = new Game(new Player(1, null, player1name),
                    new Player(-1, null, player2name));
        }
        runGame(game);
    }

    /*
    EFFECTS: allows player to select difficulty of AI opponent
     */
    private int getDifficulty() {
        int difficulty = -1;
        do {
            System.out.println("Select difficulty: (1 - 10)");
            try {
                difficulty = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (!(0 < difficulty && difficulty <= 10));
        return difficulty;
    }

    /*
    EFFECTS: runs the game and collects user input
     */
    private void runGame(Game game) {
        while (!game.isOver()) {
            System.out.println(game.getBoard());
            System.out.println(game.getBoard().getCurrentPlayer() + "'s turn.");
            game.update();
            System.out.println(game.getMoveHistory().getLastMove());
        }
        System.out.println(game.getBoard().getWinner() + " won!");
        System.out.println(game.getBoard());
        System.out.println("Enter a name for this game:");

        String gameName = scan.nextLine();

        gameHistory.add(gameName, game.getMoveHistory(), game.getBoard().getWinner());
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
                System.out.println("Please enter a valid number.");
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
    REQUIRES: char is a letter between A - B (either uppercase or lowercase is fine)
    EFFECTS: takes char A-H and converts it into number
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
