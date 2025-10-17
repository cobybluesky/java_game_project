package io.github.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import com.badlogic.gdx.Input;

public class ArrowHandler {
    private final FishinGame game;
    // arrow could be a class at this point but uhhhhhhh
    private Sprite[] arrowDirections = {
        new Sprite(new Texture("arrows/colour_left.png")),
        new Sprite(new Texture("arrows/c_up.png")),
        new Sprite(new Texture("arrows/c_down.png")),
        new Sprite(new Texture("arrows/colour_right.png"))
    };
    private float[] arrowColumns = {7, 10, 13, 16};
    private Integer[] arrowKeyCodes = {Input.Keys.LEFT, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.RIGHT};

    private Integer[] keyPresses = {
        Input.Keys.LEFT,Input.Keys.UP,Input.Keys.DOWN,Input.Keys.RIGHT
    };



    private float arrowSpeed;
    private float arrowDelay;// time between arrows
    private float arrowTimer;
    private float arrowChance = 0.95f;// chance an arrow actually spawns (possibility of blank rows)
    private int sequenceArrows; // number of rows (w or w/o arrows) in the sequence
    private int arrowCount = -1;
    private boolean spawnArrows = false;
    private Fish pendingFish;
    private int points = 0;
    private boolean pendingResult = false;

    static Random random = new Random();

    // arrows are sprites
    private ArrayList<Sprite> visibleArrows = new ArrayList<>();

    public ArrowHandler(FishinGame game) {
        this.game = game;
    }


    public float[] getColumns() {

        return(arrowColumns);
    }

    public ArrayList<Sprite> getArrowArray() {
        return visibleArrows;
    }

    public Integer[] getKeys() {
        return keyPresses;
    }


    // for creating an arrow with random direction
    public void createArrow() {
        int direction = random.nextInt(4);
        Sprite newArrow = new Sprite(arrowDirections[direction]);
        newArrow.setY(0);
        // move arrow to corresponding column
        newArrow.setX(arrowColumns[direction]);
        newArrow.setSize(8,6);
        visibleArrows.add(newArrow);
    }
    // for creating an arrow with specific direction
    public void createArrow(int direction) {
        Sprite newArrow = new Sprite(arrowDirections[direction]);
        newArrow.setY(0);
        // move arrow to corresponding column
        newArrow.setX(arrowColumns[direction]);
        newArrow.setSize(8,6);
        visibleArrows.add(newArrow);
    }

    public void updateArrows(float delta) {
        // eventually add a chance for multiple arrows on one line
        // currently random spawning
        if (spawnArrows) {
            arrowTimer -= delta;
            if (arrowTimer <= 0) {
                // random chance for arrow not to appear (guaranteed on first arrow)
                if (Math.random() < arrowChance || arrowCount == sequenceArrows) {
                    createArrow();
                    arrowCount += 1;
                }
                arrowTimer = arrowDelay;
            }
        }
        // iterate backwards so removing doesn't mess it up
        for (int i = visibleArrows.size() - 1; i >= 0; i--) {
            Sprite arrow = visibleArrows.get(i);
            arrow.setY(arrow.getY() + delta * arrowSpeed);
            // arrows are deleted once they reach top of the screen
            if (arrow.getY() > game.viewport.getWorldHeight()) {
                visibleArrows.remove(i);
                // player did not click in time... no points for you
            }
            if (arrow.getY() > game.viewport.getWorldHeight()-5 && Arrays.asList(arrowKeyCodes).contains(game.currentKey)) {
                int index = Arrays.asList(arrowKeyCodes).indexOf(game.currentKey);

                if (arrow.getX() == arrowColumns[index]) {
                    visibleArrows.remove(i);
                    points++;
                }
            }

        }
        if (arrowCount >= sequenceArrows) {
            // end sequence, kind of
            spawnArrows = false;
            if (visibleArrows.isEmpty()) { pendingResult = true; }
        }
    }

    public void drawArrows() {
        for (Sprite arrow : visibleArrows) {
            arrow.draw(game.batch);
        }
    }

    public void beginArrowSequence(Fish pendingFish) {
        this.pendingFish = pendingFish;
        arrowDelay = pendingFish.getArrowDelay();
        arrowSpeed = pendingFish.getArrowSpeed();
        sequenceArrows = pendingFish.getSequenceLen();
        arrowTimer = arrowDelay;
        spawnArrows = true;
    }

    public boolean isSpawningArrows() {
        return spawnArrows;
    }

    public Fish getPendingFish() {
        return pendingFish;
    }

    public boolean isPendingResult() {
        return pendingResult;
    }

    public boolean getsFish() {
        System.out.println(points/(float) sequenceArrows);
        return points/(float) sequenceArrows > 0.8;
    }

    public void endArrowSequence() {
        pendingResult = false;
        pendingFish = null;
        points = 0;
        arrowCount = -1;
    }
}
