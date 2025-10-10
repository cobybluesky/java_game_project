package io.github.javagame;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class ArrowHandler {
    FishinGame game;
    // arrows are sprites
    private ArrayList<Sprite> visibleArrows;

    public ArrowHandler(FishinGame game) {
        this.game = game;
    }

    public void createArrow() {
        Sprite newArrow = new Sprite();
        newArrow.setY(0);
        // move arrow to random position along bottom of the screen
        newArrow.setX((float) Math.random() * game.viewport.getWorldWidth());
        visibleArrows.add(newArrow);
    }

    public void moveArrows(float delta) {
        for (Sprite arrow : visibleArrows) {
            arrow.setY(arrow.getY() - delta * 4);
        }
    }

    public void drawArrows(SpriteBatch batch) {
        for (Sprite arrow : visibleArrows) {
            arrow.draw(batch);
        }
    }
}
