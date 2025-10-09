package io.github.javagame;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;

public class GameScreen implements Screen {
    final FishinGame game;
    private Texture gameBg;

    public GameScreen(final FishinGame game) {
        this.game = game;
        gameBg = new Texture("kiddiepool.jpg");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        game.viewport.apply();

        game.batch.begin();
        game.batch.draw(gameBg, 0, 0,Gdx.graphics.getHeight(),Gdx.graphics.getWidth());
        game.batch.end();
        //System.out.println(gdx.graphics.getHeight());
        //System.out.println(gdx.graphics.getWidth());
        
        
    }



    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
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
