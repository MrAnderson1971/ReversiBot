package ui;

import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/*
Othello board game.
 */
public class Othello extends JPanel implements MouseListener, ActionListener {

    public static final String SAVE_FILE = "./data/save.json";
    public static final String AUDIO_FILE = "./data/click.wav";
    public static final int SQUARE = 50;
    public static final int WIDTH = 640;
    public static final int HEIGHT = 480;
    public static final int X_MARGIN = (WIDTH - 8 * SQUARE) / 2;
    public static final int Y_MARGIN = (HEIGHT - 8 * SQUARE) / 2;

    public static final Color BLACK = new Color(0, 0, 0);
    public static final Color WHITE = new Color(255, 255, 255);
    public static final Color GREEN = new Color(0, 255, 0);

    private Game game;

    private GameHistory gameHistory;
    private Scanner scan = new Scanner(System.in);

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private Timer timer;

    private int[] move;

    // EFFECTS: runs the game
    public Othello() {
        init();
        jsonWriter = new JsonWriter(SAVE_FILE);
        jsonReader = new JsonReader(SAVE_FILE);
        gameHistory = loadGameHistory();
        //gameHistory = new GameHistory();
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
        for (int i = 0; i < moves.size(); i++) {
            System.out.println(board);
            System.out.println(mh.getOneLine(i));
            board.makeMove(moves.get(i)[0], moves.get(i)[1]);
            String input = scan.nextLine().toUpperCase();
            switch (input) {
                case "D":
                    gameHistory.delete(selection);
                    saveGameHistory();
                    return;
                case "B":
                    return;
            }
        }
    }

    /*
    EFFECTS: sets up visual component
     */
    private void init() {
        menu();
        start();

        setBackground(Color.blue);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);

        timer = new Timer(140, this);
        timer.start();
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

