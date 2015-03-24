Group Id: 24
Group member:
      Siqi Wu (631111)
      Chieh Cheng Lai (589252)
      Chuan Qin (621715)
      Chikai Zhang (644368)

============================================================================================================================
Description of the project:
This project we develop modified version of breakout game. Pokemon Theme! (A famous 
Japanese cartoon). This game based on real world's law of physics (collide, momentum, friction).
Vivid and passionate music! Three levels (right now,could update further by server) game. One is
harder than previous one! (the bricks in the game has feature which may disappear by one hit or more.
Other "good features" bricks will show double ball and so on features). Live updating ranking, makes
the players could have competition with others with Internet.
Game Feature: Ball not dead, Limit time, bricks with characteristics, ranking, Internet, "Pokemon Theme".

File Relationship
============================================================================================================================
After import the breakout7 (gradle project), it show three files.File "breakout7" is the head file; File "android"(my-gdx-game-android) main contains the resources of images, sounds, music, related libs(jar files), all the material we use from outside; File "core"(my-gdx-game-core) main contains the source codes! all the class files!    

How to run/install
============================================================================================================================
Install the apk(app) or run the code directly 
Install the libgdx, the game is based on the libgdx!! if 
want running in IDE or by source directly, it need install the
libgdx!
Install the gradle!! SAME REASON AS ABOVE. 
Note!!!! In order to run/import these code successfully, 
the following jar files should also be imported.
1. android-support-v4.jar   /*the android support*/
2. gson-2.2.2.jar           /*the gson file*/
3. mobileservices-1.1.5.jar /*Microsoft azure services*/
hit: file 1, 2 and 3 above in the directory  breakout7/android/libs/
4. android.jar            /* this jar file in the "Your computer path"/SDK/platforms/andriod-19/
5. open the file "local.properties" and change the directory
as: YOU PATH\sdk      For example:# Location of the android SDK
sdk.dir=D:\\WORK\\escilps\\sdk
6. first time run the app(just after install), it need download information from serve! Thus it need have the internet at least first time open. If cause error by this, just open again will fix.

Class Description
============================================================================================================================
Box2dFactory : Used for generate objects like circle and rectangle in physical world simulation.
Brick: Designed for receive bricks layout data from json string offered by mobile service from cloud.
MenuScreen:Menu screen class
GameOver:Game over screen class
HelpScreen: Help screen class
LeaderboardScreen: LeaderboardScreen class
LevelScreen: screen for choosing level
Map: class for map data.
MobileService: MobileService class encapsulate services needed for network communication like update score and map download.
MyContactListener: This is for contact handling in physical world
MyGame : MAIN CLASS FOR THE WHOLE GAME, in charge of initialization.
NewPlayerScreen: Screen for creating new player.
Score: data structure for storing game result.
WorldRenderer: Render game with the data provided from physical simulation.

============================================================================================================================

Team 24, Members responsibilities:
Chieh Cheng Lai (589252): Design the basic game rule
                          Design and implement(code) the collision function
                          Graphic design
                          Test and Debug
Chikai Zhang (644368)   : design and implement(code) the sound and music.
                          design and implement(code) particle, animation, picture effects 
                          implement bricks features and score show (in game).
                          Test and Debug
Chuan Qin (621715)      : implement main game frame by using libgdx game engine,
                          designed the physical simulation, 
						  implemented the mobile service client by using Azure cloud
						  computing api.Debugging and testing. 
                          Test and Debug.
Siqi Wu (631111)		: Implement codes to fulfil transfer between different screens
						  Implement codes for auxiliary screens
						  Test and Debug

 
