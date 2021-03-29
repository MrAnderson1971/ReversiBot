package model;

import exceptions.NodeNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

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
        level1Tree = new Tree(1, new Board(player1, player2));
        level10Tree = new Tree(10, new Board(player1, player2));
        player1.setAgent(level1Tree);
        player2.setAgent(level10Tree);
    }

    @Test
    void testTrain() {
        /*Game game = new Game(player1, player2);
        Board board = game.getBoard();
        int turns = 0;
        while (!board.isGameOver()) {
            System.out.println(turns++);
            int[] move;
            if (board.getCurrentPlayer().equals(player2)) {
                move = board.getCurrentPlayer().getAgent().bestMove();
            } else {
                ArrayList<int[]> moves = board.getPossibleMoves();
                move = moves.get(new Random().nextInt(moves.size()));
            }
            board.getCurrentPlayer().getAgent().train();
            board.makeMove(move[0], move[1]);
        }

        // No way player1 wins because it is weak
        //assertTrue(player2.equals(board.getWinner()) || board.getWinner() == null);
        assertNotEquals(player1, board.getWinner());*/

        for (int lvl = 1; lvl <= 10; lvl++) {
            Tree tree = new Tree(lvl, new Board(player1, player2));
            player1.setAgent(tree);
            int depth = 1;
            while (!tree.getCurrentMove().isLeaf()) {
                System.out.println("Depth so far: " + depth++ + ", level: " + lvl);
/*
            assertEquals(level10Tree.getCurrentMove().getBoard().getPossibleMoves().size(),
                    level10Tree.getCurrentMove().getChildren().size());*/
                if (tree.getCurrentMove().getParent() != null) {
                    assertNotSame(tree.getCurrentMove().getParent().getPlayer(), tree.getCurrentMove().getPlayer());
                }
                assertTrue((tree.getCurrentMove().getBoard() == null) ||
                        (tree.getCurrentMove() != null &&
                                tree.getCurrentMove().getBoard().getPossibleMoves().size() == 0));

                tree.bestMove();
                tree.train();
            }
            System.out.println("done level " + lvl);
        }

    }

    @Test
    void testUpdateMove() {
        for (Node child : level1Tree.getCurrentMove().getChildren()) {
            System.out.println(Arrays.toString(child.getMove()));
        }
        try {
            level10Tree.updateMove(new int[]{2, 3});
            assertEquals(Arrays.toString(new int[]{2, 3}), Arrays.toString(level10Tree.getCurrentMove().getMove()));

        } catch (NodeNotFoundException e) {
            fail("unexpected");
        }

        try {
            level1Tree.updateMove(new int[]{3, 2});
            assertEquals(Arrays.toString(new int[]{3, 2}), Arrays.toString(level1Tree.getCurrentMove().getMove()));

        } catch (NodeNotFoundException e) {
            fail("unexpected");
        }

        try {
            level10Tree.updateMove(new int[]{0, 0});
            fail("unexpected");
        } catch (NodeNotFoundException e) {
            // expected
        }
        //assertEquals("Node not found.", e.getMessage());
    }

    @Test
    void testBestMove() {
        Node bestNode = level10Tree.getCurrentMove().bestMove();
        assertEquals(Arrays.toString(bestNode.getMove()), Arrays.toString(level10Tree.bestMove()));
        assertEquals(bestNode, level10Tree.getCurrentMove());
    }
}
