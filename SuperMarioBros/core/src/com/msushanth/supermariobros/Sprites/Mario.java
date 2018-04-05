package com.msushanth.supermariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Sushanth on 10/6/16.
 */

public class Mario extends Sprite {
    public World world;
    public Body b2Body;




    public Mario(World world) {
        this.world = world;

        defineMario();
    }




    public void defineMario() {
        BodyDef bDef = new BodyDef();
        bDef.position.set(32,32);
        bDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bDef);

        FixtureDef fDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5);

        fDef.shape = shape;
        b2Body.createFixture(fDef);
    }



}
