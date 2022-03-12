package edu.duke.ece651.grp9.risk.shared;

public class AttackAction {
    private final Player attacker;
    private final Territory source;
    private final Territory destination;
    private int attackUnits;

    public AttackAction(Player player, Territory source, Territory destination, int numUnits) {
        this.source = source;
        this.destination = destination;
        this.attackUnits = numUnits;
        this.attacker = player;
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

    /**
     * if player A attacks territory X with units from multiple of her own territories, they count as a single combined force.
     * @param att another attack
     * @return true if can be combined, false if cannot be combined
     */
    public boolean isSameOriAttack(AttackAction att) {
        return this.attacker == att.getAttacker() && this.destination == att.getDestination();
    }

    /**
     * set the units for attack action
     * @param units
     */
    public void setAttackUnits(Integer units) {
        this.attackUnits = units;
    }
}