package com.mygdx.game;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.TimeUtils;
import com.google.gson.Gson;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

import java.sql.*;
import java.util.ArrayList;

/*
 * Author  Group Id: 24
 Group member:
 Siqi Wu (631111)
 Chieh Cheng Lai (589252)
 Chuan Qin (621715)
 Chikai Zhang (644368)
 */
public class GameScreen implements Screen, InputProcessor {
	public static long timePassed = 0;
	private GameOver over;
	private long startTime;

	private MyGame game;
	private float screenWidth = MyGame.w;
	private float screenHeight = MyGame.h;

	// assist button to implement back function
	private boolean isBack = false;
	// object to store user name, score, and max level
	public static Score MyScore = new Score("", 0, "1");

	private WorldRenderer renderer;
	protected static float PXTM;
	protected static Body mCircle1;
	protected static Body mCircle2;
	private Body paddle;
	private Body brick;
	public Body brick2;
	private World mWorld;
	private MouseJoint mouseJoint;
	private Body mGroupBody;
	private Fixture fixPaddle;
	private SpriteBatch batch;
	private Texture bg;
	private BitmapFont bf;
	private Texture mypaddle;
	// choose map id
	private int mapId;
	private String level;
	protected MobileServiceClient client;

	// set up music
	protected static Sound sound1 = Gdx.audio.newSound(Gdx.files
			.internal("musics/pikachu.mp3"));
	private Music myMusic;

	// particle effect
	ParticleEffect mParticle;
	ParticleEffect temp;
	ParticleEffectPool mParticlePool;
	ArrayList<ParticleEffect> particleList;
	ParticleEmitter pe;

	int count = 0;
	int check = 0;

	private float toWorldSize(float pos) {
		return (pos / PXTM);
	}

