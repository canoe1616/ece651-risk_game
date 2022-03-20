package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {
    private Player findPlayer(Map map, String name) {
        for (Player p: map.getPlayer()) {
            if (p.getName().equals(name))
                return p;
        }
        return null;
    }

    private Territory findTerritory(Map map, String name) {
        for (Territory t: map.getList()) {
            if (t.getName().equals(name)) {
                return t;
            }
        }
        return null;
    }

    @Test
    void addAttackAction_combine() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Battle battle = new Battle(map);
        Player player = findPlayer(map, "red");

        Territory src = findTerritory(map, "A");
        src.setUnit(10);
        Territory dst = findTerritory(map,"C");
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

        assertEquals(map.findPlayer("purple"), null);
        assertEquals(map.findTerritory("purple"), null);
    }

    @Test
    void addAttackAction_twoPlayersAttacks() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForThree();
        Battle battle = new Battle(map);
        Player p1 = findPlayer(map, "red");
        Player p2 = findPlayer(map, "green");

        Territory src1 = findTerritory(map, "A");
        Territory src2 = findTerritory(map, "D");
        Territory dst = findTerritory(map,"F");
        src1.setUnit(10);
        src2.setUnit(10);
        AttackAction attack1 = new AttackAction(p1, src1, dst, 6);
        AttackAction attack2 = new AttackAction(p2, src2, dst, 4);
        battle.addAttackAction(attack1);
        assertEquals(1, battle.getAllAttackActions().size());
        battle.addAttackAction(attack2);
        assertEquals(2, battle.getAllAttackActions().size());
    }

    @Test
    void simpleBattle() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Player playerRed = findPlayer(map,"red");
        Player playerBlue = findPlayer(map,"blue");
        Territory srcA = findTerritory(map,"A");
        Territory srcB = findTerritory(map,"B");
        srcA.setUnit(5);
        srcA.setOwner(playerRed);
        srcB.setUnit(5);
        srcB.setOwner(playerRed);
        Territory dstC = findTerritory(map,"C");
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
        if (!attackRes.get(0).getState()) {
            assertEquals("blue", dstC.getOwner().getName());
        }
    }

    @Test
    void simpleBattle2() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Player playerRed = findPlayer(map, "red");
        Player playerBlue = findPlayer(map, "blue");
        Territory srcA = findTerritory(map, "A");
        srcA.setUnit(5);
        srcA.setOwner(playerRed);
        Territory dstD = findTerritory(map, "D");
        dstD.setUnit(10);
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