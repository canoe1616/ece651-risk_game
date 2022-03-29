package edu.duke.ece651.grp9.risk.shared;

import java.util.*;

/**
 * This class is to handle all attacks in a battle round. All attacks would be executed after all
 * move actions completed.
 */
public class Battle {

  // The general methodology is to first add all players' attack actions to the hashmap, then iteratively the
  // territory and do the battles on each territory.
  // private final Map territoryMap;
  // key: territory being attacked; value: combined attacks onto the territory
  private HashMap<Territory, HashSet<AttackAction>> territoryUnderAttack;
  private final Map territoryMap;

  /*
   * Constructor to create an Attack
   * @param map is a map for game
   */
  public Battle(Map map) {
    this.territoryMap = map;
    this.territoryUnderAttack = new HashMap<>();
  }

  /**
   * This method currently only used for testing
   */
  public List<AttackAction> getAllAttackActions() {
    List<AttackAction> res = new ArrayList<>();
    for (HashSet<AttackAction> attacks : territoryUnderAttack.values()) {
      for (AttackAction attack : attacks) {
        res.add(attack);
      }
    }
    return res;
  }

  /**
   * The method is to add attacks to the battle. If the attack comes from same player and attack to
   * the same destination, combing the attacks to a single force. Note that the combined attack's
   * source is the first attack's source
   *
   * @param oneAttack is the new attack action
   */
  public void addAttackAction(AttackAction oneAttack) {
    // Update the units in source defender.
    Territory source = oneAttack.getSource();
    source.setUnits(source.getUnits(oneAttack.getUnitLevel()) - oneAttack.getAttackUnits(),
        oneAttack.getUnitLevel());
    source.setUnit(source.getUnit() - oneAttack.getAttackUnits());
    // Add attack action to map
    Territory destination = oneAttack.getDestination();
    if (territoryUnderAttack.containsKey(destination)) {
      for (AttackAction att : territoryUnderAttack.get(destination)) {
        // combine attack actions from the same player of same tech level.
        if (att.isSameOriAttack(oneAttack) && oneAttack.getUnitLevel() == att.getUnitLevel()) {
          att.setAttackUnits(att.getAttackUnits() + oneAttack.getAttackUnits());
          return;
        }
      }
      // new attack from a new player with different tech level.
      territoryUnderAttack.get(destination).add(oneAttack);
    } else {
      HashSet<AttackAction> attackMap = new HashSet<>();
      attackMap.add(oneAttack);
      territoryUnderAttack.put(destination, attackMap);
    }
  }


  /**
   * this method is to get attacks grouped by players
   * @param attacks are the attacks on a specific territory
   * @return a hashmap that stores attacks grouped by player
   */
  private HashMap<Player, HashSet<AttackAction>> getAttacksByPlayer(HashSet<AttackAction> attacks ) {
    HashMap<Player, HashSet<AttackAction>> attacksByPlayer = new HashMap<>();
    for (AttackAction att: attacks) {
      Player p = att.getAttacker();
      if (attacksByPlayer.containsKey(p)) {
        attacksByPlayer.get(att.getAttacker()).add(att);
      } else {
        HashSet<AttackAction> attackActions = new HashSet<>();
        attackActions.add(att);
        attacksByPlayer.put(p, attackActions);
      }
    }
    return attacksByPlayer;
  }

  /**
   * The method is to play the battles on each territory. On each territory, there might be multiple
   * players attack the same destination. The hashset ensures the order on each territory is random
   */
  public void playBattlePhase() {
    for (HashSet<AttackAction> attacksAll : territoryUnderAttack.values()) {
      HashMap<Player, HashSet<AttackAction>> attacksByPlayer = getAttacksByPlayer(attacksAll);
      // a player starts a battle on a territory
      for (HashSet<AttackAction> attacks: attacksByPlayer.values()) {
        AttackAction attack = attacks.iterator().next();
        Player attacker = attack.getAttacker();
        Player defender = attack.getDestination().getOwner();
        Territory destination = attack.getDestination();
        HashMap<Integer, Unit> defenderUnits = destination.getAllUnits();
        HashMap<Integer, Unit> attackerUnits = getAllUnits(attacks);
        doAttacks(attackerUnits,defenderUnits, attacker, defender, destination);
      }
    }
  }

