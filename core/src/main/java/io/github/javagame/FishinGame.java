package io.github.javagame;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class FishinGame extends Game {
    public SpriteBatch batch;
    public FitViewport viewport;
    public ArrowHandler arrowHandler;
    //public InventoryManager inventoryManager;
    public ArrayList<Fish> fishInventory;
    private Texture image;
    private Texture fishingRod;
    private OrthographicCamera gameCamera;
    private OrthographicCamera uiCamera;
    public FitViewport uiViewport;
    public int currentKey;

    @Override
    public void create() {
        gameCamera = new OrthographicCamera();
        uiCamera = new OrthographicCamera();
        batch = new SpriteBatch();
        fishInventory = new ArrayList<>();
        arrowHandler = new ArrowHandler(this);
        // one "meter" will be 32 pixels (window size 960 x 640)
        viewport = new FitViewport(30,20,gameCamera); //does something to set up proportion based on the actual screen size
        uiViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), uiCamera);
        image = new Texture("fishfishfishfish.jpeg");
        //fishingRod = new Texture("fishingrod.png");
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                currentKey = keycode;
                return true;
            }
            public boolean keyUp(int keycode) {
                currentKey = -1;// no one cares
                return true;
            }
        });

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

}
