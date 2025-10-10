package io.github.javagame;

import com.badlogic.gdx.Gdx;
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
    private boolean enter_pressed;
 

    public GameScreen(final FishinGame game) {
        this.game = game;
        gameBg = new Texture("kiddiepool2.png");
        fisher = new Texture("Fisher000.png");
        fishtext = new Texture("fishtext.png");
        enter_pressed = false; //sets the variable, tho I'm not sure if it repeats over and over. Hope not.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
    //sets the s


        game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(gameBg, 0, 0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(fisher,10,10,4,4);
        //game.batch.draw(fishtext,0,15,8,4);

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            System.out.println("pressed!");
            enter_pressed = true;

        }
        else if (enter_pressed == false) {
//game.bath.draw;
            game.batch.draw(fishtext,0,15,15,3);
            //System.out.println("Not working :c");
        }

        //keeps camera units and drawing units consistent, while Gdx.graphics uses pixels instead or something. It's weird
        //game.batch.draw(gameBg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.batch.end();


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
