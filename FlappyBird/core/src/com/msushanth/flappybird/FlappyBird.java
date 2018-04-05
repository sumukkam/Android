package com.msushanth.flappybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;

import java.util.Random;


public class FlappyBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;

	float screenWidth = 0;
	float screenHeight = 0;


    Texture topTube;
    Texture bottomTube;


    Texture birdImage;
    Animation animation;
    TextureRegion[] animationFrames;
    float elapsedTime = 0;


    float birdY = 0;
    float velocity = 0;
    float gravity = 2;


    int numOfTubes = 4;
    float distanceBetweenTubes;
    float gapBetweenTubes = 900;
    float maxTubeOffset;
    Random randomGenerator;
    float tubeVelocity = 5;
    float[] tubeX = new float[numOfTubes];
    float[] tubeOffset = new float[numOfTubes];


    //ShapeRenderer shapeRenderer;
    Circle birdCircle;
    Rectangle[] topTubeRectangles;
    Rectangle[] bottomTubeRectangles;


    int gameState = 0;




    @Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("bg.png");


        //shapeRenderer = new ShapeRenderer();
        birdCircle = new Circle();
        topTubeRectangles = new Rectangle[numOfTubes];
        bottomTubeRectangles = new Rectangle[numOfTubes];


        //creating the bird animation
        birdImage = new Texture("bird_animation.png");
        TextureRegion[][] tmpFrames = TextureRegion.split(birdImage, 136, 96);
        animationFrames = new TextureRegion[4];
        int index = 0;
        for(int row=0; row<2; row++) {
            for(int column=0; column<2; column++) {
                animationFrames[index++] = tmpFrames[row][column];
            }
        }
        animation = new Animation(10f/30f, animationFrames);


        // Creating the tubes
        topTube = new Texture("toptube.png");
        bottomTube = new Texture("bottomtube.png");


		screenWidth = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

        maxTubeOffset = screenHeight/2 - gapBetweenTubes/2 - 100;
        randomGenerator = new Random();

        birdY = screenHeight/2 - birdImage.getHeight()/4;

        distanceBetweenTubes = screenWidth/2;


        for(int i=0; i < numOfTubes; i++) {
            tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (screenHeight - gapBetweenTubes - 200);

            tubeX[i] = screenWidth/2 - topTube.getWidth()/2 + i*distanceBetweenTubes + screenWidth;

            topTubeRectangles[i] = new Rectangle();
            bottomTubeRectangles[i] = new Rectangle();
        }




        // batch.begin() tells render method that we will start displaying sprites
		batch.begin();
		batch.draw(background, 0, 0, screenWidth, screenHeight);
		batch.end();

	}




	@Override
	public void render () {

        // need to get the time for the animation to work
        elapsedTime += Gdx.graphics.getDeltaTime();



        if(gameState != 0) {

            batch.begin();
            batch.draw(background, 0, 0, screenWidth, screenHeight);
            batch.end();


            if(Gdx.input.justTouched()) {
                //Gdx.app.log("Screen Touched", "The screen was touched!!!");
                velocity -= 30;
            }


            for(int i=0; i < numOfTubes; i++) {

                if(tubeX[i] < -topTube.getWidth()) {
                    tubeX[i] = numOfTubes * distanceBetweenTubes - topTube.getWidth();
                    tubeOffset[i] = (randomGenerator.nextFloat() - 0.5f) * (screenHeight - gapBetweenTubes - 200);
                }
                else {
                    tubeX[i] = tubeX[i] - tubeVelocity;
                }

                batch.begin();
                batch.draw(topTube, tubeX[i], screenHeight / 2 + gapBetweenTubes / 2 + tubeOffset[i]);
                batch.draw(bottomTube, tubeX[i], screenHeight / 2 - gapBetweenTubes / 2 - bottomTube.getHeight() + tubeOffset[i]);
                batch.end();

                topTubeRectangles[i] = new Rectangle(tubeX[i], screenHeight / 2 + gapBetweenTubes / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
                bottomTubeRectangles[i] = new Rectangle(tubeX[i], screenHeight / 2 - gapBetweenTubes / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());
            }


            // if statement for testing purposes
            if(birdY > 0 || velocity < 0) {
                velocity = velocity + gravity;
                birdY -= velocity;
            }
        }
        else {
            if(Gdx.input.justTouched()) {
                //Gdx.app.log("Screen Touched", "The screen was touched!!!");
                gameState = 1;
            }
        }


        // batch.begin() tells render method that we will start displaying sprites
        // true means that when we get to the forth frame in the animation, we want to loop through again
        batch.begin();
        batch.draw(animation.getKeyFrame(elapsedTime, true), screenWidth / 2 - birdImage.getWidth() / 4, birdY);
        batch.end();

        birdCircle.set(screenWidth/2, birdY+birdImage.getHeight()/4, birdImage.getWidth()/4);

        //shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //shapeRenderer.setColor(Color.RED);
        //shapeRenderer.circle(birdCircle.x, birdCircle.y, birdCircle.radius);

        for(int i=0; i < numOfTubes; i++) {
            //shapeRenderer.rect(tubeX[i], screenHeight / 2 + gapBetweenTubes / 2 + tubeOffset[i], topTube.getWidth(), topTube.getHeight());
            //shapeRenderer.rect(tubeX[i], screenHeight / 2 - gapBetweenTubes / 2 - bottomTube.getHeight() + tubeOffset[i], bottomTube.getWidth(), bottomTube.getHeight());

            if(Intersector.overlaps(birdCircle, topTubeRectangles[i]) || Intersector.overlaps(birdCircle, bottomTubeRectangles[i])) {
                //Gdx.app.log("Collision", "RIP");
                gameState = 0;
            }
        }
        //shapeRenderer.end();
    }




	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
	}
}
