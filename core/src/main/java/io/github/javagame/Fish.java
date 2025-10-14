package io.github.javagame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;

public class Fish {
    private String type;
    private float speed;
    private float delay;
    private float sequences;
    private float length;
    private Texture texture;
    private float weight;






    public Fish(String initType, float initSpeed, float initDelay, float initSequences, float initLength, float initWeight, String initTexture) {
        type = initType;
        speed = initSpeed;
        delay = initDelay;
        sequences = initSequences;
        length = initLength;
        texture = new Texture(Gdx.files.internal(initTexture));
        weight = initWeight;

    }

    public Texture getTexture() {
        return texture;
    }

    public String getType() {
        return type;
    }

    public float getSpeed() {
        return speed;
    }
    
    public float getDelay() {
        return delay;
    }
    public float getSequences() {

        return sequences;
    }

    public float getLength() {
        return length;
    }

    public float getWeight() {
        return weight;
    }


}   
