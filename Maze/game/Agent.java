package game;

import java.awt.Image;

public class Agent {

    protected int currentLocation;
    protected int previousLocation;
    protected final Image image;

    protected Agent(int start, Image image){
        currentLocation = start;
        previousLocation = start;
        this.image = image;
    }

    protected void move(int newLocation){
        previousLocation = currentLocation;
        currentLocation = newLocation;
    }
}
