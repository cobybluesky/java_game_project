package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import java.util.ArrayList;
import com.badlogic.gdx.graphics.GL20;


public class InventoryScreen implements Screen {
    
    final FishinGame game;
    
    String type;
    double rarity;
    double length;
    Texture sprite; 

    public InventoryScreen(final FishinGame game) {
        this.game = game;

    }



    public void Fish(String initType, double initRarity, double initLength,Texture initSprite) {
        type = initType;
        rarity = initRarity;
        length = initLength;

    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();

        

    }

        @Override
    public void show() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

}
