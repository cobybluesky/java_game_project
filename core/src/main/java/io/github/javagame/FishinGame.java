package io.github.javagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FishinGame extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;
    private Texture image;
    private Texture fishingRod;

    @Override
    public void create() {
        batch = new SpriteBatch();
        // one "meter" will be 16 pixels (window size 640 x 480)
        viewport = new FitViewport(40,30);
        image = new Texture("fishfishfishfish.jpeg");
        fishingRod = new Texture("fishingrod.png");

        this.setScreen(new MainMenuScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // true centers the camera
    }

    private void input () {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println("SPACE");
        }
    }
}
