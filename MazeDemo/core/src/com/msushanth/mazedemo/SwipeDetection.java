package com.msushanth.mazedemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;


/**
 * Created by chris on 9/21/2016.
 */

/*
public class SwipeDetection implements GestureDetector.GestureListener {

    Coordinate playerLocation;

    public void setPlayerLocation(Coordinate coordinate) {
        playerLocation = new Coordinate(coordinate);
    }


*/







    /*
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        if (Math.abs(x - playerLocation.getX()) >= Math.abs(y - playerLocation.getY())) {
            if (x < playerLocation.getX()) {
                MazeDemo.personDir.setDirection(-1);
            }
            else {
                MazeDemo.personDir.setDirection(1);
            }
        }
        else {
            if (y < playerLocation.getY()) {
                MazeDemo.personDir.setDirection(2);
            }
            else if (y > playerLocation.getY()) {
                MazeDemo.personDir.setDirection(-2);
            }
        }

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {

        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
    *//*
        int screenWidth = Gdx.graphics.getWidth();
        int screenHeight = Gdx.graphics.getHeight();

        if (Math.abs(velocityX) > Math.abs(velocityY)) {
            if (velocityX > 0) {
                Gdx.app.log("hi", "right");
                MazeDemo.personDir.setDirection(1);
            }
            else {
                Gdx.app.log("hi", "left");
                MazeDemo.personDir.setDirection(-1);
            }
        }
        else {
            if (velocityY > 0) {
                Gdx.app.log("hi", "down");
                MazeDemo.personDir.setDirection(-2);
            }
            else {
                Gdx.app.log("hi", "up");
                MazeDemo.personDir.setDirection(2);
            }

        }

        *//*

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {

        if (Math.abs(x - playerLocation.getX()) >= Math.abs(y - playerLocation.getY())) {
            if (x < playerLocation.getX()) {
                MazeDemo.personDir.setDirection(-1);
            }
            else {
                MazeDemo.personDir.setDirection(1);
            }
        }
        else {
            if (y < playerLocation.getY()) {
                MazeDemo.personDir.setDirection(2);
            }
            else if (y > playerLocation.getY()) {
                MazeDemo.personDir.setDirection(-2);
            }
        }

        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {

        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){

        return false;
    }
    @Override
    public void pinchStop () {
    }
}

*/






















/*package com.msushanth.mazedemo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
*/

/**
 * Created by chris on 9/21/2016.
 */

public class SwipeDetection implements GestureDetector.GestureListener {

    Coordinate playerLocation;

    float screenWidth = Gdx.graphics.getWidth();
    float screenHeight = Gdx.graphics.getHeight();

    public void setPlayerLocation(Coordinate coordinate) {
        playerLocation = new Coordinate(coordinate);
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {

        //Gdx.app.log("Touch Location", "x: " + x + "y: " + y);
        //Gdx.app.log("Player Location", "x: " + playerLocation.getX() + "y: " + playerLocation.getY());


        if (Math.abs(x - playerLocation.getX()) >= Math.abs(Math.abs(y - screenHeight)- playerLocation.getY())) {
            if (x < playerLocation.getX()) {
                //Gdx.app.log("Left", "Going Left");
                MazeDemo.personDir.setDirection(-1);
            }
            else if (x > playerLocation.getX()){
                //Gdx.app.log("Right", "Going Right");
                MazeDemo.personDir.setDirection(1);
            }
        }
        else {
            if (Math.abs(y - screenHeight) > playerLocation.getY()) {
                //Gdx.app.log("Up", "Going Up");
                MazeDemo.personDir.setDirection(2);
            }
            else if (Math.abs(y - screenHeight) < playerLocation.getY()) {
                //
                // Gdx.app.log("Down", "Going Down");
                MazeDemo.personDir.setDirection(-2);
            }
        }

        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        MazeDemo.personDir.setDirection(69);
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {

        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        if (Math.abs(x - playerLocation.getX()) >= Math.abs(Math.abs(y - screenHeight)- playerLocation.getY())) {
            if (x < playerLocation.getX()) {
                MazeDemo.personDir.setDirection(-1);
            }
            else if (x > playerLocation.getX()){
                MazeDemo.personDir.setDirection(1);
            }
        }
        else {
            if (Math.abs(y - screenHeight) > playerLocation.getY()) {
                MazeDemo.personDir.setDirection(2);
            }
            else if (Math.abs(y - screenHeight) < playerLocation.getY()) {
                MazeDemo.personDir.setDirection(-2);
            }
        }

        return false;
    }


    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        MazeDemo.personDir.setDirection(69);
        return false;
    }

    @Override
    public boolean zoom (float originalDistance, float currentDistance){

        return false;
    }

    @Override
    public boolean pinch (Vector2 initialFirstPointer, Vector2 initialSecondPointer, Vector2 firstPointer, Vector2 secondPointer){

        return false;
    }
    @Override
    public void pinchStop () {
    }
}
