package edu.duke.ece651.grp9.risk.shared;
/**
 * This interface represents an Abstract Factory pattern for Map creation.
 */

public interface AbstractMapFactory {
  /**
   * Make a map for two people.
   * @return the Map created for two people.
   */

  public Map makeMapForTwo();
  public Map makeMapForThree();
  //public Map makeMapForFour();
  //public Map makeMapForFive();
}
