package edu.duke.ece651.grp9.risk.shared;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import org.junit.jupiter.api.Test;

public class ActionTest {

    @Test
    public void test_performAction() {
        Player p1 = new Player("red");
        Territory t1 = new Territory("Two Rivers");
        p1.addTerritory(t1);
        t1.setOwner(p1);
        t1.setUnit(5);

        Territory t2 = new Territory("Tar Valon");
        p1.addTerritory(t2);
        t2.setOwner(p1);
        t2.setUnit(0);
        t2.addNeighbors(t1);

        Territory t3 = new Territory("Falme");
        p1.addTerritory(t3);
        t3.setOwner(p1);
        t3.setUnit(10);
        t3.addNeighbors(t1);

        Player p2 = new Player("green");
        Territory t4 = new Territory("Andor");
        p2.addTerritory(t4);
        t4.setOwner(p2);
        t4.setUnit(3);
        t2.addNeighbors(t4);

        Territory t5 = new Territory("Tear");
        p2.addTerritory(t5);
        t5.setOwner(p2);
        t5.setUnit(5);
        t5.addNeighbors(t4);

        HashSet<Action> actions = new HashSet<>();
        Action m1 = new MoveAction(p1, t1, t2, 5);
        Action m2 = new MoveAction(p1, t1, t2, 3);
        Action m3 = new MoveAction(p1, t3, t1, 7);

        Action a1 = new AttackAction(p1, t2, t4, 7);

        actions.add(m1);
        actions.add(m2);
        actions.add(m3);

        for (Action action : actions) {
            assertEquals(action.canPerformAction(), null);
        }
        a1.canPerformAction();

        for (Territory territory : p1.getTerritoryList()) {
            assertTrue(territory.mockIsValid());
        }

        for (Action action : actions) {
            action.performAction();
        }

        assertEquals(t1.getUnit(), 4);
        //assertEquals(t2.getUnit(), 0);
        assertEquals(t3.getUnit(), 3);

        Action m4 = new MoveAction(p2, t4, t5, 5);

        assertEquals(m4.canPerformAction(), null);
        assertFalse(t4.mockIsValid());
    }
}

