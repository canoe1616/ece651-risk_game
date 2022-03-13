package edu.duke.ece651.grp9.risk.shared;


public class AttackAction {
    private final Player attacker;
    private final Territory source;
    private final Territory destination;
    private int attackUnits;
    // TODO(rune): prefer to enum, but conflicts to clover.
    private boolean win;

    public AttackAction(Player player, Territory source, Territory destination, int numUnits) {
        this.source = source;
        this.destination = destination;
        this.attackUnits = numUnits;
        this.attacker = player;
        this.win = false;  // default battle status as false
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

    public void setWin() {
        this.win = true;
    }
}