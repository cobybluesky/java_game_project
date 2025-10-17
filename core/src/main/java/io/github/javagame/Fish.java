package io.github.javagame;
import com.badlogic.gdx.graphics.Texture;

public class Fish {
    private String type;
    private float arrowSpeed;
    private float arrowDelay;
    private float sequenceLen;
    private float length;
    private Texture texture;
    private float chanceweight;

    // using "this." is more clean and pretty imo
    public Fish(String type, float arrowSpeed, float arrowDelay, float sequenceLen, float length, float chanceweight, String texture) {
        this.type = type;
        this.arrowSpeed = arrowSpeed;
        this.arrowDelay = arrowDelay;
        this.sequenceLen = sequenceLen;
        this.length = length;
        this.texture = new Texture(texture);// texture is the file path as a string
        this.chanceweight = chanceweight;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getType() {
        return type;
    }

    public float getArrowSpeed() {
        return arrowSpeed;
    }

    public float getArrowDelay() {
        return arrowDelay;
    }
    public float getSequenceLen() {
        return sequenceLen;
    }

    public float getLength() {
        return length;
    }

    public float getChanceWeight() {
        return chanceweight;
    }


}
