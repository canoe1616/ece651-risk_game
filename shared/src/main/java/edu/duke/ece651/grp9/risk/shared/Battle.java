package edu.duke.ece651.grp9.risk.shared;

import org.checkerframework.checker.units.qual.A;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class Battle {
    private final Map territoryMap;
    // key: territory being attacked; value: combined attacks onto the territory
    private HashMap<Territory, HashSet<AttackAction>> territoryUnderAttack;

    public Battle(Map map) {
        this.territoryMap = map;
        this.territoryUnderAttack = new HashMap<>();
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
        for (Territory terr: territoryMap.getList()) {
            HashSet<AttackAction> attacks = territoryUnderAttack.get(terr);
            if (attacks.size() == 0) {
                continue;
            }
            for (AttackAction att : attacks) {
                playOneAttack(att);
            }
        }
    }

    /**
     * roll two 20-sided dice for attacker and defender, and check which side wins the game.
     * The player with larger number wins; the defender wins if in a tie.
     * @return true if attacker makes a successful attack, false if fail
     */
    private boolean isSuccessAttack() {
        Random attackRoll = new Random();
        Random defenderRoll = new Random();
        int roll1 = attackRoll.nextInt(20);
        int roll2 = defenderRoll.nextInt(20);
        return roll1 > roll2;
    }


    /**
     * Given an attack, do attack with the territory onwer
     * @param attack
     */
    private void playOneAttack(AttackAction attack) {
        Player attacker = attack.getAttacker();
        Territory destination = attack.getDestination();
        int defenderUnit = destination.getUnit();
        int attackerUnit = attack.getAttackUnits();

        while (defenderUnit > 0 && attackerUnit > 0) {
            if (isSuccessAttack()) {
                defenderUnit --;
            } else {
                attackerUnit --;
            }
        }
        if (attackerUnit > 0) {
            // if attacker wins the round, reset the unit and owner
            destination.setOwner(attacker);
            destination.setUnit(attackerUnit);
        } else {
            // if defender wins the round, reset the unit
            destination.setUnit(defenderUnit);
        }
    }

}
