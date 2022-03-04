package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTextViewTest {
    @Test
    void test_getAdjacencyText() {
        MapTextView view = new MapTextView(new Map());
        Territory t = new Territory("RiverWood", 10, new Player('g'));
        t.addNeighbors(new Territory("Hawii", 10, new Player('g')));
        t.addNeighbors(new Territory("Durham", 10, new Player('b')));
        String exp = "Hawii, Durham";
        assertEquals(exp, view.getAdjacencyText(t));
    }

    @Test
    void test_displayGeneralInfo() {
        Map map = new Map();
        MapTextView view = new MapTextView(map);
        String exp = "g Player:\n" +
                "----------------------------------\n" +
                "10 units in D(next to: B, C)\n" +
                "10 units in C(next to: A, D)\n" +
                "\n" +
                "b Player:\n" +
                "----------------------------------\n" +
                "10 units in B(next to: A, D)\n" +
                "10 units in A(next to: D, C)\n" +
                "\n" +
                "You are the g Player, what would you like to do?\n" +
                " (M)ove\n" +
                " (a)ttack\n" +
                " (D)one\n";
        assertEquals(exp, view.displayGameState(new Player('g')));
    }
}
