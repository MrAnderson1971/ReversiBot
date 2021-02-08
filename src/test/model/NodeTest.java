package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    Node rootNode;
    Node childNode;
    Node parentNode;
    Node parentNode2;

    @BeforeEach
    void runBefore() {
        rootNode = new Node(null, null, null);
        childNode = new Node(null, null, null);
        parentNode = new Node(null, null, null);
        rootNode.addChildren(new ArrayList<>(Collections.singletonList(childNode)));
        parentNode = new Node(null, null, null);

        ArrayList<Node> children = new ArrayList<>();
        Node child = new Node(null, null, null);
        child.addGame();
        child.setWins(-2);
        children.add(child);
        child = new Node(null, null, null);
        child.addGame();
        child.addGame();
        child.addGame();
        child.setWins(-5);
        children.add(child);
        child = new Node(null, null, null);
        children.add(child);
        parentNode2 = new Node(null, null, null);
        parentNode2.addChildren(children);
    }

    @Test
    void testAddChildren() {
        ArrayList<Node> kidNodes = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            kidNodes.add(new Node(null, null, null));
        }
        parentNode.addChildren(kidNodes);
        assertEquals(10, parentNode.getChildren().size());

        for (Node child : parentNode.getChildren()) {
            assertEquals(parentNode, child.getParent());
        }
    }

    @Test
    void testUct() {
        childNode.setWins(42.0);
        assertEquals(42 + Math.sqrt((2 * Math.log(1)) / 1),
                childNode.uct());
    }

    @Test
    void testAddGame() {
        assertEquals(1.0, rootNode.getGames());
        rootNode.addGame();
        assertEquals(2.0, rootNode.getGames());
    }

    @Test
    void testBestNode() {
        assertEquals(childNode, rootNode.bestNode());
        assertEquals(parentNode2.getChildren().get(2), parentNode2.bestNode());
    }

    @Test
    void testIsLeaf() {
        assertTrue(childNode.isLeaf());
        assertFalse(rootNode.isLeaf());
    }

    @Test
    void testRandomChild() {
        assertTrue(parentNode2.getChildren().contains(parentNode2.randomChild()));
    }

    @Test
    void testBestMove() {
        assertEquals(childNode, rootNode.bestMove());
        assertEquals(parentNode2.getChildren().get(1), parentNode2.bestMove());
    }

    @Test
    void testGetBoard() {
        assertNull(rootNode.getBoard());
    }

    @Test
    void testGetWins() {
        assertEquals(0.0, rootNode.getWins());
    }

    @Test
    void testGetMove() {
        assertNull(rootNode.getMove());
    }

    @Test
    void testGetPlayer() {
        assertNull(rootNode.getPlayer());
    }

}
