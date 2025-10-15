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



    Fish walnut = new Fish("Walnut",5f,1f,10f,30.2f,4f,"assets\\fish\\walnut.png");
    Fish narwhal = new Fish("Narwhal",2f,2f,5f,80.2f,4f,"assets\\fish\\narwhal.png");

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
    }

    @Override
    public void render(float delta) {

        ScreenUtils.clear(Color.WHITE);
        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.batch.draw(walnut.getTexture(),0,0,30,30);
        game.batch.end();

        
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
