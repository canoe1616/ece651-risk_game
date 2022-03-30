package edu.duke.ece651.grp9.risk.shared;

public class FoodRuleChecker extends RuleChecker {

    /**
     * Adds the next Action rule to our list
     *
     * @param next Action rule in list
     */
    public FoodRuleChecker(RuleChecker next) {
        super(next);
    }

    /**
     * Performs mockActions to make sure Territories have enough food to move or attack
     *
     * @param action Action we are checking rules against
     * @return String description of error if invalid move, or null if okay
     */
    @Override
    protected String checkMyRule(Action action) {
        int foodConsume = action.computeCost();
        Player player = action.getPlayer();
        if (player.getFoodQuantity() - foodConsume < 0) {
            return "This action is invalid: do not have enough food resources for orders.";
        }
        // TODO mock resources
//        player.mockActions(action.getDestination(), action.getNumUnits(),
//                action.getUnitLevel()); //EVOLUTION 2

        return null;
    }

}