	public GameScreen(MobileServiceClient m, MyGame game, int mapId) {
		startTime = TimeUtils.millis();
		over = new GameOver(game);

		this.game = game;

		myMusic = Gdx.audio.newMusic(Gdx.files.internal("musics/beijing.MP3"));
		backgroundMusic();

		Preferences prefs = Gdx.app.getPreferences(".state");
		MyScore.id = prefs.getString("playerName");
		MyScore.level = String.valueOf(mapId);

		// zoom rate to apply self adaptment
		PXTM = screenHeight * screenWidth / 92160f;
		mWorld = new World(new Vector2(0f, 0f), true);

		Vector2 vector1 = Transform.ptm(screenWidth / 12f, screenWidth / 3.6f,
				screenWidth, screenHeight, 0f, 0f, PXTM);
		BodyDef mCircleBodyDef1 = new BodyDef();
		mCircleBodyDef1.type = BodyType.DynamicBody;
		mCircle1 = Box2dFactory.createBall(mWorld, vector1, 2f, false, "ball");
		Vector2 force = new Vector2(3000f, 3000f);
		mCircle1.setBullet(true);
		mCircle1.applyLinearImpulse(force, mCircleBodyDef1.position, true);
		mCircle2 = null;

		// particle effect initialized
		mParticle = new ParticleEffect();
		mParticle.load(Gdx.files.internal("images/ban.p"),
				Gdx.files.internal("images/"));
		mParticlePool = new ParticleEffectPool(mParticle, 5, 10);
		particleList = new ArrayList<ParticleEffect>();

		// create texture
		batch = new SpriteBatch();
		bg = new Texture(Gdx.files.internal("images/bg1.jpg"));
		mypaddle = new Texture(Gdx.files.internal("images/paddle.png"));
		// create 4 walls
		float x = screenWidth / PXTM;
		float y = screenHeight / PXTM;
		BodyDef edgeBodyDef = new BodyDef();
		edgeBodyDef.position.set(-x / 2, -y / 2);
		edgeBodyDef.type = BodyType.StaticBody;
		mGroupBody = mWorld.createBody(edgeBodyDef);
		EdgeShape edge = new EdgeShape();
		FixtureDef edgeShapeDef = new FixtureDef();
		edgeShapeDef.shape = edge;
		edge.set(new Vector2(0f, 0f), new Vector2(x, 0f));
		mGroupBody.createFixture(edgeShapeDef).setUserData("wall");
		edge.set(new Vector2(x, 0f), new Vector2(x, y));
		mGroupBody.createFixture(edgeShapeDef).setUserData("wall");
		edge.set(new Vector2(x, (float) (y * 0.9)), new Vector2(0,
				(float) (y * 0.9)));
		mGroupBody.createFixture(edgeShapeDef).setUserData("wall");
		edge.set(new Vector2(0, y), new Vector2(0, 0));
		mGroupBody.createFixture(edgeShapeDef).setUserData("wall");

		// initialize map file
		client = m;
		this.mapId = mapId;

		try {
			MobileService.globalHighScoreSetup(client);
		} catch (NullPointerException e) {
			LeaderboardScreen.high[0] = e.toString();
		}

		if (MyGame.map[mapId] == null)
			level = MobileService.getMapFromFile(String.valueOf(mapId));
		else
			level = MyGame.map[mapId];

		for (int i = 0; i < level.split("\\s+").length; i++)
			brick = Box2dFactory.createBrick(mWorld, level.split("\\s+")[i]);
		Log.i("Brick", level.split("\\s+")[0]);

		// create table in back end
		MobileService.Insert(m, GameScreen.MyScore);

		// create paddle
		BodyDef paddleBodyDef = new BodyDef();
		paddleBodyDef.type = BodyType.DynamicBody;
		paddleBodyDef.position.set(vector1);
		paddle = mWorld.createBody(paddleBodyDef);
		PolygonShape paddleShape = new PolygonShape();
		paddleShape.setAsBox(toWorldSize(screenHeight / 20),
				toWorldSize(screenWidth / 40));

		FixtureDef paddleShapeDef = new FixtureDef();
		paddleShapeDef.shape = paddleShape;
		paddleShapeDef.density = 10f;
		paddleShapeDef.friction = 0.5f;
		paddleShapeDef.restitution = 0.1f;
		paddle.createFixture(paddleShapeDef).setUserData("paddle");
		fixPaddle = paddle.createFixture(paddleShapeDef);
		PrismaticJointDef prismaticJointDef = new PrismaticJointDef();
		// only move in x axis
		Vector2 axis = new Vector2(1f, 0f);
		// block to detect collison
		prismaticJointDef.collideConnected = true;
		prismaticJointDef.initialize(mGroupBody, paddle,
				paddle.getWorldCenter(), axis);
		mWorld.createJoint(prismaticJointDef);
		mWorld.setContactListener(new MyContactListener(mWorld, m));
		Gdx.input.setInputProcessor(this);

	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (mouseJoint == null) {
			MouseJointDef mouseJointDef = new MouseJointDef();
			mouseJointDef.bodyA = mGroupBody;
			mouseJointDef.bodyB = paddle;
			// change force
			mouseJointDef.maxForce = 6000000f;
			mouseJointDef.collideConnected = true;
			mouseJointDef.target.x = paddle.getPosition().x;
			mouseJointDef.target.y = paddle.getPosition().y;
			mouseJoint = (MouseJoint) mWorld.createJoint(mouseJointDef);
		}
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if (mouseJoint != null) {
			mWorld.destroyJoint(mouseJoint);
			mouseJoint = null;
		}
		System.out.println(screenX + ":" + screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		if (mouseJoint != null) {
			Vector2 vector2 = Transform.ptm(screenX, screenHeight - screenY,
					screenWidth, screenHeight, 0, 0, PXTM);
			mouseJoint.setTarget(vector2);
		}
		return false;
	}

	@Override
	public void render(float delta) {
		timePassed = TimeUtils.timeSinceMillis(startTime);
		if (timePassed >= 60000) {
			Preferences prefs = Gdx.app.getPreferences(".state");
			if (prefs.getString("highestScore") != "") {
				int hScore = Integer.parseInt(prefs.getString("highestScore"));
				if (MyScore.score > hScore) {
					prefs.putString("highestScore",
							String.valueOf(MyScore.score));
					prefs.flush();
				}
			}
			myMusic.stop();
			game.setScreen(over);
		}

		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// create score info, etc and background
		batch.begin();
		batch.draw(bg, 0f, 0f, screenWidth, screenHeight);
		bf.setScale(screenWidth / 720, screenHeight / 1280);
		bf.draw(batch,
				"    Name: " + MyScore.id + "    Score: "
						+ String.valueOf(MyScore.score) + "    Time: "
						+ String.valueOf(60 - timePassed / 1000) + "s",
				screenWidth / 14.4f, screenHeight / 1.024f);
		bf.draw(batch,
				"    Next: " + getNext(LeaderboardScreen.high, MyScore.score)
						+ "    Rank: "
						+ getRank(LeaderboardScreen.high, MyScore.score)
						+ "    Level: " + MyScore.level, screenWidth / 14.4f,
				screenHeight / 1.070f);

		batch.end();

		// particle effect initialized
		if (true) {
			if (Gdx.input.isTouched()) {
				pe = mParticle.getEmitters().get(0);
				pe.setAdditive(false);
				temp = mParticlePool.obtain();
				temp.setPosition(
						(mCircle1.getPosition().x * 2 * PXTM + screenWidth) / 2,
						(mCircle1.getPosition().y * 2 * PXTM + screenHeight) / 2);

				if (count < 10) {
					particleList.add(temp);
				} else {
					particleList.remove(0);
					particleList.add(temp);
				}
				count = count + 1;
			}
			batch.begin();
			for (int i = 0; i < particleList.size(); i++) {
				particleList.get(i).draw(batch, Gdx.graphics.getDeltaTime());
			}
			batch.end();
			check = check + 1;
			if (check - count > 20) {
				for (int i = 0; i < particleList.size(); i++) {
					particleList.remove(i);
				}
				count = 0;
				check = 0;
			}
			// clear out the use less fire atoms.
			ParticleEffect temParticle;
			for (int i = 0; i < particleList.size(); i++) {
				temParticle = particleList.get(i);
				if (temParticle.isComplete()) {
					particleList.remove(i);
				}
			}
		}

		renderer.render(delta);
		batch.begin();
		batch.draw(
				mypaddle,
				((paddle.getPosition().x - screenHeight / 20 / PXTM) * PXTM * 2 + screenWidth) / 2,
				((paddle.getPosition().y - screenWidth / 40 / PXTM) * PXTM * 2 + screenHeight) / 2,
				screenHeight / 20 * 2, screenWidth / 40 * 2);
		batch.end();
		/* Back to level screen */
		if (!isBack && Gdx.input.isKeyPressed(Input.Keys.BACK)) {
			myMusic.stop();
			game.setScreen(game.menuScreen);
			isBack = true;
		}

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		isBack = false;
		bf = new BitmapFont(Gdx.files.internal("fonts/breakout_light.fnt"));
		renderer = new WorldRenderer(mWorld, screenWidth / PXTM, screenHeight
				/ PXTM, true, mCircle1, PXTM);
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		myMusic.dispose();
		mParticle.dispose();
		if (temp != null)
			temp.dispose();
		mParticlePool.clear();
	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void backgroundMusic() {
		myMusic.setLooping(true);
		myMusic.play();
		myMusic.setVolume(20);

	}

	

	public String getRank(String[] items, int score) {
		int count = 0;

		if (items[0] == null) {
			return "---";
		}
		int len = items.length;//

		for (int i = 1; i <= len; i++) {
			
			if(items[i-1]!=null && !items[i-1].equals(""))
			//int temp []= Integer.parseInt(items[i - 1].split(",");
			if (score > Integer.parseInt(items[i - 1].split(",")[1])) {
				return String.valueOf(i);
			}
		}

		for (int j = 0; j < len; j++) {
			if(items[j]!=null)
			count++;
		}

		if (count < 10)
			return String.valueOf(count);
		else
			return "---";
	}

	public String getNext(String[] items, int score) {
		if (items==null) {
			return "---";
		}
		String rank = getRank(items, score);
		if(rank.equals("1"))
			return "---";
		if (!rank.equals("---")) {
			return String
					.valueOf(Integer.parseInt(items[Integer.parseInt(rank) - 2]
							.split(",")[1]));
		} else {
			if(items[9]!=null && !items[9].equals(""))
			return String.valueOf(Integer.parseInt(items[9].split(",")[1]));
			else return "---";
		}
	}
}

class Transform {
	public static Vector2 ptm(float x_px, float y_px, float screenWidth,
			float screenHeight, float width_px, float height_px, float scale) {
		Vector2 vector2 = new Vector2();
		vector2.x = -(screenWidth - x_px * 2 - width_px) / scale / 2;
		vector2.y = -(screenHeight - y_px * 2 - height_px) / scale / 2;
		return vector2;
	}

}
