package io.github.javagame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

public class ArrowHandler {
    private final FishinGame game;
    private final Texture upArrowTexture;
    private final Texture downArrowTexture;
    private final Texture leftArrowTexture;
    private final Texture rightArrowTexture;
    public float arrowSpeed;
    public double arrowDelay;// time between arrows

    // arrows are sprites
    private ArrayList<Sprite> visibleArrows = new ArrayList<>();

    public ArrowHandler(FishinGame game) {
        this.game = game;
        upArrowTexture = new Texture("c_up.png");
        downArrowTexture = new Texture("c_down.png");
        leftArrowTexture = new Texture("colour_left.png");
        rightArrowTexture = new Texture("colour_right.png");
        arrowSpeed = 4.0f;
    }

    public void createArrow() {
        Sprite newArrow = new Sprite(upArrowTexture);
        newArrow.setY(0);
        // move arrow to random position along bottom of the screen
        newArrow.setX((float) Math.random() * game.viewport.getWorldWidth());
        newArrow.setSize(8,6);
        visibleArrows.add(newArrow);
    }

    public void moveArrows(float delta) {
        for (int i = visibleArrows.size() - 1; i >= 0; i--) {
            Sprite arrow = visibleArrows.get(i);
            arrow.setY(arrow.getY() + delta * arrowSpeed);
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
