package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackActionTest {

    @Test
    void canPerformAction_invalid() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnit(10);
        t2.setUnit(0);
        // not neighbors
        AttackAction att = new AttackAction(p1, t1, t2, 6);
        String err = att.canPerformAction();
        assertNotEquals(null, err);
        // units out of bound
        AttackAction att2 = new AttackAction(p1, t1, t2, 100);
        err = att2.canPerformAction();
        assertNotEquals(null, err);
    }


    @Test
    void canPerformAction_valid() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.addNeighbors(t2);
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnit(10);
        t2.setUnit(0);
        AttackAction att = new AttackAction(p1, t1, t2, 6);
        String err = att.canPerformAction();
        assertEquals(null, err);
    }

    @Test
    void BasicTest() {
        Territory src = new Territory("Duke");
        Territory dst = new Territory("UNC");
        Player player = new Player("Luna");
        AttackAction attackAction = new AttackAction(player, src, dst,10);

        assertEquals(10, attackAction.getAttackUnits());
        assertEquals(dst, attackAction.getDestination());
        assertEquals(src, attackAction.getSource());
        assertEquals(player, attackAction.getAttacker());
        assertTrue(attackAction.isSameOriAttack(attackAction));

        attackAction.setAttackUnits(20);
        assertEquals(20, attackAction.getAttackUnits());
    }

    @Test
    void performAction_10vs0() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnit(10);
        t2.setUnit(0);
        AttackAction att = new AttackAction(p1, t1, t2, 6);
        att.performAction();
        assertEquals(p1, t2.getOwner());
        assertEquals(6, t2.getUnit());
    }


    @Test
    void performAction_0vs1() {
        Player p1 = new Player("red");
        Player p2 = new Player("blue");
        Territory t1 = new Territory("NC");
        Territory t2 = new Territory("CA");
        t1.setOwner(p1);
        t2.setOwner(p2);
        t1.setUnit(10);
        t2.setUnit(1);
        AttackAction att = new AttackAction(p1, t1, t2, 0);
        att.performAction();
        assertEquals(p2, t2.getOwner());
    }
}
