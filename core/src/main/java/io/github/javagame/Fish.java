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






    public Fish(String initType, float initSpeed, float initDelay, float initSequences, float initLength, String initTexture) {
        type = initType;
        speed = initSpeed;
        delay = initDelay;
        sequences = initSequences;
        length = initLength;
        texture = new Texture(Gdx.files.internal(initTexture));

    }

    public Texture getTexture() {
        return texture;
    }


}   