            Player player1 = new Player(1, player1name);
            Player player2 = new Player(-1, player2name);
            game = new Game(player1, player2);
            //player2.setAgent(new Tree(difficulty, game.getBoard())); // comment this out
            player1.setAgent(new Tree(difficulty, game.getBoard()));

        } else {
            game = new Game(new Player(1, player1name),
                    new Player(-1, player2name));
        }
        //init();
        //runGame(game);
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
            update(game.getBoard(), game.getMoveHistory());
            System.out.println(game.getMoveHistory().getLastMove());
        }
        System.out.println(game.getBoard().getWinner() + " won!");
        System.out.println(game.getBoard());
        System.out.println(game.getMoveHistory());
        System.out.println("Enter a name for this game:");

        String gameName = scan.nextLine();

        gameHistory.add(gameName, game.getMoveHistory(), game.getBoard().getWinner());
        saveGameHistory();
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
    public int[] getMove(ArrayList<int[]> validMoves) {
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
        } while (!(listContainsArray(validMoves, move)));

        return move;
    }

    /*
    REQUIRES: char is a letter between A - H (either uppercase or lowercase is fine)
    EFFECTS: takes char A-H and converts it into number
        A => 0, B => 1, etc.
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

    /*
    EFFECTS: play a sound from path
     */
    public void playSound(String path) {
        try {
            AudioInputStream audio = AudioSystem.getAudioInputStream(new File(path));
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            ex.printStackTrace();
        }
    }

    /*
    MODIFIES: this, board, moveHistory
    REQUIRES: board and moveHistory from the same game
    EFFECTS: gets player / computer move, then executes move
     */
    public void update(Board board, MoveHistory moveHistory) {
        if (board.isGameOver()) {
            game.setOver(true);
            return;
        }
        int[] move;
        if (board.getCurrentPlayer().getAgent() == null) { // get player move
            //move = getMove(board.getPossibleMoves());
            move = this.move;

        } else { // get computer move
            //board.getCurrentPlayer().getAgent().train();
            move = board.getCurrentPlayer().getAgent().bestMove();
            board.getCurrentPlayer().getAgent().train();
        }

        if (move != null) {
            moveHistory.add(move, board.getCurrentPlayer().toString());
            board.makeMove(move[0], move[1]);
            this.move = null;
        }

        if (board.getCurrentPlayer().getAgent() != null) {
            // If opponent is computer, update computer AI tree.
            //board.getCurrentPlayer().getAgent().train();
            board.getCurrentPlayer().getAgent().updateMove(move);
            board.getCurrentPlayer().getAgent().train();
        }
    }

    /*
     EFFECTS: true if ArrayList of int arrays contains array
      */
    public static boolean listContainsArray(ArrayList<int[]> list, int[] array) {
        for (int[] i : list) {
            if (Arrays.equals(i, array)) {
                return true;
            }
        }
        return false;
    }

    /*
    EFFECTS: saves GameHistory to file
     */
    public void saveGameHistory() {
        try {
            jsonWriter.open();
            jsonWriter.write(gameHistory);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("Error in writing to save file.");
        }
    }

    /*
    EFFECTS: loads GameHistory from file
     */
    public GameHistory loadGameHistory() {
        GameHistory gh = new GameHistory();
        File tempFile = new File(SAVE_FILE);
        if (!tempFile.exists()) {
            return gh; // File doesn't exist
        }
        try {
            gh = (GameHistory) jsonReader.load();
        } catch (IOException e) {
            System.err.println("Could not read file.");
        }
        return gh;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        ArrayList<int[]> possibleMoves = game.getBoard().getPossibleMoves();

        int x = (e.getX() - X_MARGIN) / SQUARE;
        int y = (e.getY() - Y_MARGIN) / SQUARE;

        if (0 <= x && x < 8 && 0 <= y && y < 8) {
            if (listContainsArray(possibleMoves, new int[]{x, y})) {
                playSound(AUDIO_FILE);
                this.move = new int[]{x, y};
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*
    MODIFIES: this
    EFFECTS: Runs every frame
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        update(game.getBoard(), game.getMoveHistory());
        repaint();
    }

    /*
    EFFECTS: renders the game onto the screen
     */
    private void renderGame(Graphics g, Game game) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        renderBoardSquares(g, game);
        renderPieces(g, game);
    }

    /*
    EFFECTS: renders the board + possible moves
     */
    private void renderBoardSquares(Graphics g, Game game) {

        ArrayList<int[]> possibleMoves = game.getBoard().getPossibleMoves();
        // Draw light squares.
        g.setColor(GREEN);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                g.fillRect(x * SQUARE + X_MARGIN, y * SQUARE + Y_MARGIN, SQUARE, SQUARE);
            }
        }

        // Draw possible moves
        g.setColor(BLACK);
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (listContainsArray(possibleMoves, new int[]{x, y})) {
                    g.fillRect(x * SQUARE + X_MARGIN + SQUARE / 2, y * SQUARE + Y_MARGIN + SQUARE / 2, 4, 4);
                }
            }
        }

        // draw vertical lines
        g.setColor(BLACK);
        for (int x = 0; x < 8; x++) {
            g.drawLine(x * SQUARE + X_MARGIN, Y_MARGIN, x * SQUARE + X_MARGIN, Y_MARGIN + 8 * SQUARE);
        }

        for (int y = 0; y < 8; y++) {
            g.drawLine(X_MARGIN, y * SQUARE + Y_MARGIN, X_MARGIN + 8 * SQUARE, y * SQUARE + Y_MARGIN);
        }
    }

    /*
    EFFECTS: draws light and dark pieces
     */
    private void renderPieces(Graphics g, Game game) {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                if (game.getBoard().getPiece(x, y) != 0) {
                    g.setColor(game.getBoard().getPiece(x, y) == 1 ? BLACK : WHITE);
                    g.fillOval(x * SQUARE + X_MARGIN, y * SQUARE + Y_MARGIN, SQUARE, SQUARE);
                }
            }
        }
    }

    /*
    EFFECTS: renders everything onto the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderGame(g, game);
    }

}
