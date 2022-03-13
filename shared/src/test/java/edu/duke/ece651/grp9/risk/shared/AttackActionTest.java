package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttackActionTest {
    @Test
    void BasicTest() {
        Territory src = new Territory("Duke");
        Territory dst = new Territory("UNC");
        Player player = new Player("Luna");
        AttackAction attackAction = new AttackAction(player, src, dst,10);

        assertEquals(10, attackAction.getAttackUnits());
        //assertEquals(dst, attackAction.getDestination());
        //assertEquals(src, attackAction.getSource());
        //assertEquals(player, attackAction.getAttacker());
        assertTrue(attackAction.isSameOriAttack(attackAction));

        attackAction.setAttackUnits(20);
        assertEquals(20, attackAction.getAttackUnits());
    }
}