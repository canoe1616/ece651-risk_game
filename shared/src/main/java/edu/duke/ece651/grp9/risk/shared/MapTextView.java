package edu.duke.ece651.grp9.risk.shared;

import java.util.HashSet;

public class MapTextView {
    private final Map toDisplay;

    public MapTextView(Map toDisplay) {
        this.toDisplay = toDisplay;
        // todo: check correctness?
    }

    // this method is to get all neighboring territories
    String getAdjacencyText(Territory ter) {
        String ans = "";
        for (Territory t: ter.getNeighbors()) {
            ans += t.getName() + ", ";
        }
        return ans.substring(0, ans.length() - 2);
    }

    public String displayGameState(Player player) {
        String ans = "";
        // display the territory's unit information
        for (Player p: toDisplay.getPlayer()) {
            ans += p.getName() + " Player:\n" +
                    "----------------------------------\n";
            for (Territory t: p.getTerritoryList()) {
                ans += t.getUnit() + " units in " + t.getName();
                ans += "(next to: " + this.getAdjacencyText(t) + ")\n";
            }
            ans += "\n";
        }

        ans += "You are the " + player.getName() + " Player, what would you like to do?\n" +
                " (M)ove\n" + " (a)ttack\n" + " (D)one\n";
        return ans;
    }


}
