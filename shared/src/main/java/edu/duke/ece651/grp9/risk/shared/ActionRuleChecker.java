package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;

public class ActionRuleChecker {
  public String checkLoseAction( String input){
    if(input.length() != 1){
            return "the input length is invalid, please enter again!";
        }
        else if(!(input.charAt(0) == 'Q' || input.charAt(0) == 'q' || input.charAt(0) == 'C' || input.charAt(0) == 'c')){
            return "the input character is invalid, please enter again!";
        }
        return null;
  }
  
    public String checkAction( String input){
        if(input.length() != 1){
            return "the input length is invalid, please enter again!";
        }
        else if(!(input.charAt(0) == 'M' || input.charAt(0) == 'A' || input.charAt(0) == 'D' || input.charAt(0) == 'm' || input.charAt(0) == 'a' || input.charAt(0) == 'd' )){
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

    public String checkUnit(String input, Player player){
        char[] tmp = input.toCharArray();
        //count the number of blanks here
        int count = 0 ;
        //calculate the total number of units
        int total = 0;
        for(int i = 0; i < tmp.length; ++i) {
            //for blanks
            if (!Character.isDigit(tmp[i])) {
                // 10  15
                if (i > 0 && i < tmp.length - 1 && (!Character.isDigit(tmp[i - 1]) || !Character.isDigit(tmp[i + 1]))) {
                    return "The input is not valid, please enter again";
                }
                // 10 5 15
                //10 5 15

                else if (i == 0 || i == tmp.length - 1) {
                    return "The input is not valid, please enter again";
                }
                count++;
            }
        }
            if(count != player.getTerritoryList().size() -1){
                return "The input is not valid, please enter again";
            }

            int k = 0;
            int digit = 0;
            while(k < tmp.length){

                if(Character.isDigit(tmp[k])){
                    digit =0 ;
                    while(k < tmp.length && Character.isDigit(tmp[k])){
                    digit = digit*10 + Character. getNumericValue(tmp[k]);
                    k++;
                }
                }
                total += digit;
                k++;
            }
            if(total != 30){
                return "The input is not valid, please enter again";
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

