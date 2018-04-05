package com.msushanth.supermariobros.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.msushanth.supermariobros.SuperMarioBros;
import com.sun.prism.image.ViewPort;

/**
 * Created by Sushanth on 10/5/16.
 */

public class Hud {

    /*
     * New camera and new ViewPort specifically for the hud since game moves
     * around and we want to follow it to always be displayed
     */
    public Stage stage;
    private FitViewport viewPort;

    private Integer worldTimer;
    private float timeCount;
    private  Integer score;

    Label countDownLabel;
    Label scoreLabel;
    Label timeLabel;
    Label levelLabel;
    Label worldLabel;
    Label marioLabel;




    public Hud(SpriteBatch hud) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;

        /*
         * A stage is basically an empty box
         * we are going to create a table inside the stage
         * then we can organize that table to hold the info we want
         */
        viewPort = new FitViewport(SuperMarioBros.V_WIDTH, SuperMarioBros.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewPort, hud);

        countDownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("WORLD" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        marioLabel = new Label("MARIO" , new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Table table = new Table();
        table.top();
        table.setFillParent(true); //our table is now the size of our stage

        //everything will take an equal portion of the screen with expandX
        table.add(marioLabel).expandX().padTop(10);
        table.add(worldLabel).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();         //create a new row
        table.add(scoreLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);
        table.add(countDownLabel).expandX().padTop(10);

        //adding the table to our stage
        stage.addActor(table);
    }
}
