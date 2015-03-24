package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
public class MenuScreen implements Screen {

    private MyGame breakoutGame;
    
    /**
     * set up sub screens
     */
    LevelScreen levelScreen;
    LeaderboardScreen leaderboardScreen;
    NewPlayerScreen newPlayerScreen;
    HelpScreen helpScreen;

    /**
     * texture parts
     */
    private TextButton.TextButtonStyle btn_style;
    private TextButton newPlayer, newGame, leaderboard, help, exit;
    private Stage stage;
    private SpriteBatch batch;
    private Texture bg;
    
    /**
     * music parts
     */
    private boolean isPlayed = false;
    private Sound mSound;
    public static Sound buttonSound;
    
    public MenuScreen(MyGame breakoutGame){
        this.breakoutGame = breakoutGame;

        Preferences prefs = Gdx.app.getPreferences(".state");
        String playerName = prefs.getString("playerName");

        if (playerName == ""){
            MyTextInputListener listener = new MyTextInputListener();
            Gdx.input.getTextInput(listener, "Welcome to Breakout, please input your name", "");
        }
        
        mSound=Gdx.audio.newSound(Gdx.files.internal("musics/pika2.mp3"));
        buttonSound=Gdx.audio.newSound(Gdx.files.internal("musics/Button.wav"));

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        bg = new Texture(Gdx.files.internal("images/bg.jpg"));
        stage = new Stage();
        initBtnStyle();
        initBtn();
        Gdx.input.setInputProcessor(stage);
        if(isPlayed == false){
        	mySound();
            isPlayed=true;
        }
    }

    /**
     * Initial button style
     */
    private void initBtnStyle(){
        SpriteDrawable spriteDrawable =
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("images/btn_bg.png"))));

        btn_style = new TextButton.TextButtonStyle();
        btn_style.down = spriteDrawable;
        btn_style.up = spriteDrawable;
        btn_style.font = new BitmapFont(Gdx.files.internal("fonts/breakout.fnt"),
                Gdx.files.internal("fonts/breakout.png"), false);
    }

    /**
     * Initial buttons
     */
    private void initBtn(){
        // "new player" button.
        newPlayer = new TextButton("New Player", btn_style);
        newPlayer.setPosition(MyGame.w/2 - newPlayer.getWidth()/2,
        		MyGame.h/2 + newPlayer.getHeight()*5/2);
        newPlayer.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	mSound.stop();
            	buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(1);
            }
        });

        // "new game" button.
        newGame = new TextButton("New Game", btn_style);
        newGame.setPosition(MyGame.w/2 - newGame.getWidth()/2,
        		MyGame.h/2 + newGame.getHeight()*3/2);
        newGame.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	mSound.stop();
            	buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(2);
            }
        });

        // "leaderboard" button.
        leaderboard = new TextButton("Leaderboard", btn_style);
        leaderboard.setPosition(MyGame.w/2 - leaderboard.getWidth()/2,
        		MyGame.h/2 + newGame.getHeight()*1/2);
        leaderboard.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	mSound.stop();
            	buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(3);
            }
        });

        // "help" button.
        help = new TextButton("Help", btn_style);
        help.setPosition(MyGame.w/2 - help.getWidth()/2,
        		MyGame.h/2 - help.getHeight()*1/2);
        help.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	mSound.stop();
            	buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(4);
            }
        });

        // "exit" button.
        exit = new TextButton("Exit", btn_style);
        exit.setPosition(MyGame.w/2 - exit.getWidth()/2,
        		MyGame.h/2 - exit.getHeight()*3/2);
        exit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	mSound.stop();
            	buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        stage.addActor(newPlayer);
        stage.addActor(newGame);
        stage.addActor(leaderboard);
        stage.addActor(help);
        stage.addActor(exit);
    }

    private void setScreen(int screenNum) {
        switch (screenNum) {
        case 1:
            newPlayerScreen = new NewPlayerScreen(breakoutGame);
            breakoutGame.setScreen(newPlayerScreen);
            break;
        case 2:
            levelScreen = new LevelScreen(breakoutGame);
            breakoutGame.setScreen(levelScreen);
            break;
        case 3:
            leaderboardScreen = new LeaderboardScreen(breakoutGame);
            breakoutGame.setScreen(leaderboardScreen);
            break;
        case 4:
            helpScreen = new HelpScreen(breakoutGame);
            breakoutGame.setScreen(helpScreen);
            break;
        default:
            break;
        }
    }
    
    public void mySound(){
    	mSound.play(1.0f);
    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    	
    }

    public class MyTextInputListener implements Input.TextInputListener {

        @Override
        public void input (String text) {
            Preferences prefs = Gdx.app.getPreferences(".state");
            prefs.putString("playerName", text);
            prefs.flush();
        }

        @Override
        public void canceled () {

        }
    }
}
