package com.msushanth.supermariobros.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.msushanth.supermariobros.Scenes.Hud;
import com.msushanth.supermariobros.Sprites.Mario;
import com.msushanth.supermariobros.SuperMarioBros;


/**
 * Created by Sushanth on 10/5/16.
 */

public class PlayScreen implements Screen {

    private SuperMarioBros game;

    /* follows us around the game world (what the viewport actually displays) */
    private OrthographicCamera gameCam;
    private FitViewport gamePort;
    private Hud hud;

    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    private World world;
    private Box2DDebugRenderer b2dr;


    private Mario player;



    public PlayScreen(SuperMarioBros game) {
        this.game = game;

        gameCam = new OrthographicCamera();
        gamePort = new FitViewport(SuperMarioBros.V_WIDTH, SuperMarioBros.V_HEIGHT, gameCam);
        hud = new Hud(game.batch);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);

        //center the camera
        gameCam.position.set(gamePort.getWorldWidth()/2, gamePort.getWorldHeight()/2, 0);


        /* 0,0 - no gravity for now */
        /* true - sleep objects that are at rest */
        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();


        /* Creating bodies and fixtures at every object we created in the tile map editor */
        BodyDef bDef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fDef = new FixtureDef();
        Body body;

        /* creating the ground bodies/fixtures */
        /* ground is 2 from the bottom in tile editor ( hence .get(2) ) */
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            /*
             * Dynamic body - a player that is affected by forces, gravity, can move around on screen
             * Static body - dont really move. not affect by forces
             * Kinematic bodies - not affect by forces, but can be manipulated by velocities (pendulums, moving platforms)
             */
            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            //adding body to our world
            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }


        /* creating the pipe bodies/fixtures */
        /* pipe is 3 from the bottom in tile editor ( hence .get(3) ) */
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            //adding body to our world
            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }


        /* creating the coins bodies/fixtures */
        /* coins is 4 from the bottom in tile editor ( hence .get(4) ) */
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            //adding body to our world
            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }


        /* creating the bricks bodies/fixtures */
        /* bricks is 5 from the bottom in tile editor ( hence .get(5) ) */
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(rect.getX()+rect.getWidth()/2, rect.getY()+rect.getHeight()/2);

            //adding body to our world
            body = world.createBody(bDef);

            shape.setAsBox(rect.getWidth()/2, rect.getHeight()/2);
            fDef.shape = shape;
            body.createFixture(fDef);
        }


        //creating mario
        player = new Mario(world);
    }




    public void handleInput(float dt) {
        if(Gdx.input.isTouched()) {
            gameCam.position.x += 200 * dt;
        }
    }




    /* Where we will handle the updating of our gameworld
     * inputs, clicks, collisions...
     * dt = delta time
     */
    public void update(float dt) {
        handleInput(dt);

        //in order for box2d to execute the physics emulation we need to tell it how many times to calculate per second
        //velocity and position iteration - affects how 2 bodies react during a collision
        world.step(1/60f, 6, 2);


        gameCam.update();

        //to tell our maprenderer what it needs to update (in this case, only what our gameCam sees)
        renderer.setView(gameCam);
    }




    @Override
    public void show() {

    }




    @Override
    public void render(float delta) {
        update(delta);


        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //render our game map
        renderer.render();


        //render our Box2DDebugLines
        b2dr.render(world, gameCam.combined);


        //To find where the camera is and render the hud on it
        //set our batch camera to now draw what the hud sees
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }




    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
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
