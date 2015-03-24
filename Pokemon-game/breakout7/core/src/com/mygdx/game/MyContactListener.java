package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;
//import com.mygdx.game.
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
public class MyContactListener implements ContactListener {

	World mWorld;
	MobileServiceClient mClient;

	public MyContactListener(World mWorld, MobileServiceClient m) {
		super();
		this.mWorld = mWorld;
		mClient = m;
	}

	@Override
	public void beginContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub

	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		WorldManifold manifold = contact.getWorldManifold();
		for (int j = 0; j < manifold.getNumberOfContactPoints(); j++) {
			// for (int i = 0; i < 2; i++) {
			if (contact.getFixtureA().getUserData() != null
					&& contact.getFixtureA().getUserData().equals("b"))
				contact.setEnabled(true);
			if (contact.getFixtureB().getUserData() != null
					&& contact.getFixtureB().getUserData().equals("b"))
				contact.setEnabled(true);
		}
		// }

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Body body1 = null;
		Body body2 = null;
		if (contact.getFixtureA() != null)
			body1 = contact.getFixtureA().getBody();
		if (contact.getFixtureB() != null)
			body2 = contact.getFixtureB().getBody();

		if (body1 != null
				&& body1.getFixtureList().get(0).getUserData().equals("b")) {
			body1.getFixtureList().get(0).setUserData("mb");
			return;
		}

		if (body2 != null
				&& body2.getFixtureList().get(0).getUserData().equals("b")) {
			body2.getFixtureList().get(0).setUserData("mb");
			return;
		}

		if (body1 != null && body2 != null) {
			if ((body2.getFixtureList().get(0).getUserData().equals("ball") || body2
					.getFixtureList().get(0).getUserData().equals("pikachu"))) {
				if (body1.getFixtureList().get(0).getUserData().equals("mb")) {
					body1.setActive(false);
					mWorld.destroyBody(body1);
					GameScreen.MyScore.score = GameScreen.MyScore.score + 10;
					MobileService.Update(mClient, GameScreen.MyScore);// update
																		// to
																		// table
					// AndroidLauncher.
					return;
				}
				if (body1.getFixtureList().get(0).getUserData().equals("tb")) {
					//body1.setActive(false);
					//mWorld.destroyBody(body1);
					body1.getFixtureList().get(0).setUserData("mb");
					GameScreen.MyScore.score = (GameScreen.MyScore.score + 10);
					MobileService.Update(mClient, GameScreen.MyScore);
					GameScreen.timePassed = GameScreen.timePassed - 1000;
					return;
				}
			}
			if ((body1.getFixtureList().get(0).getUserData().equals("ball") || body1
					.getFixtureList().get(0).getUserData().equals("pikachu"))) {
				if (body2.getFixtureList().get(0).getUserData().equals("mb")) {
					body2.setActive(false);
					mWorld.destroyBody(body2);
					GameScreen.MyScore.score = GameScreen.MyScore.score + 10;
					MobileService.Update(mClient, GameScreen.MyScore);// update
																		// to
																		// table
					// AndroidLauncher.
					return;
				}
				if (body2.getFixtureList().get(0).getUserData().equals("tb")) {
					//body2.setActive(false);
					//mWorld.destroyBody(body2);
					body2.getFixtureList().get(0).setUserData("pikachuB");
					GameScreen.MyScore.score = (GameScreen.MyScore.score + 10);
					MobileService.Update(mClient, GameScreen.MyScore);
					GameScreen.timePassed = GameScreen.timePassed - 1000;
					return;
				}
			}

			// Split ball
			if (body1.getFixtureList().get(0).getUserData().equals("sb")
					&& body2.getFixtureList().get(0).getUserData()
							.equals("ball")) {
				body1.setActive(false);
				mWorld.destroyBody(body1);

				WorldRenderer.timestep = 0;
				GameScreen.sound1.play(1.0f);
				body2.getFixtureList().get(0).getShape().setRadius(4f);

				// AndroidLauncher.
				return;
			}
			if (body1.getFixtureList().get(0).getUserData().equals("ball")
					&& body2.getFixtureList().get(0).getUserData().equals("sb")) {
				body2.setActive(false);
				mWorld.destroyBody(body2);

				WorldRenderer.timestep = 0;
				GameScreen.sound1.play(1.0f);
				body1.getFixtureList().get(0).getShape().setRadius(4f);
				return;
			}

			// get pikachu back
			if (body1.getFixtureList().get(0).getUserData().equals("pikachuB")
					&& body2.getFixtureList().get(0).getUserData()
							.equals("ball")) {
				body1.setActive(false);
				GameScreen.mCircle2 = null;
				mWorld.destroyBody(body1);
				body2.getFixtureList().get(0).getShape().setRadius(2f);

				return;
			}
			if (body1.getFixtureList().get(0).getUserData().equals("ball")
					&& body2.getFixtureList().get(0).getUserData()
							.equals("pikachuB")) {
				body2.setActive(false);
				GameScreen.mCircle2 = null;
				mWorld.destroyBody(body2);
				body1.getFixtureList().get(0).getShape().setRadius(2f);

				// GameScreen.MyScore.score =
				// String.valueOf(Integer.valueOf(GameScreen.MyScore.score)+100);
				// MobileService.Update(mClient,GameScreen.MyScore);
				return;
			}
			if (body1.getFixtureList().get(0).getUserData().equals("pikachu")
					&& body2.getFixtureList().get(0).getUserData()
							.equals("ball")) {
				body1.getFixtureList().get(0).setUserData("pikachuB");
				return;
			}

			if (body1.getFixtureList().get(0).getUserData().equals("ball")
					&& body2.getFixtureList().get(0).getUserData()
							.equals("pikachu")) {

				body2.getFixtureList().get(0).setUserData("pikachuB");

				return;
			}

		}

	}


}
