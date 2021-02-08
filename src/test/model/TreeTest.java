package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {

    Tree blankTree;
    Tree level1Tree;
    Tree level10Tree;

    Player player1;
    Player player2;

    @BeforeEach
    void runBefore() {
        /*Player player1 = new Player(1, "one");
        Player player2 = new Player(-1, "two");
        blankTree = new Tree(1, new Board(player1, player2), player1, player2);*/

        player1 = new Player(1, "one");
        player2 = new Player(-1, "two");
        level1Tree = new Tree(1, new Board(player1, player2), player1, player2);
        level10Tree = new Tree(10, new Board(player1, player2), player2, player1);
        player1.setAgent(level1Tree);
        player2.setAgent(level10Tree);
    }

    @Test
    void testTrain() {
        Game game = new Game(player1, player2);
        Board board = game.getBoard();
        int turns = 0;
        while (!board.isGameOver()) {
            System.out.println(turns++);
            int[] move = board.getCurrentPlayer().getAgent().bestMove();
            board.getCurrentPlayer().getAgent().train();
            //moveHistory.add(move, board.getCurrentPlayer().toString());
            board.makeMove(move[0], move[1]);
        }

        // No way player1 wins because it is weak
        assertTrue(player2.equals(board.getWinner()) || board.getWinner() == null);
        assertNotEquals(player1, board.getWinner());
    }
}
