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
    
 

    public InventoryScreen(final FishinGame game) {
        this.game = game;

    }



    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        Fish walnut = new Fish("Walnut",5f,1f,10f,30.2f,"walnut.png");
        game.batch.begin();
        game.batch.draw(walnut.getTexture(),0,0,2,2);

        
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
