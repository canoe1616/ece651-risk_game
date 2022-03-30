package edu.duke.ece651.grp9.risk.shared;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FoodRuleCheckerTest {

    @Test
    void checkMyRule_attack() {
        Player p1 = new Player("red");
        Player p2 = new Player("green");
        p1.setFoodQuantity(2);
        p2.setFoodQuantity(2);

        Territory t1 = new Territory("Two Rivers");
        t1.setOwner(p1);
        t1.setUnits(5,0);

        Territory t2 = new Territory("Tar Valon");
        t2.setOwner(p2);
        t2.setUnits(3, 0);

        Action att = new AttackAction(p1, t1, t2, 1, 0);
        RuleChecker foodRuleChecker = new FoodRuleChecker(null);
        assertEquals(null, foodRuleChecker.checkMyRule(att));

        Action att2 = new AttackAction(p1, t1, t2, 5, 0);
        String exp = "This action is invalid: do not have enough food resources for orders.";
        assertEquals(exp, foodRuleChecker.checkMyRule(att2));
    }


    @Test
    void checkMyRule_move() {
        Player p1 = new Player("red");
        Player p2 = new Player("green");

        Territory t1 = new Territory("a");
        Territory t2 = new Territory("b");
        Territory t3 = new Territory("c");
        Territory t4 = new Territory("d");
        Territory t5 = new Territory("e");
        Territory t6 = new Territory("f");

        t1.setUnits(10);
        p1.setFoodQuantity(151);

        t1.setOwner(p1);
        t2.setOwner(p1);
        t3.setOwner(p1);
        t4.setOwner(p2);
        t5.setOwner(p2);
        t6.setOwner(p2);

        t1.addNeighbors(t2);
        t1.addNeighbors(t3);
        t1.addNeighbors(t4);

        t2.addNeighbors(t1);
        t2.addNeighbors(t4);

        t3.addNeighbors(t1);
        t3.addNeighbors(t4);
        t3.addNeighbors(t5);
        t3.addNeighbors(t6);

        t4.addNeighbors(t1);
        t4.addNeighbors(t2);
        t4.addNeighbors(t4);
        t4.addNeighbors(t6);

        t5.addNeighbors(t3);
        t5.addNeighbors(t6);

        t6.addNeighbors(t4);
        t6.addNeighbors(t5);
        t6.addNeighbors(t3);

        Action move1 = new MoveAction(p1, t1, t6, 5, 0);

        RuleChecker foodRuleChecker = new FoodRuleChecker(null);
        assertEquals(null, foodRuleChecker.checkMyRule(move1));

        t1.setUnits(10);
        p1.setFoodQuantity(149);
        Action move2 = new MoveAction(p1, t1, t6, 5, 0);
        assertEquals("This action is invalid: do not have enough food resources for orders.", foodRuleChecker.checkMyRule(move2));




    }

}