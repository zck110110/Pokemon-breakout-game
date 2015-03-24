package com.mygdx.game.android;

import java.net.MalformedURLException;

import android.os.Bundle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.mygdx.game.GameScreen;
import com.mygdx.game.MyGame;
//import com.mygdx.game.MyGdxGame;
import com.mygdx.game.Score;

public class AndroidLauncher extends AndroidApplication {
	public static MobileServiceClient mclient;
	//public static Score score= new Score("NoName","0",);
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		 try {
	            mclient = new MobileServiceClient(
	            		"https://smash.azure-mobile.net/",
						"NpIOkRVgEByaiglxUWlNdexgZfYLlC27",
	                      this
	                );
	        } catch (MalformedURLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
		 
		super.onCreate(savedInstanceState);
		

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGame(mclient),config);
		
	
	
	
	
	}

	@Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
//        super.onBackPressed();
    }	

}
