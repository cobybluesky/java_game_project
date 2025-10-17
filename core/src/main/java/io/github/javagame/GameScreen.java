package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

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
    private InventoryManager inventoryManager;

    //may change this to a dictionary(I forget what the java term is called for it) later so I can manually input
    //the fish and their chances of being caught. For now we should just stick with an equally random chance.`

    Fish walnut = new Fish("Walnut",5f,1f,10,3f,5f,"fish/walnut.png");
    Fish narwhal = new Fish("Narwhal", 15f,3f,20, 200.5f, 3f, "fish/narwhal.png");
    Fish plague = new Fish("Plague", 2f, 1f, 40, 0f, 2f, "fish/plague.png");
    Fish cod = new Fish("Cod",3.5f,1.5f,8,15f,4f,"fish/placeholder.png");
    Fish bass = new Fish("Bass",6f,2f,15,30f,3f,"fish/placeholder.png");
    Fish megaladon = new Fish("Megaladon",20f,2f,35,1200f,0.1f,"fish/placeholder.png");

    private Fish[] allFish = {walnut, narwhal, plague,cod,bass,megaladon};
    private double totalWeight = 0.0;
    private ArrayList<Double> cumulativeWeights = new ArrayList<Double>();

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
        inventoryManager = new InventoryManager(allFish);
        // for weighted choosing of the fish
        for (Fish fish : allFish) {
            totalWeight += fish.getChanceWeight();
            cumulativeWeights.add(totalWeight);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);
        //game.viewport.apply();

        game.batch.setProjectionMatrix(game.viewport.getCamera().combined);
        game.batch.begin();
        game.batch.draw(gameBg, 0, 0,game.viewport.getWorldWidth(),game.viewport.getWorldHeight());
        game.batch.draw(fisher,10,5,10,10);

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

                    for(int n = 0; n<=3; n++) {
                        //iterates through all possible keys the player pressed, and if they match up with the arrows column
                        if (Gdx.input.isKeyJustPressed(game.arrowHandler.getKeys()[n]) && game.arrowHandler.getArrowArray().get(i).getX() == game.arrowHandler.getColumns()[n] ){
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
                Fish hookedFish = chooseRandomFish();
                System.out.println("You caught a "+hookedFish.getType()+"!");
                game.arrowHandler.beginArrowSequence(hookedFish.getArrowSpeed(), hookedFish.getArrowDelay(), hookedFish.getSequenceLen());
                // once the sequence is finished, the player will get the fish or not depending on score
                fishingDisabled = false;// this will need to be moved/changed eventually (once sequence ends)

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
        font.draw(game.batch, inventoryManager.getInventoryString(), Gdx.graphics.getWidth()-130, Gdx.graphics.getHeight()-70);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            inventoryManager.addFish(chooseRandomFish());
        }
    }

    public Fish chooseRandomFish() {
        // choose a random fish with weighted probability
        double random = Math.random() * totalWeight;
        for (int i = 0; i < allFish.length; i++) {
            if (cumulativeWeights.get(i) >= random) {
                return allFish[i];
            }
        }
        // should not happen.
        return null;
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


