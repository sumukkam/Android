package com.msushanth.mazedemo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Random;


public class MazeDemo extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;

    private Circle user;


    public static Direction personDir = new Direction();



    private Random randomGenerator;

    private float screenWidth;
    private float screenHeight;

    private ArrayList<ArrayList<Coordinate>> allSpaces;

    private ArrayList<Rectangle> walls;
    //ArrayList<Coordinate> playableSpaces;

    private Rectangle[][] mazeRectangles;

    private ShapeRenderer shapeRenderer;

    private float widthOfRectangles;
    private float heightOfRectangles;
    private float ratio = 10;

    private float sizeSmaller = 45f;
    private int speed = 15;


    private int[][] maze = new int[][]{

            /*
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            { 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
            { 1, 1, 1, 1, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
            */


            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
            { 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0},
            { 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 0},
            { 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0},
            { 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            { 0, 1, 0, 0, 1, 0, 1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0},
            { 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0},
            { 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0},
            { 0, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 0},
            { 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            { 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0},
            { 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0},
            { 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 0},
            { 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 0, 0, 0},
            { 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0},
            { 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 0, 0, 0, 1, 0},
            { 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0},
            { 0, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0},
            { 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1, 0},
            { 0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0},
            { 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0},
            { 0, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0, 0},
            { 0, 1, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0},
            { 0, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0},
            { 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0},
            { 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            { 0, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0},
            { 0, 1, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0, 0},
            { 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0},
            { 0, 1, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
            { 0, 1, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 1, 0},
            { 0, 1, 0, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 1, 0},
            { 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0},
            { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}

    };




    private int upDirection = 2;
    private int downDirection = -2;
    private int leftDirection = -1;
    private int rightDirection = 1;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    boolean[] directionMoving = {up, down, left, right};

    private Coordinate currentLocation;

    private int xIndex = 0;
    private int yIndex = 0;




    private SwipeDetection playerMovement;


    Sprite vision;






    @Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");

        vision = new Sprite(new Texture("vision.png"));

        playerMovement = new SwipeDetection();

        Gdx.input.setInputProcessor(new GestureDetector((playerMovement)));

        allSpaces = new ArrayList<ArrayList<Coordinate>>();
        walls = new ArrayList<Rectangle>();
        //playableSpaces = new ArrayList<Coordinate>();

        randomGenerator = new Random();

        shapeRenderer = new ShapeRenderer();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        widthOfRectangles = screenWidth/ratio;
        heightOfRectangles = widthOfRectangles;
        //heightOfRectangles = screenHeight/ratio;


        for (float y=Gdx.graphics.getHeight(); y >= -heightOfRectangles; y -= heightOfRectangles) {
            ArrayList<Coordinate> temp = new ArrayList<Coordinate>();

            for( float x=0; x < screenWidth; x += widthOfRectangles ) {
                Coordinate coordinate = new Coordinate(x, y);
                temp.add(coordinate);
            }

            allSpaces.add(temp);
        }



        mazeRectangles = new Rectangle[allSpaces.size()][allSpaces.get(0).size()];

        for(int x=0; x<allSpaces.size(); x++) {
            for(int y=0; y<allSpaces.get(x).size(); y++) {
                mazeRectangles[x][y] = new Rectangle(allSpaces.get(x).get(y).getX(), allSpaces.get(x).get(y).getY(), widthOfRectangles, heightOfRectangles+1);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                if(maze[x][y] == 1) {
                //if(x==3 && y==2) {
                //if(x%2 == 0) {
                    allSpaces.get(x).get(y).setPlayable(true);
                    //playableSpaces.add(allSpaces.get(x).get(y));
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(allSpaces.get(x).get(y).getX(), allSpaces.get(x).get(y).getY(), widthOfRectangles, heightOfRectangles+1);
                    shapeRenderer.end();
                }
                else {
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.rect(allSpaces.get(x).get(y).getX(), allSpaces.get(x).get(y).getY(), widthOfRectangles, heightOfRectangles+1);
                    shapeRenderer.end();

                    walls.add(new Rectangle(allSpaces.get(x).get(y).getX(), allSpaces.get(x).get(y).getY(), widthOfRectangles, heightOfRectangles+1));
                }
            }
        }


        while (!allSpaces.get(xIndex).get(yIndex).getPlayable()) {
            xIndex = randomGenerator.nextInt(allSpaces.size());
            yIndex = randomGenerator.nextInt(allSpaces.get(0).size());
        }


        currentLocation = new Coordinate(allSpaces.get(xIndex).get(yIndex).getX() + widthOfRectangles/2, allSpaces.get(xIndex).get(yIndex).getY() + widthOfRectangles/2);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(allSpaces.get(xIndex).get(yIndex).getX() + widthOfRectangles/2, allSpaces.get(xIndex).get(yIndex).getY() + widthOfRectangles/2, widthOfRectangles/2-sizeSmaller);
        shapeRenderer.end();

        user = new Circle();
        user.set(allSpaces.get(xIndex).get(yIndex).getX() + widthOfRectangles/2, allSpaces.get(xIndex).get(yIndex).getY() + widthOfRectangles/2, widthOfRectangles/2);


        batch.begin();
        vision.setPosition(currentLocation.getX() - vision.getWidth()/2, currentLocation.getY()- vision.getWidth()/2);
        vision.draw(batch);
        batch.end();
    }















	@Override
	public void render () {

        /*
        for(int x=0; x<allSpaces.size(); x++) {
            for(int y=0; y<allSpaces.get(x).size(); y++) {
                shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                if(maze[x][y] == 1) {
                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(allSpaces.get(x).get(y).getX(), allSpaces.get(x).get(y).getY(), widthOfRectangles, heightOfRectangles+1);
                    shapeRenderer.end();
                }
                else {
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.rect(allSpaces.get(x).get(y).getX(), allSpaces.get(x).get(y).getY(), widthOfRectangles, heightOfRectangles+1);
                    shapeRenderer.end();
                }
            }
        }
        */


        Coordinate previousLocation = new Coordinate(currentLocation.getX(), currentLocation.getY());

        playerMovement.setPlayerLocation(previousLocation);


        int dirAdjusterX = 0;
        int dirAdjusterY = 0;
        if (personDir.getDirection() == upDirection) {
            dirAdjusterX = 0;
            dirAdjusterY = speed;

            currentLocation = new Coordinate(currentLocation.getX()+ dirAdjusterX, currentLocation.getY()+ dirAdjusterY);
            user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);

            for(int i=0; i<walls.size(); i++) {
                if(Intersector.overlaps(user, walls.get(i))) {
                    currentLocation = new Coordinate(currentLocation.getX()- dirAdjusterX, currentLocation.getY()- dirAdjusterY);
                    user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
                }
            }
        }
        else if (personDir.getDirection() == leftDirection) {
            dirAdjusterX = -speed;
            dirAdjusterY = 0;


            currentLocation = new Coordinate(currentLocation.getX()+ dirAdjusterX, currentLocation.getY()+ dirAdjusterY);
            user.set(currentLocation.getX(), currentLocation.getY()+1, widthOfRectangles/2-sizeSmaller);

            for(int i=0; i<walls.size(); i++) {
                if(Intersector.overlaps(user, walls.get(i))) {
                    //.app.log("Wall Hit", "Wall was hit going left");
                    currentLocation = new Coordinate(currentLocation.getX()- dirAdjusterX, currentLocation.getY()- dirAdjusterY);
                    user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
                }
                else {
                    user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
                }
            }


        }
        else if (personDir.getDirection() == downDirection) {
            dirAdjusterX = 0;
            dirAdjusterY = -speed;


            currentLocation = new Coordinate(currentLocation.getX()+ dirAdjusterX, currentLocation.getY()+ dirAdjusterY);
            user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);

            for(int i=0; i<walls.size(); i++) {
                if(Intersector.overlaps(user, walls.get(i))) {
                    currentLocation = new Coordinate(currentLocation.getX()- dirAdjusterX, currentLocation.getY()- dirAdjusterY);
                    user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
                }
            }
        }
        else if (personDir.getDirection() == rightDirection) {
            dirAdjusterX = speed;
            dirAdjusterY = 0;


            currentLocation = new Coordinate(currentLocation.getX()+ dirAdjusterX, currentLocation.getY()+ dirAdjusterY);
            user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2);

            for(int i=0; i<walls.size(); i++) {
                if(Intersector.overlaps(user, walls.get(i))) {
                    //Gdx.app.log("Wall Hit", "Wall was hit going right");
                    currentLocation = new Coordinate(currentLocation.getX()- dirAdjusterX, currentLocation.getY()- dirAdjusterY);
                    user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
                }
                else {
                    user.set(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
                }
            }
        }


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.WHITE);
        shapeRenderer.circle(previousLocation.getX(), previousLocation.getY(), widthOfRectangles/2-sizeSmaller);
        shapeRenderer.end();


        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(currentLocation.getX(), currentLocation.getY(), widthOfRectangles/2-sizeSmaller);
        shapeRenderer.end();


        //batch = null;
        //batch = new SpriteBatch();
        //vision = new Sprite(new Texture("vision.png"));
        //batch.draw(vision, currentLocation.getX() - vision.getWidth()/2, currentLocation.getY()- vision.getWidth()/2);
        vision.setColor(0,0,0,0);

        batch.begin();
        vision.setPosition(currentLocation.getX() - vision.getWidth()/2, currentLocation.getY()- vision.getWidth()/2);
        vision.setColor(225);
        //vision.setPosition(300, 300);
        vision.draw(batch);
        batch.end();

    }




	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
