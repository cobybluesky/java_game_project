package io.github.javagame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.ArrayList;

//https://alohaeee.itch.io/fishing-assets for the fishin lady

public class GameScreen implements Screen {
    final FishinGame game;
    private Texture gameBg;
    private Texture fisher;
    private Texture fishtext;
    private Texture exclamationPoint;
    private Texture greenRect;
    private boolean fishingDisabled;
    private boolean instructionsVisible;
    private InventoryManager inventoryManager;

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
    double reelTimer = 1.8;

    String infoText = "Click to Cast";

    BitmapFont font = new BitmapFont(Gdx.files.internal("font/default-72.fnt"));

    public GameScreen(final FishinGame game) {
        this.game = game;
        gameBg = new Texture("kiddiepool2.png");
        fisher = new Texture("boatman2.png");
        fishtext = new Texture("fishtext.png");
        exclamationPoint = new Texture("exclamation.png");
        greenRect = new Texture("green_rectangle.jpeg");
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

        if (game.arrowHandler.getPendingFish() != null) {
            game.batch.draw(greenRect, 6, game.viewport.getWorldHeight()-2, 18, 2);
        }

        game.arrowHandler.drawArrows();
        game.arrowHandler.updateArrows(delta);

        //keeps camera units and drawing units consistent, while Gdx.graphics uses pixels instead or something. It's weird
        //game.batch.draw(gameBg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //maybe handle this logic elsewhere???if we have time for refactoring

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && !fishingDisabled) {
            System.out.println("Fish time");
            fisher = Texture("boatman.png");  /*May die due to privacy */
            // start timer
            fishDelay = 2 + (Math.random() * 4);// delay will be between 2 and 6 seconds
            isCast = true;
            instructionsVisible = false;
            System.out.println(fishDelay);
            fishingDisabled = true;
            infoText = "Awaiting fish...";
        }

        if (fishDelay > 0) {
            fishDelay -= delta;// delta - time between frames in seconds
        }

        if (fishDelay <= 0 && isCast) {
            System.out.println("fish caught");
            reelTimer = timeFrame;
            fishDelay = -1;
            isCast = false;
            fishHooked = true;
            infoText = "You hooked a fish! Right click to reel";
        }

        //detects if person clicks fast enough
        // MOVED THE SCORE DETECTION TO ARROWHANDLER
        // IT IS STILL THERE
        // JUST MOVED
        // ALSO I'M USING INPUTPROCESSOR NOW.
        if (fishHooked) {
            //System.out.println("this is running");
            reelTimer -= delta; //copying what caleb pulled earlier lol
            game.batch.draw(exclamationPoint, 16, 9, 5, 5);
            if (reelTimer >= 0 && Gdx.input.isButtonJustPressed(Input.Buttons.RIGHT)) {
                System.out.println("Successful reel in");
                fisher = Texture("boatman2.png");  /*May die due to privacy */
                fishHooked = false;
                Fish hookedFish = chooseRandomFish();
                infoText = "You caught a "+hookedFish.getType()+"!";
                //System.out.println("You caught a "+hookedFish.getType()+"!");
                game.arrowHandler.beginArrowSequence(hookedFish);
            } else if (reelTimer < 0) {
                infoText = "You lost the fish :(";
                System.out.println("U suck and didn't get it lol");
                fisher = Texture("boatman2.png");    /*May die due to privacy */
                fishHooked = false;
                fishingDisabled = false;
            }
        }
        if (game.arrowHandler.isPendingResult()) {
            if (game.arrowHandler.getsFish()) {
                inventoryManager.addFish(game.arrowHandler.getPendingFish());
            }
            infoText = "Accuracy: "+game.arrowHandler.getScore()*100+"%";
            game.arrowHandler.endArrowSequence();
            fishingDisabled = false;
        }
        // RENDER INVENTORY TEXT, HOPEFULLY
        game.batch.setProjectionMatrix(game.uiViewport.getCamera().combined);
        font.setColor(Color.BLACK);
        font.getData().setScale(0.3f);
        font.draw(game.batch, "Inventory", Gdx.graphics.getWidth()-170, Gdx.graphics.getHeight()-30);
        font.draw(game.batch, inventoryManager.getInventoryString(), Gdx.graphics.getWidth()-170, Gdx.graphics.getHeight()-70);
        font.draw(game.batch, infoText, 30, 30);
        game.batch.end();
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