  private Unit makeUnit(int level, int num) {
    Unit unit = null;
    if (level == 0) {
      unit = new Level0Unit();
    } else if (level == 1) {
      unit = new Level1Unit();
    } else if (level == 2) {
      unit = new Level2Unit();
    } else if (level == 3) {
      unit = new Level3Unit();
    } else if (level == 4) {
      unit = new Level4Unit();
    } else if (level == 5) {
      unit = new Level5Unit();
    } else if (level == 6) {
      unit = new Level6Unit();
    }
    unit.setNumUnits(num);
    return unit;
  }

  /**
   * convert the attackAction to Unit hashmap
   * @param attacks
   * @return
   */
  private HashMap<Integer, Unit> getAllUnits(HashSet<AttackAction> attacks) {
    HashMap<Integer, Unit> allAttacks = new HashMap<>();
    for (AttackAction att: attacks) {
      int level = att.getUnitLevel();
      int attackUnitNum = att.getAttackUnits();
      allAttacks.put(att.getUnitLevel(), makeUnit(level, attackUnitNum));
    }
    return allAttacks;
  }


  /**
   * get the Unit with the highest bonus
   * @param unitMap
   * @return
   */
  private Unit getHighestLevelUnit(HashMap<Integer, Unit> unitMap) {
    Unit unit = null;
    for (int i = 6; i >= 0; i--) {
      if (unitMap.get(i) != null && unitMap.get(i).getNumUnits() > 0) {
        unit = unitMap.get(i);
      }
    }
    return unit;
  }

  /**
   * get the Unit with the lowest bonus
   * @param unitMap
   * @return
   */
  private Unit getLowestLevelUnit(HashMap<Integer, Unit> unitMap) {
    Unit unit = null;
    for (int i = 0; i <= 6; i++) {
      if (unitMap.get(i) != null && unitMap.get(i).getNumUnits() > 0) {
        unit = unitMap.get(i);
      }
    }
    return unit;
  }

  private int getBattleUnitNum(HashMap<Integer, Unit> playerUnits) {
    int battleUnitNum = 0;
    for (Integer i : playerUnits.keySet()) {
      battleUnitNum += playerUnits.get(i).getNumUnits();
    }
    return battleUnitNum;
  }

  public void doAttacks(HashMap<Integer, Unit> attackerUnits, HashMap<Integer, Unit> defenderUnits, Player attacker, Player defender, Territory destination) {
    int iterIndex = 0;
    int defenderUnitSum = getBattleUnitNum(defenderUnits);
    int attackerUnitSum = getBattleUnitNum(attackerUnits);;

    while (defenderUnitSum > 0 && attackerUnitSum > 0) {
      Unit attackUnit;
      Unit defendUnit;
      if (iterIndex++ % 2 == 0) { // iteratively do the attacks
        attackUnit = getHighestLevelUnit(attackerUnits);
        defendUnit = getLowestLevelUnit(defenderUnits);
      } else {
        attackUnit = getLowestLevelUnit(attackerUnits);
        defendUnit = getHighestLevelUnit(defenderUnits);
      }
      if (isSuccessBattle(attackUnit, defendUnit)) {
        // defender's unit decreased by 1
        int level = defendUnit.getLevel();
        int unitNum = defendUnit.getNumUnits() - 1;
        defenderUnits.get(level).setNumUnits(unitNum); // update unitNum
      } else {
        // attacker's unit decreased by 1
        int level = attackUnit.getLevel();
        int unitNum = attackUnit.getNumUnits() - 1;
        attackerUnits.get(level).setNumUnits(unitNum);
      }
      defenderUnitSum = getBattleUnitNum(defenderUnits);
      attackerUnitSum = getBattleUnitNum(attackerUnits);;
    }

    if (defenderUnitSum == 0 && attackerUnitSum > 0) { // make a successful attack
      destination.setOwner(attacker);
      for (Integer level: attackerUnits.keySet()) {
       destination.setUnits(attackerUnits.get(level).getNumUnits(), level);
      }
      attacker.addTerritory(destination);
      defender.removeTerritory(destination);
    } else {
      for (Integer level: defenderUnits.keySet()) {
        destination.setUnits(defenderUnits.get(level).getNumUnits(), level);
      }
    }
  }

  /**
   * check if the attack from player A to territory x is success or not
   * @param attack
   * @param defender
   * @return
   */
  private boolean isSuccessBattle(Unit attack, Unit defender) {
    Random attackRoll = new Random();
    Random defenderRoll = new Random();
    int roll1 = attackRoll.nextInt(20);
    int roll2 = defenderRoll.nextInt(20);
    int attackNum = attack.applyBonus(roll1);
    int defendNum = defender.applyBonus(roll2);
    return attackNum > defendNum;
  }
}
