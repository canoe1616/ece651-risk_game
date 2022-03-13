package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {

    @Test
    void addAttackAction() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Battle battle = new Battle(map);
        Player player = map.findPlayer("red");
        Territory src = map.findTerritory("A");
        src.setUnit(10);
        Territory dst = map.findTerritory("C");
        dst.setUnit(5);
        AttackAction attack0 = new AttackAction(player, src, dst, 6);

        battle.addAttackAction(attack0);
        assertEquals(1, battle.getAllAttackActions().size());
        assertEquals(4, src.getUnit());
        assertEquals(5, dst.getUnit());

        AttackAction attack1= new AttackAction(player, dst, src, 2);
        battle.addAttackAction(attack1);
        assertEquals(2, battle.getAllAttackActions().size());
        assertEquals(4, src.getUnit());
        assertEquals(3, dst.getUnit());

        AttackAction attack2 = attack0;
        attack2.setAttackUnits(2);
        battle.addAttackAction(attack2);
        assertEquals(2, battle.getAllAttackActions().size());
        assertEquals(2, src.getUnit());
        assertEquals(3, dst.getUnit());
    }

    @Test
    void simpleBattle() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Player playerRed = map.findPlayer("red");
        Player playerBlue = map.findPlayer("blue");
        Territory srcA = map.findTerritory("A");
        Territory srcB = map.findTerritory("B");
        srcA.setUnit(5);
        srcA.setOwner(playerRed);
        srcB.setUnit(5);
        srcB.setOwner(playerRed);
        Territory dstC = map.findTerritory("C");
        dstC.setUnit(5);
        dstC.setOwner(playerBlue);
        AttackAction attack0 = new AttackAction(playerRed, srcA, dstC, 1);
        AttackAction attack1 = new AttackAction(playerRed, srcB, dstC, 4);

        Battle battle = new Battle(map);
        battle.addAttackAction(attack0);
        battle.addAttackAction(attack1);
        assertEquals(1, battle.getAllAttackActions().size());

        battle.playBattlePhase();
        List<AttackAction> attackRes = battle.getAllAttackActions();
        assertEquals(attackRes.size(), 1);
        if (attackRes.get(0).getState()) {
            assertEquals("red", dstC.getOwner().getName());
        } else {
            assertEquals("blue", dstC.getOwner().getName());
        }
    }

    @Test
    void simpleBattle2() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Player playerRed = map.findPlayer("red");
        Player playerBlue = map.findPlayer("blue");
        Territory srcA = map.findTerritory("A");
        srcA.setUnit(5);
        srcA.setOwner(playerRed);
        Territory dstD = map.findTerritory("D");
        dstD.setUnit(10);
        //test
        srcA.setOwner(playerBlue);
        AttackAction attack0 = new AttackAction(playerRed, srcA, dstD, srcA.getUnit());
        AttackAction attack1 = new AttackAction(playerBlue, dstD, srcA, dstD.getUnit());

        Battle battle = new Battle(map);
        battle.addAttackAction(attack0);
        battle.addAttackAction(attack1);
        assertEquals(2, battle.getAllAttackActions().size());

        battle.playBattlePhase();
        assertEquals("blue", srcA.getOwner().getName());
        assertEquals(10, srcA.getUnit());
        assertEquals("red", dstD.getOwner().getName());
        assertEquals(5, dstD.getUnit());
    }
}