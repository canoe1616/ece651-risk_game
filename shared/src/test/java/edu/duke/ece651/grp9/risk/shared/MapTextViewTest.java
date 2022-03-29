package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        assertEquals(exp, view.displayGameState(map.findPlayer("red")));
    }

    @Test
    void displayWinInfo() {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        Player p1 = new Player("blue");
        Territory ter = new Territory("NC");
        Map map = new Map();
        map.addPlayer(p1);
        map.addTerritory(ter);
        p1.addTerritory(ter);
        ter.setUnit(15);
        MapTextView view = new MapTextView(map);
        String exp = "end_game = win\n" +
                "blue Player:\n" +
                "----------------------------------\n" +
                "15 units in NC(next to)\n" +
                "\n" +
                "\n" +
                "Congratulations! You win the game!\n";
        assertEquals(exp, view.sendInfoWinner("blue", map));

    }

    @Test
    void displayGameOverInfo() {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        Player p1 = new Player("blue");
        Territory ter = new Territory("NC");
        Map map = new Map();
        map.addPlayer(p1);
        MapTextView view = new MapTextView(map);
        String exp = "end_game = game over\n" +
                "blue Player:\n" +
                "----------------------------------\n" +
                "\n" +
                "\n" +
                "The game is over now.\n";
        assertEquals(exp, view.sendInfoLoser("blue", map));
    }
}
