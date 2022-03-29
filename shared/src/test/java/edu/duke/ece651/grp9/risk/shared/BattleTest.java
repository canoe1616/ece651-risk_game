package edu.duke.ece651.grp9.risk.shared;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BattleTest {
    @Test
    void addAttackAction_combine() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Battle battle = new Battle(map);
        Player player = map.findPlayer( "red");

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

        assertEquals(map.findPlayer("purple"), null);
        assertEquals(map.findTerritory("purple"), null);
    }

    @Test
    void addAttackAction_twoPlayersAttacks() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForThree();
        Battle battle = new Battle(map);
        Player p1 = map.findPlayer( "red");
        Player p2 = map.findPlayer( "green");

        Territory src1 = map.findTerritory( "A");
        Territory src2 = map.findTerritory( "D");
        Territory dst = map.findTerritory("F");
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
        Player playerRed = map.findPlayer("red");
        Player playerBlue = map.findPlayer("blue");
        Territory srcA = map.findTerritory("A");
        Territory srcB = map.findTerritory("B");
        srcA.setUnit(5);
        srcA.setOwner(playerRed);
        srcB.setUnit(5);
        srcB.setOwner(playerRed);
        Territory dstC = map.findTerritory("C");
        dstC.setUnit(0);
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
        //if (!attackRes.get(0).getState()) {
            assertEquals("red", dstC.getOwner().getName());
        //}
    }

    @Test
    void simpleBattle2() {
        MapFactory mapFactory = new MapFactory();
        Map map = mapFactory.makeMapForTwo();
        Player playerRed = map.findPlayer("red");
        Player playerBlue = map.findPlayer("blue");
        Territory srcA = map.findTerritory( "A");
        srcA.setUnits(5);
        assertEquals(srcA.getUnits(0), 5);
        srcA.setOwner(playerRed);
        Territory dstD = map.findTerritory("D");
        dstD.setUnits(10);
        assertEquals(dstD.getUnits(0), 10);
        srcA.setOwner(playerBlue);

        AttackAction attack0 = new AttackAction(playerRed, srcA, dstD, 5, 0);
        AttackAction attack1 = new AttackAction(playerBlue, dstD, srcA, 10, 0);

        Battle battle = new Battle(map);
        battle.addAttackAction(attack0);
        battle.addAttackAction(attack1);
        assertEquals(2, battle.getAllAttackActions().size());

        battle.playBattlePhase();
        assertEquals("blue", srcA.getOwner().getName());
        assertEquals(10, srcA.getUnits(0));
        assertEquals("red", dstD.getOwner().getName());
        assertEquals(5, dstD.getUnits(0));
    }

    @Test
    void getHighest_lowest_LevelUnit() {
        // public Unit getHighestLevelUnit(HashMap<Integer, Unit> unitMap)
        Map map = new Map();
        Battle battle = new Battle(map);
        HashMap<Integer, Unit> unitHashMap = new HashMap<>();

        Unit unit1 = new Level1Unit();
        Unit unit2 = new Level5Unit();
        Unit unit3 = new Level2Unit();

        unitHashMap.put(1, unit1);
        unitHashMap.put(5, unit2);
        unitHashMap.put(2, unit3);
        assertEquals(null, battle.getHighestLevelUnit(unitHashMap));
        assertEquals(null, battle.getLowestLevelUnit(unitHashMap));

        unit1.setNumUnits(5);
        unit2.setNumUnits(3);
        unit3.setNumUnits(2);

        unitHashMap.put(1, unit1);
        unitHashMap.put(5, unit2);
        unitHashMap.put(2, unit3);

        int highestLevel = battle.getHighestLevelUnit(unitHashMap).getLevel();
        int lowestLevel = battle.getLowestLevelUnit(unitHashMap).getLevel();
        assertEquals(1, lowestLevel);
        assertEquals(5, highestLevel);

    }

    @Test
    void getAllUnits() {

    }
}