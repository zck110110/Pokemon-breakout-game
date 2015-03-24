package com.mygdx.game;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
public class WorldRenderer {
	static public float timestep = 1;
	Box2DDebugRenderer renderer;
	public float CAMERA_WIDTH;
	public float CAMERA_HEIGHT;
	public float zoom;
	
	private World world;
	public OrthographicCamera cam;
	
	private SpriteBatch spriteBatch;
	private Texture ball1, ball2;
	private Body ballbody;

	public WorldRenderer(World world, float w, float h, boolean debug, Body ballbody, float zoom) {
		renderer = new Box2DDebugRenderer();// font = new BitmapFont();
		this.world = world;
		this.zoom = zoom;
		CAMERA_WIDTH = w;
		CAMERA_HEIGHT = h;
		ball1 = new Texture(Gdx.files.internal("images/ball1.png"));
		ball2 = new Texture(Gdx.files.internal("images/ball2.png"));
		this.ballbody = ballbody;
		spriteBatch = new SpriteBatch();
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);

	}

	public void SetCamera(float x, float y) {
		this.cam.position.set(x, y, 0);
		this.cam.update();
	}

	public void dispose() {
		world.dispose();
	}

	public void render(float delta) {
		renderer.render(world, cam.combined);
		spriteBatch.begin();
		drawBall();

		spriteBatch.end();
		if(timestep == 0)
		{
			world.step(timestep, 8, 6);
			Vector2 vector = new Vector2(ballbody.getPosition().x,ballbody.getPosition().y-10);
			//mWorld.step(timeStep, velocityIterations, positionIterations);
			Vector2 force = new Vector2(-1000f, -1000f);
			
			GameScreen.mCircle2=Box2dFactory.createBall(world,vector, 4f, false,"pikachu");
			GameScreen.mCircle2.applyLinearImpulse(force, GameScreen.mCircle2.getPosition(), true);
			timestep=delta;
		}
		else
		world.step(delta, 8, 6);
	}

	private void drawBall() {
		float radius =ballbody.getFixtureList().get(0).getShape().getRadius();
		spriteBatch.draw(ball1, ((ballbody.getPosition().x - radius) * zoom * 2 + CAMERA_WIDTH
				* zoom) / 2, ((ballbody.getPosition().y - radius) * zoom * 2 + CAMERA_HEIGHT
						* zoom) / 2,
				radius*zoom, radius*zoom, 2*radius*zoom, 2*radius*zoom, 1, 
				1, ballbody.getAngle() * MathUtils.radiansToDegrees,
				0, 0, 64, 64, false,
				false);
		if(GameScreen.mCircle2!=null)
			spriteBatch.draw(ball2,
					((GameScreen.mCircle2.getPosition().x - radius) * zoom * 2 + CAMERA_WIDTH
						* zoom) / 2,
					((GameScreen.mCircle2.getPosition().y - radius) * zoom * 2 + CAMERA_HEIGHT
							* zoom) / 2, radius*zoom, radius*zoom, 2*radius*zoom, 2*radius*zoom, 1, 
							1, ballbody.getAngle() * MathUtils.radiansToDegrees / 10,
							0, 0, 64, 64, false,
							false);		
		
	}

}
