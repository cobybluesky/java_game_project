package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    // fish inventory is now a variable in game (game.fishInventory), since it should persist between screens
    //public ArrayList<Fish> fishInventory;
    private String [] fishTypes = {"Codfish","Salmon","Large Bass","Narwhal","Megaladon","Walnut","Plague","Dogfish"};


    //may change this to a dictionary(I forget what the java term is called for it) later so I can manually input
    //the fish and their chances of being caught. For now we should just stick with an equally random chance.`

    Fish walnut = new Fish("Walnut",5f,1f,10f,3f,4f,"fish/walnut.png");
    Fish narwhal = new Fish("Narwhal", 15f,3f,20f, 200.5f, 5f, "fish/narwhal.png");
    Fish plague = new Fish("Plague", 2f, 1f, 40f, 0f, 10f, "fish/plague.png");

    double fishDelay = -100;
    boolean isCast = false;
    boolean fishHooked = false;
    double timeFrame = 1.8;

    BitmapFont font = new BitmapFont(Gdx.files.internal("font/default-72.fnt"));

    public GameScreen(final FishinGame game) {
        this.game = game;
        gameBg = new Texture("kiddiepool2.png");
        fisher = new Texture("boatman.png");
        fishtext = new Texture("fishtext.png");
        exclamationPoint = new Texture("exclamation.png");
        instructionsVisible = true; //sets the variable, tho I'm not sure if it repeats over and over. Hope not.
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        //game.viewport.apply();

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

        //maybe handle this logic elsewhere???if we have time for refactoring
        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !fishingDisabled) {
            System.out.println("Fish time");
            // start timer
            fishDelay = 2 + (Math.random() * 4);// delay will be between 2 and 6 seconds
            isCast = true;
            instructionsVisible = false;
            System.out.println(fishDelay);
            fishingDisabled = true;
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
        for (int i = game.arrowHandler.getArrowArray().size()-1; i>=0; i--) {

                if (game.arrowHandler.getArrowArray().get(i).getY() > game.viewport.getWorldHeight()-5 && (game.arrowHandler.getArrowArray().get(i).getY() < game.viewport.getWorldHeight()+1)) {
                    System.out.println("this happened ig"); //checks if it's within these coords
                    for(int n = 0; n<=3; n++) {
                        System.out.println("checkin"); //iterates through all possible keys the player pressed, and if they match up with the arrows column
                        if (Gdx.input.isKeyPressed(game.arrowHandler.getKeys()[n]) && game.arrowHandler.getArrowArray().get(i).getX() == game.arrowHandler.getColumns()[n] ){
                            System.out.println("Hooray!!!!!");
                            game.arrowHandler.getArrowArray().remove(game.arrowHandler.getArrowArray().get(i));

                                
                        }
                        else {
                                System.out.println("NBOOOO");
                            }
                         }
                    }
                }
        if (fishHooked) {
            //System.out.println("this is running");
            timeFrame -= delta; //copying what caleb pulled earlier lol
            game.batch.draw(exclamationPoint, 16, 9, 5, 5);
            if (timeFrame >= 0 && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                System.out.println("Successful reel in");
                fishHooked = false;
                // 4 testing
                game.arrowHandler.beginArrowSequence(4.0f, 1.0f, 10);
                
                fishingDisabled = false;// this will need to be moved/changed eventually
                System.out.println("this happens too");
                
                
                //arrow functionality (checks if it crosses a line)


                 





            } else if (timeFrame < 0) {
                System.out.println("U suck and didn't get it lol");
                fishHooked = false;
                fishingDisabled = false;
            }
        }
        // RENDER INVENTORY TEXT, HOPEFULLY
        game.batch.setProjectionMatrix(game.uiViewport.getCamera().combined);
        font.setColor(Color.BLACK);
        font.getData().setScale(0.3f);
        font.draw(game.batch, "Inventory", Gdx.graphics.getWidth()-100, Gdx.graphics.getHeight()-30);
        game.batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.R)) {    //temporary access to inventory. We'll hopefully change it to button later
            game.setScreen(new InventoryScreen(game));
            dispose();
        }
    }

    @Override
    public void resize(int width, int height) {
        game.viewport.update(width, height, true);
        game.uiViewport.update(width,height, true);
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


