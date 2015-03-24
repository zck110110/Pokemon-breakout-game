package com.mygdx.game;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.QueryOrder;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;
/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
public class MobileService {
	public static List<Score> gHighScores = new ArrayList<Score>();

	public static void Insert(MobileServiceClient m, Score s) {
		if (m != null) {
			m.getTable(Score.class).insert(s,
					new TableOperationCallback<Score>() {
						@Override
						public void onCompleted(Score arg0, Exception arg1,
								ServiceFilterResponse arg2) {
							// TODO Auto-generated method stub
						}
					});
		}
	}

	public static void Update(MobileServiceClient m, Score s) {
		if (m != null) {
			m.getTable(Score.class).update(s,
					new TableOperationCallback<Score>() {

						@Override
						public void onCompleted(
								Score arg0,
								Exception arg1,
								com.microsoft.windowsazure.mobileservices.ServiceFilterResponse arg2) {
							// TODO Auto-generated method stub
						}
					});
		}
	}

	public static String getMapFromFile(String id) {
		FileHandle map;
		map = Gdx.files.internal("maps/level" + id + ".txt");
		return map.readString();
	}

	public static void getMap(MobileServiceClient mClient) {

		if (mClient != null) {
			mClient.getTable(Map.class).orderBy("id", QueryOrder.Descending)
					.top(5).execute(new TableQueryCallback<Map>() {
						public void onCompleted(List<Map> result, int count,
								Exception exception,
								ServiceFilterResponse response) {
							if (exception == null) {
								int i = 0;
								for (Map item : result) {
									// Log.i(TAG, "Read object with ID " +
									// item.id);
									MyGame.map[i] = item.brick;
									i++;
								}
							}
						}
						/* same implementation as above */
					});
		}
	}

	// return temp;

	public static void globalHighScoreSetup(MobileServiceClient mClient) {
		
		if (mClient != null) {
			LeaderboardScreen.high = new String[10];
			mClient.getTable(Score.class)
					.orderBy("score", QueryOrder.Descending).top(10)
					.execute(new TableQueryCallback<Score>() {
						public void onCompleted(List<Score> result, int count,
								Exception exception,
								ServiceFilterResponse response) {
							if (exception == null) {
								int i = 0;
								for (Score item : result) {
									LeaderboardScreen.high[i] = item.id + ","
											+ item.score;
									i++;
								}
							}
						}
					});
		}

		else
			Log.i("Game", "Null");
	}
}
