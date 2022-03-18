package edu.duke.ece651.grp9.risk.shared;

import java.util.Random;

public class AttackAction implements Action {
    private final Player attacker;
    private final Territory source;
    private final Territory destination;
    private int attackUnits;
    private final RuleChecker attackChecker;
    // TODO(rune): prefer to enum, but conflicts to clover.
    private boolean win;


    // "A B 10"
    public AttackAction(Player player, Territory source, Territory destination, int numUnits) {
        this.source = source;
        this.destination = destination;
        this.attackUnits = numUnits;
        this.attacker = player;
        source.syncUnits();
        destination.syncUnits();
        this.attackChecker = new UnitsRuleChecker(new OwnerRuleChecker(new AttackRuleChecker(null)));
        this.win = false;  // default battle status as false
    }

    @Override
    public String canPerformAction() {
        return attackChecker.checkAction(attacker, source, destination, attackUnits);
    }

    @Override
    public void performAction() {
        int defenderUnit = destination.getUnit();
        Player defender = destination.getOwner();

        while (defenderUnit > 0 && attackUnits> 0) {
            if (isSuccessAttack()) {
                defenderUnit --;
            } else {
                attackUnits --;
            }
        }
        if (attackUnits > 0) {
            win = true;
            // if attacker wins the round, reset the unit and owner
            destination.setOwner(attacker);
            destination.setUnit(attackUnits);
            // if attacker wins the round, add the unit to attacker territory list
            attacker.addTerritory(destination);
            defender.removeTerritory(destination);
        } else {
            // if defender wins the round, reset the unit
            destination.setUnit(defenderUnit);
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

    public int getAttackUnits() {
        return attackUnits;
    }

    public Territory getDestination() {
        return destination;
    }

    public Territory getSource() {
        return source;
    }

    public Player getAttacker() {
        return attacker;
    }

    public boolean getState() {
        return win;
    }
    /**
     * if player 1 attacks territory X with units from multiple of her own territories, the attacks count as a single combined force.
     * @param att another attack
     * @return true if can be combined, false if cannot be combined
     */
    public boolean isSameOriAttack(AttackAction att) {
        return this.attacker.equals(att.getAttacker()) && this.destination.equals(att.getDestination());
    }

    /**
     * If the attacks are the sameOriAttack, the attack units should be reset.
     * eg: Player 1 attacks 5 units from territory A to D
     * Player 1 attacks 3 units from territory B to D
     * Player 1 attacks 8 units to D
     * @param units
     */
    public void setAttackUnits(int units) {
        this.attackUnits = units;
    }


    //public AttackAction parseInput()
}
