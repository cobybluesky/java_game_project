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
    public float arrowSpeed = 4.0f;
    public float arrowDelay = 0.5f;// time between arrows

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

    public void moveArrows(float delta) {
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
    }

    public void drawArrows() {
        for (Sprite arrow : visibleArrows) {
            arrow.draw(game.batch);
        }
    }
}
