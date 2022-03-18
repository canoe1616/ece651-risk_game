package edu.duke.ece651.grp9.risk.shared;

import java.util.*;

public class Battle {
    // The general methodology is to first add all players' attack actions to the hashmap, then iteratively the
    // territory and do the battles on each territory.
    // private final Map territoryMap;
    // key: territory being attacked; value: combined attacks onto the territory
    private HashMap<Territory, HashSet<AttackAction>> territoryUnderAttack;
  private final Map territoryMap;

   public Battle(Map map) {
        this.territoryMap = map;
        this.territoryUnderAttack = new HashMap<>();
    }

    /**
     * This method currently only used for test
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
     * Add attacks to the battle. If the attack comes from same player and attack to the same destination,
     * combing the attacks to a single force.
     * Note that the combined attack's source is the first attack's source
     * @param oneAttack is the new attack action
     */
    public void addAttackAction(AttackAction oneAttack) {
        // Update the units in source defender.
        Territory source = oneAttack.getSource();
        source.setUnit(source.getUnit()- oneAttack.getAttackUnits());
        // Add attack action to map
        Territory destination = oneAttack.getDestination();
        if (territoryUnderAttack.containsKey(destination)) {
            for (AttackAction att: territoryUnderAttack.get(destination)) {
                // combine attack actions from the same player.
                if (att.isSameOriAttack(oneAttack)) {
                    att.setAttackUnits(att.getAttackUnits() + oneAttack.getAttackUnits());
                    return;
                }
            }
            // new attack from a new player.
            territoryUnderAttack.get(destination).add(oneAttack);
        } else {
            HashSet<AttackAction> attackSet = new HashSet<>();
            attackSet.add(oneAttack);
            territoryUnderAttack.put(destination, attackSet);
        }
    }

    /**
     * Play the battles on each territory. On each territory, there might be multiple players attack the same destination.
     * The hashset ensures the order on each territory is random
     */
    public void playBattlePhase() {
        for (HashSet<AttackAction> attacks : territoryUnderAttack.values()) {
            for (AttackAction att : attacks) {
                att.performAction();
            }
        }
    }



}
