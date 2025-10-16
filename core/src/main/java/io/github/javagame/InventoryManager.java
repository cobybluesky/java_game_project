package io.github.javagame;

import java.util.HashMap;

public class InventoryManager {
    FishinGame game;
    HashMap<Fish, Integer> inventory = new HashMap<Fish, Integer>();

    public InventoryManager(FishinGame game, Fish[] allFish) {
        this.game = game;
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
}
