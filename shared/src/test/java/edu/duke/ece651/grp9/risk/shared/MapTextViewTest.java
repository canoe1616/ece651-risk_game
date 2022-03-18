package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapTextViewTest {
    @Test
    void test_displayGeneralInfo() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        map.findTerritory("A").setUnit(10);
        map.findTerritory("B").setUnit(10);
        map.findTerritory("C").setUnit(10);
        map.findTerritory("D").setUnit(10);
        MapTextView view = new MapTextView(map);
        String exp =
                "blue Player:\n" +
                        "----------------------------------\n" +
                        "10 units in C(next to: A, D)\n" +
                        "10 units in D(next to: B, C)\n" +
                        "\n" +
                        "red Player:\n" +
                        "----------------------------------\n" +
                        "10 units in A(next to: B, C)\n" +
                        "10 units in B(next to: A, D)\n\n";
          /*
                        "You are the red Player, what would you like to do?\n" +
                        " (M)ove\n" +
                        " (A)ttack\n" +
                        " (D)one\n";
                        */
        assertEquals(exp, view.displayGameState(null, map.findPlayer("red")));
    }

    @Test
    void test_displayPlayerLose() {
        Player p1 = new Player("blue");
        Territory ter = new Territory("NC");
        Map map = new Map();
        map.addPlayer(p1);
        MapTextView view = new MapTextView(map);
        String exp = "Player blue, you lose the game!"
                + " What would you like to do?\n" +
                " (Q)uit\n" + " (C)ontinue watching game\n";
        assertEquals(exp, view.displayGameState(null, p1));

    }

    @Test
    void test_displayGameOver() {
        Player p1 = new Player("blue");
        Territory ter = new Territory("NC");
        Map map = new Map();
        map.addPlayer(p1);
        MapTextView view = new MapTextView(map);
        String exp ="Game Over! Player blue wins the game!";
        assertEquals(exp, view.displayGameState(p1, p1));

    }
}
