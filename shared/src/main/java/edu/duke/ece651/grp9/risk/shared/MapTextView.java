package edu.duke.ece651.grp9.risk.shared;

import java.util.*;

public class MapTextView {
    private final Map toDisplay;

    public MapTextView(Map toDisplay) {
        this.toDisplay = toDisplay;
        // todo: check correctness?
    }

    /**
     * given a set of territories, return a set of territory name in alphabetical order.
     * @param ter is a set of territories.
     * @return sorted territory names
     */
    private TreeSet<String> getSortedTerr(HashSet<Territory> ter) {
        TreeSet<String> myTreeSet = new TreeSet<>();
        for (Territory t : ter) {
            myTreeSet.add(t.getName());
        }
        return myTreeSet;
    }

    /**
     * given a set of players, return the a set of player name in alphabetical order.
     * @param players is a set of players.
     * @return sorted territory names
     */
    private TreeSet<String> getSortedPlayerName(HashSet<Player> players) {
        TreeSet<String> sortedPlayerNames = new TreeSet<>();
        for (Player player: players) {
            sortedPlayerNames.add(player.getName());
        }
        return sortedPlayerNames;
    }

    /**
     * This method is to display game status before playing the game each round
     * @param player the player that needs to display info
     * @return a string of game status info
     */
    public String displayGameState(Player winner, Player player) {
        String ans = "";
        if (winner != null) {
            ans = "Game Over! Player " + winner.getName() + " wins the game!";
        } else {
            if (player.isLose()) {
                // if player lose the game, she can choose to quit to continue watch
                ans = "Player " + player.getName() + ", you lose the game!"
                        + " What would you like to do?\n" +
                        " (Q)uit\n" + " (C)ontinue watching game\n";
            } else {
                // options for player to play the game
                for (String playerName : getSortedPlayerName(toDisplay.getPlayer())) {
                    Player p = toDisplay.findPlayer(playerName);
                    ans += playerName + " Player:\n" +
                            "----------------------------------\n";
                    for (String terrName : getSortedTerr(p.getTerritoryList())) {
                        Territory ter = toDisplay.findTerritory(terrName);
                        ans += ter.getUnit() + " units in " + terrName + "(next to: ";
                        for (String s : getSortedTerr(ter.getNeighbors())) {
                            ans += s + ", ";
                        }
                        ans = ans.substring(0, ans.length() - 2);
                        ans += ")\n";
                    }
                    ans += "\n";
                }
                ans += "You are the " + player.getName() + " Player, what would you like to do?\n" +
                        " (M)ove\n" + " (A)ttack\n" + " (D)one\n";
            }
        }
        return ans;
    }


}
