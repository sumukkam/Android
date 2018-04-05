package com.msushanth.mazedemo;

/**
 * Created by Sushanth on 9/21/16.
 */

public class Coordinate {
    float x;
    float y;
    boolean playable = false;


    public Coordinate(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate other) {
        this.x = other.x;
        this.y = other.y;
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPlayable(boolean play) {
        playable = play;
    }

    public boolean getPlayable() {
         return playable;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinate)) {
            return false;
        }
        Coordinate other = (Coordinate) o;
        return this.getX() == other.getX() && this.getY() == other.getY();
    }

    @Override
    public String toString() {
        return "X: " + getX() + ", Y: " + getY();
    }
}
