package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;

public class MainMenuScreen implements Screen {
    final FishinGame game;
    private Texture menuBG;

    public MainMenuScreen(final FishinGame game) {
        this.game = game;
        menuBG = new Texture("fishfishfishfish.jpeg");
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        //Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //Gdx.gl.glClearColor(1f,1f,1f,1f);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.viewport.apply();
        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);

        game.batch.begin();
        game.batch.draw(menuBG, 0, 0, game.viewport.getWorldWidth(), game.viewport.getWorldHeight());
        game.batch.end();

        // pressing space will switch to the game screen
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
