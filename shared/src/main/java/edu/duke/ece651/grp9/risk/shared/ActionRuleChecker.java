package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;

public class ActionRuleChecker {

    public String checkAction( String input){
        if(input.length() != 1){
            return "the input length is invalid, please enter again!";
        }
        else if(!(input.charAt(0) == 'M' || input.charAt(0) == 'm' || input.charAt(0) == 'A'
                && input.charAt(0) == 'a' || input.charAt(0) == 'D' || input.charAt(0) == 'd' )){
            return "the input character is invalid, please enter again!";
        }
        return null;
    }


    public String checkColor(String input, HashSet<String> remainingColors ){
        if(!remainingColors.contains(input)){
            return "The color you input is invalid, please enter again";
        }
        return null;
    }

}

//color rule checker

//
//    public String checkUnit(String input, Player player){
//        //if the string the player input is bigger than the units this player had currently
//        //Q: what is the valid input for player to input
//        //M
//        //Client: which terrority do you want to move ? -- app.java
//        // player:
//        // client: how many units do
//        //player: 10
//        //client : which terrority do you want to move to ? - app.
//        //
//
//    }

// terrorites - move - owned by this player // connected -- new class MoveActionChecker

