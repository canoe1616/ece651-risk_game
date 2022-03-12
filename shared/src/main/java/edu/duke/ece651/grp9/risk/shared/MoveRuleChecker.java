package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * This class makes sure the source Territory is connected to destination Territory
 *
 * @author PROY
 * @since 10 March 2022
 */
public class MoveRuleChecker extends RuleChecker {

  /**
   * Adds the next Action rule to our list
   *
   * @param next Action rule in list
   */
  public MoveRuleChecker(RuleChecker next) {
    super(next);
  }

  /**
   * Checks if source Territory is connected to destination Territory
   *
   * @param player is the Player performing the Action
   * @param source is the Territory we are moving units from
   * @param destination is the Territory we are moving units to
   * @param numUnits is the number of units we are moving from source to desination
   * @return String description of error if invalid move, or null if okay
   */
  @Override
  protected String checkMyRule(Player player, Territory source, Territory destination, int numUnits) {
    Queue<Territory> queue = new LinkedList<Territory>();
    HashSet<Territory> visited = new HashSet<Territory>();
    queue.add(source);
    visited.add(source);
    while (!queue.isEmpty()) {
      Territory front = queue.poll();
      if (front.equals(destination)) {
        return null;
      }
      for (Territory t : front.getNeighbors()) {
        if (t.getOwner().equals(player) && !visited.contains(t)) {
          queue.add(t);
          visited.add(source);
        }
      }
    }
    return "This action is invalid: " + source.getName() + " is not connected to " + destination.getName() + ".";
  }
}

