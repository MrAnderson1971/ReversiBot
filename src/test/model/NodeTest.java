package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    Node rootNode;
    Node childNode;
    Node parentNode;

    @BeforeEach
    void runBefore() {
        rootNode = new Node(null, null, null);
        childNode = new Node(null, null, null);
        parentNode = new Node(null, null, null);
        rootNode.addChildren(new ArrayList<>(Collections.singletonList(childNode)));
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

}
