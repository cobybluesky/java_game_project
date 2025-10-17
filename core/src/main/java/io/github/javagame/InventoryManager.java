package io.github.javagame;

import java.util.HashMap;

public class InventoryManager {
    private HashMap<Fish, Integer> inventory = new HashMap<Fish, Integer>();

    public InventoryManager(Fish[] allFish) {
        // initialize inventory with all fish at zero
        for (Fish fish : allFish) {
            this.inventory.put(fish, 0);
        }
    }

    // returns string to write to the screen
    public String getInventoryString() {
        String output = "";
        for (Fish fish : inventory.keySet()) {
            output += fish.getType()+" x"+inventory.get(fish)+"\n";
        }
        return output;
    }
    // add 1 to the # of given fish
    public void addFish(Fish fish) {
        inventory.put(fish, inventory.get(fish) + 1);
    }
}
