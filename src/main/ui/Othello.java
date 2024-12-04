package ui;

import exceptions.NodeNotFoundException;
import model.*;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/*
Othello board game.
 */
public class Othello extends JPanel implements MouseListener, ActionListener, KeyListener {

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
    private MoveHistory replayGame;
    private int replayGameIndex;

    private Timer timer;
    private Mode mode = Mode.PLAYING;

    private int[] move;

    private int replayIndex;

    // MODIFIES: this
    // EFFECTS: runs the game
    public Othello() {
        jsonWriter = new JsonWriter(SAVE_FILE);
        jsonReader = new JsonReader(SAVE_FILE);
        gameHistory = loadGameHistory();
        setBackground(Color.blue);
        setFocusable(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        addMouseListener(this);
        addKeyListener(this);

        timer = new Timer(140, this);
        init();
        //gameHistory = new GameHistory();
    }

    // MODIFIES: this
    // EFFECTS: Displays main menu
    private void menu() {
        System.out.println("1");
        String[] options = new String[]{"New game", "Previous games"};
        int selection = JOptionPane.showOptionDialog(null, "Select", "Main menu", JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        switch (selection) {
            case -1:
                System.exit(1);
            case 0:
                mode = Mode.PLAYING;
                start();
                break;
            case 1:
                viewRecord();
                break;
            default:
                break;
        }
    }

            /*
        String selection = "";

        do {
            System.out.println("Press p to play, or v to view past games.");
            selection = scan.nextLine().toLowerCase();
        } while (!selection.equals("p") && !selection.equals("v"));
        if (selection.equals("p")) {
            return;
        }*/


    /*
    MODIFIES: this
    EFFECTS: Displays record of past games
     */
    private void viewRecord() {
        replayGameIndex = 0;
        replayGame = null;

        if (gameHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No games played yet.");
            menu();
            return;
        }

        String[] options = new String[gameHistory.getDisplayMenu().size()];
        for (int i = 0; i < gameHistory.getDisplayMenu().size(); i++) {
            options[i] = i + ": " + gameHistory.optionToString(i);
        }

        Object selection = JOptionPane.showInputDialog(this, "Choose a game to view",
                "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (selection == null) {
            menu();
            return;
        }

        for (int i = 0; i < options.length; i++) {
            if (selection.toString().equals(options[i])) {
                replayIndex = 0;
                viewReplay(i);
            }
        }
    }

    /*
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
        }*/

    /*
    EFFECTS: allows user to view a replay of a game
     */
    public void viewReplay(int selection) {
        replayGame = gameHistory.get(selection);
        replayGameIndex = selection;
        mode = Mode.REPLAYING;
        game = new Game(replayGame.getPlayer1(), replayGame.getPlayer2());
        timer.start();

        if (replayGameIndex >= replayGame.getMoves().size()) {
            replayGame = null;
            replayGameIndex = 0;
            menu();
        }
    }

    /*
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
        }*/

    /*
    MODIFIES: this
    EFFECTS: sets up visual component
     */
    private void init() {
        game = null;

        timer.start();
        menu();
        if (mode == Mode.PLAYING) {
            //start();
        }

    }

    /*
    MODIFIES: this
    EFFECTS: sets up 1 or 2 player game
     */
    private void start() {
        mode = Mode.PLAYING;
        //System.out.println("Hello Player 1. Please enter your name:");
        String player1name = JOptionPane.showInputDialog("Hello Player 1. Please enter your name:") + "";
        //System.out.println("Hello Player 2. Please enter your name:");
        String player2name = JOptionPane.showInputDialog("Hello Player 2. Please enter your name:") + "";

        int numPlayers = getNumPlayers();

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
        If user presses cancel/X, terminates the program.
     */
    private int getDifficulty() {
        /*
        int difficulty = -1;
        do {
            System.out.println("Select difficulty: (1 - 10)");
            try {
                difficulty = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (!(0 < difficulty && difficulty <= 10));
        return difficulty;*/

        String[] options = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        Object selection = JOptionPane.showInputDialog(this, "Select difficulty",
                "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        System.out.println(selection);
        if (selection == null) {
            System.exit(1);
        }
        return Integer.parseInt(selection.toString());
    }

    /*
    MODIFIES: game
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
    If user presses cancel/X, terminates the program.
     */
    private int getNumPlayers() {
        /*
        int numPlayers = -1;
        do {
            System.out.println("One player or two players?");
            try {
                numPlayers = Integer.parseInt(scan.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        } while (numPlayers != 1 && numPlayers != 2);
        return numPlayers;*/

        String[] options = {"1", "2"};
        Object selectionObject = JOptionPane.showInputDialog(this, "Select number of players",
                "Menu", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
        if (selectionObject == null) {
            System.exit(1);
        }
        return Integer.parseInt(selectionObject.toString());
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
            try {
                board.getCurrentPlayer().getAgent().updateMove(move);
            } catch (NodeNotFoundException e) {
                // oops
            }
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
            JOptionPane.showMessageDialog(this, "Game saved.");
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
            JOptionPane.showMessageDialog(this, "Successfully loaded record from memory.");
        } catch (IOException e) {
            System.err.println("Could not read file.");
        }
        return gh;
    }

    /*
    stub method for interface
    EFFECTS: none
     */
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /*
    stub method for interface
    EFFECTS: none
    */
    @Override
    public void mousePressed(MouseEvent e) {

    }

    /*
    MODIFIES: this
    EFFECTS: handles mouse input
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (mode == Mode.PLAYING) {
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
    }

    /*
stub method for interface
EFFECTS: none
*/
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    /*
stub method for interface
EFFECTS: none
*/
    @Override
    public void mouseExited(MouseEvent e) {

    }

    /*
    MODIFIES: this
    EFFECTS: Runs every frame
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (game != null) {
            repaint();
            update(game.getBoard(), game.getMoveHistory());

            if (game.isOver()) {
                if (mode == Mode.PLAYING) {
                    String gameName = JOptionPane.showInputDialog(this, game.getBoard().getWinner() + " won!"
                            + "\nEnter a name for this game:") + "";
                    System.out.println(game.getMoveHistory());
                    gameHistory.add(gameName, game.getMoveHistory(), game.getBoard().getWinner());
                    saveGameHistory();
                } else {
                    JOptionPane.showMessageDialog(this, game.getBoard().getWinner() + " won!");
                }
                timer.stop();
                init();
            }
        }
    }

    /*
    MODIFIES: g
    EFFECTS: renders the game onto the screen
     */
    private void renderGame(Graphics g, Game game) {
        g.setColor(Color.blue);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        g.setColor(BLACK);
        g.drawString(game.getBoard().getCurrentPlayer() + "'s turn", 0, 36);
        if (mode == Mode.REPLAYING) {
            g.drawString("Press enter to continue, backspace to return to menu, del to delete replay", 0, HEIGHT - 12);
        }
        renderBoardSquares(g, game);
        renderPieces(g, game);
    }

    /*
    MODIFIES: g
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
    MODIFIES: g
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
    MODIFIES: g
    EFFECTS: renders everything onto the screen
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderGame(g, game);
    }

    /*
    stub method for interface
    EFFECTS: none
    */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    /*
    stub method for interface
    EFFECTS: none
    */
    @Override
    public void keyPressed(KeyEvent e) {

    }

    /*
    MODIFIES: this
    EFFECTS: handles keyboard input
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (mode == Mode.REPLAYING) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_ENTER:
                    playSound(AUDIO_FILE);
                    int[] m = replayGame.getMoves().get(replayIndex);
                    game.getBoard().makeMove(m[0], m[1]);
                    replayIndex++;
                    break;
                case KeyEvent.VK_BACK_SPACE:
                    viewRecord();
                    break;
                case KeyEvent.VK_DELETE:
                    if (0 == JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this?")) {
                        gameHistory.delete(replayGameIndex);
                        saveGameHistory();
                        viewRecord();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
