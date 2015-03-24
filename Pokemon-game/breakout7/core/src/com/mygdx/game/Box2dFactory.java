package com.mygdx.game;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.google.gson.Gson;

/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
/**
 * Methods to create Box2D shapes
 */

public class Box2dFactory {
	
	private static Gson gson = new Gson();

	// Creates a circle object with radius in the given position
	public static Body createBall (World world, Vector2 vector, float radius, boolean isStatic, String userData) {
		CircleShape sd = new CircleShape();
		sd.setRadius(radius);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = sd;
		fdef.density = 2.0f;
		fdef.friction = 0f;
		fdef.restitution = 1f;

		BodyDef bd = new BodyDef();
		bd.allowSleep = false;
		bd.position.set(vector.x, vector.y);
		Body body = world.createBody(bd);
		body.createFixture(fdef).setUserData(userData);
		if (isStatic) {
			body.setType(BodyDef.BodyType.StaticBody);
		} else {
			body.setType(BodyDef.BodyType.DynamicBody);
		}
		return body;
	}

	/** Creates a wall by constructing a rectangle whose corners are (xmin,ymin) and (xmax,ymax), and rotating the box
	 * counterclockwise through the given angle, with specified restitution. */
	public static Body createWall (World world, float xmin, float ymin, float xmax, float ymax, float angle, float restitution) {
		float cx = (xmin + xmax) / 2;
		float cy = (ymin + ymax) / 2;
		float hx = (xmax - xmin) / 2;
		float hy = (ymax - ymin) / 2;
		if (hx < 0) hx = -hx;
		if (hy < 0) hy = -hy;
		PolygonShape wallshape = new PolygonShape();
		wallshape.setAsBox(hx, hy, new Vector2(0f, 0f), angle);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = wallshape;
		fdef.density = 1.0f;
		if (restitution > 0) fdef.restitution = restitution;

		BodyDef bd = new BodyDef();
		bd.position.set(cx, cy);
		Body wall = world.createBody(bd);
		wall.createFixture(fdef);
		wall.setType(BodyDef.BodyType.StaticBody);
		return wall;
	}

	/** Creates a segment-like thin wall with 0.05 thickness going from (x1,y1) to (x2,y2) */
	public static Body createThinWall (World world, float x1, float y1, float x2, float y2, float restitution) {
		// determine center point and rotation angle for createWall
		float cx = (x1 + x2) / 2;
		float cy = (y1 + y2) / 2;
		float angle = (float)Math.atan2(y2 - y1, x2 - x1);
		float mag = (float)Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
		return createWall(world, cx - mag / 2, cy - 0.05f, cx + mag / 2, cy + 0.05f, angle, restitution);
	}
	public static Body createBrick(World world, float xmin, float ymin, float xmax, float ymax, float angle, float restitution ,String UserData)
	{
		float cx = (xmin + xmax) / 2;
		float cy = (ymin + ymax) / 2;
		float hx = (xmax - xmin) / 2;
		float hy = (ymax - ymin) / 2;
		if (hx < 0) hx = -hx;
		if (hy < 0) hy = -hy;
		PolygonShape brickshape = new PolygonShape();
		brickshape.setAsBox(hx, hy, new Vector2(0f, 0f), angle);

		FixtureDef fdef = new FixtureDef();
		fdef.shape = brickshape;
		fdef.density = 2.0f;
		
		if (restitution > 0) fdef.restitution = restitution;

		BodyDef bd = new BodyDef();
		bd.position.set(cx, cy);
		Body brick = world.createBody(bd);
		
		brick.setType(BodyDef.BodyType.DynamicBody);
		brick.createFixture(fdef).setUserData(UserData);
		//brick.setUserData("brick");
		return brick;
	}

	public static Body createBrick(World world, String json)
	{
		Brick b = gson.fromJson(json, Brick.class);
		Vector2 vector1 = Transform.ptm(b.xmin, b.ymin, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, GameScreen.PXTM);
		Vector2 vector2 = Transform.ptm(b.xmax, b.ymax, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, GameScreen.PXTM);
	
		float cx = (vector1.x + vector2.x) / 2;
		float cy = (vector1.y + vector2.y) / 2;
		float hx = (vector1.x - vector2.x) / 2;
		float hy = (vector1.y - vector2.y) / 2;
		Log.i("Brick", String.valueOf(cx));
		if (hx < 0) hx = -hx;
		if (hy < 0) hy = -hy;
		PolygonShape brickshape = new PolygonShape();
		brickshape.setAsBox(hx, hy, new Vector2(0f, 0f), b.angle);
	
		FixtureDef fdef = new FixtureDef();
		fdef.shape = brickshape;
		fdef.density = 0.5f;
		
		if (b.res > 0) fdef.restitution = b.res;

		BodyDef bd = new BodyDef();
		bd.position.set(cx, cy);
		Body brick = world.createBody(bd);
		
		// "b" mode brick can not be moved
		if(b.mode.equals("b"))
		brick.setType(BodyDef.BodyType.StaticBody);
		else
		brick.setType(BodyDef.BodyType.DynamicBody);
		
		// change the brick mode to specified mode
		brick.createFixture(fdef).setUserData(b.mode);
		return brick;
	}

	
}
