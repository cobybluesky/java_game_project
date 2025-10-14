package io.github.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;
import java.util.Random;

public class ArrowHandler {
    private final FishinGame game;
    private Sprite[] arrowDirections = {
        new Sprite(new Texture("colour_left.png")),
        new Sprite(new Texture("c_up.png")),
        new Sprite(new Texture("c_down.png")),
        new Sprite(new Texture("colour_right.png"))
    };
    private float[] arrowColumns = {7, 10, 13, 16};
    private float arrowSpeed;
    private float arrowDelay;// time between arrows
    private float arrowTimer;
    private float arrowChance = 0.8f;// chance an arrow actually spawns (possibility of blank rows)
    private int sequenceRows; // number of rows (w or w/o arrows) in the sequence
    private int rowCount;
    private boolean spawnArrows = false;

    static Random random = new Random();

    // arrows are sprites
    private ArrayList<Sprite> visibleArrows = new ArrayList<>();

    public ArrowHandler(FishinGame game) {
        this.game = game;
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
                if (Math.random() < arrowChance || rowCount == sequenceRows) { createArrow(); }
                arrowTimer = arrowDelay;
                rowCount += 1;
            }
        }
        // iterate backwards so removing doesn't mess it up
        for (int i = visibleArrows.size() - 1; i >= 0; i--) {
            Sprite arrow = visibleArrows.get(i);
            arrow.setY(arrow.getY() + delta * arrowSpeed);
            // arrows are deleted once they reach top of the screen
            if (arrow.getY() > game.viewport.getWorldHeight()) {
                visibleArrows.remove(i);
                // player did not click in time
            }
        }
        if (rowCount >= sequenceRows) {
            // end sequence
            spawnArrows = false;
        }
    }

    public void drawArrows() {
        for (Sprite arrow : visibleArrows) {
            arrow.draw(game.batch);
        }
    }

    public void beginArrowSequence(float speed, float delay, int sequenceLen) {
        arrowDelay = delay;
        arrowSpeed = speed;
        sequenceRows = sequenceLen;
        arrowTimer = arrowDelay;
        spawnArrows = true;
    }
}
