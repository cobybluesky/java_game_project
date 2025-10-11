package io.github.javagame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
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
    private OrthographicCamera camera;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        batch = new SpriteBatch();
        // one "meter" will be 32 pixels (window size 960 x 640)
        viewport = new FitViewport(30,20,camera); //does something to set up proportion based on the actual screen size 
        image = new Texture("fishfishfishfish.jpeg");
        //fishingRod = new Texture("fishingrod.png");

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
    public void resize(int width, int height) { //automatically calls this method when screen is resized
        viewport.update(width, height, true); // true centers the camera
    }

    private void input () {

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            System.out.println("SPACE");
        }
        //if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {


        
    }
}
