package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;

public class ActionRuleChecker {

  public String checkLoseAction(String input) {
    if (input.length() != 1) {
      return "the input length is invalid, please enter again!";
    } else if (!(input.charAt(0) == 'Q' || input.charAt(0) == 'q' || input.charAt(0) == 'C'
        || input.charAt(0) == 'c')) {
      return "the input character is invalid, please enter again!";
    }
    return null;
  }

  public String checkAction(String input) {
    if (input.length() != 1) {
      return "the input length is invalid, please enter again!";
    } else if (!(input.charAt(0) == 'M' || input.charAt(0) == 'A' || input.charAt(0) == 'D'
        || input.charAt(0) == 'm' || input.charAt(0) == 'a' || input.charAt(0) == 'd')) {
      return "the input character is invalid, please enter again!";
    }
    return null;
  }


  public String checkColor(String input, HashSet<String> remainingColors) {
    if (!remainingColors.contains(input)) {
      return "The color you input is invalid, please enter again";
    }
    return null;
  }

  public String checkUnit(String input, Player player) {
    String[] words = input.split(" ");

    if (words.length != player.getTerritoryList().size()) {
      return "The input is invalid: Must enter " + player.getTerritoryList().size() + " separate numbers.";
    }

    int sum = 0;
    for (int i = 0; i < words.length; i++) {
      int numUnits = 0;
      try {
        numUnits = Integer.parseInt(words[i]);
      } catch (NumberFormatException e) {
        return "The input is invalid: input must only be numbers.";
      }
      sum += numUnits;
    }

    if (sum != 30) {
      return "This input is invalid: Sum of units must equal 30.";
    }
    
    return null;
  }
}