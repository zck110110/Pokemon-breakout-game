package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.joints.MouseJoint;
import com.badlogic.gdx.physics.box2d.joints.MouseJointDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.math.Vector2;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
public class MyGame extends Game implements InputProcessor{
	
	protected MenuScreen menuScreen;

    protected static float w;
    protected static float h;
    protected static String[] map = new String[5];
	private MobileServiceClient mClient;
	
	public MyGame(MobileServiceClient m) {
		super();
		setmClient(m);
		if(m!=null)
			MobileService.getMap(m);
	}

	@Override
	public void create() {
		w = Gdx.graphics.getWidth();
        h = Gdx.graphics.getHeight();
        /**
         * Dialog to indicate no Internet
         */
        
//        if(getmClient()==null)
//        {
//        	Gdx.input.getTextInput(null, "Alert!", "Can not connect to server!");
//        }
//        
        try{
    		MobileService.globalHighScoreSetup(getmClient());
    		}
    		catch(NullPointerException e)
    		{
    			LeaderboardScreen.high[0] = e.toString();
    		}
        
        /**
         * set up menu screen
         */
        menuScreen = new MenuScreen(this);
        this.setScreen(menuScreen);
	}
	
	public MobileServiceClient getmClient() {
		return mClient;
	}

	public void setmClient(MobileServiceClient mClient) {
		this.mClient = mClient;
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
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
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

}




 
