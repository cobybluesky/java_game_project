package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

//https://alohaeee.itch.io/fishing-assets for the fishin lady

public class GameScreen implements Screen {
    final FishinGame game;
    private Texture gameBg;
    private Texture fisher;
    private Texture fishtext;
    private Texture exclamationPoint;
    private boolean fishingDisabled;
    private boolean instructionsVisible;

    private String [] fishTypes = {"Codfish","Salmon","Large Bass","Narwhal","Megaladon","Walnut","Plague","Dogfish"};
    public ArrayList<String> fishInventory;
    //may change this to a dictionary(I forget what the java term is called for it) later so I can manually input
    //the fish and their chances of being caught. For now we should just stick with an equally random chance.`

    double fishDelay = -100;
    boolean isCast = false;
    boolean fishHooked = false;
    double timeFrame = 1.8;

    public GameScreen(final FishinGame game) {
        this.game = game;
        gameBg = new Texture("kiddiepool2.png");
        fisher = new Texture("Fisher000.png");
        fishtext = new Texture("fishtext.png");
        exclamationPoint = new Texture("exclamation.png");
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

        game.arrowHandler.drawArrows();
        game.arrowHandler.updateArrows(delta);

        //keeps camera units and drawing units consistent, while Gdx.graphics uses pixels instead or something. It's weird
        //game.batch.draw(gameBg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

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
            fishHooked = true;
            // get some fish
        }

        //detects if person clicks fast enough 

        if (fishHooked == true) {
            //System.out.println("this is running");
            timeFrame -= delta; //copying what caleb pulled earlier lol
            game.batch.draw(exclamationPoint,16,9,5,5);

            if (timeFrame >= 0 && Gdx.input.isButtonPressed(Input.Buttons.RIGHT))
            {
                System.out.println("Successful reel in");
                fishHooked = false;
                // 4 testing
                game.arrowHandler.beginArrowSequence(3.0f, 1.0f, 10);
                fishingDisabled = false;// this will need to be moved/changed eventually
            }

            else if (timeFrame < 0) {
                System.out.println("U suck and didn't get it lol");
                fishHooked = false;
            }

        }

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
