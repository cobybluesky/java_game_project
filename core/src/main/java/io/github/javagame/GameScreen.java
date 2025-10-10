package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

//https://alohaeee.itch.io/fishing-assets for the fishin lady

public class GameScreen implements Screen {
    final FishinGame game;
    private Texture gameBg;
    private Texture fisher;
    private Texture fishtext;
    private boolean instructionsVisible;

    double fishDelay = -100;
    boolean isCast = false;

    public GameScreen(final FishinGame game) {
        this.game = game;
        gameBg = new Texture("kiddiepool2.png");
        fisher = new Texture("Fisher000.png");
        fishtext = new Texture("fishtext.png");
        instructionsVisible = true; //sets the variable, tho I'm not sure if it repeats over and over. Hope not.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(gameBg, 0, 0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(fisher,10,10,4,4);

        if (instructionsVisible) {
            game.batch.draw(fishtext,0,15,15,3);
        }

        //keeps camera units and drawing units consistent, while Gdx.graphics uses pixels instead or something. It's weird
        //game.batch.draw(gameBg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !isCast) {
            System.out.println("Fish time");
            // start timer
            fishDelay = 2 + (Math.random() * 4);// delay will be between 2 and 6 seconds
            isCast = true;
            instructionsVisible = false;
            System.out.println(fishDelay);
        }
        if (fishDelay > 0) {
            fishDelay -= delta;// delta - time between frames in seconds
        }
        if (fishDelay <= 0 && isCast) {
            System.out.println("fish caught");
            fishDelay = -1;
            isCast = false;
            // get some fish
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
