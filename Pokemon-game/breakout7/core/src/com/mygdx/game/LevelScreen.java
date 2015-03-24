package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
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
public class LevelScreen implements Screen {

    private MyGame breakoutGame;
    
    // set up connected screen
    GameScreen gameScreen;

    private TextButton.TextButtonStyle btn_style, blank_style;
    private TextButton level1, level2, level3, level4, level5;
    private TextButton back;
    private Stage stage;
    private SpriteBatch batch;
    private Texture bg;

    public LevelScreen(MyGame breakoutGame){
        this.breakoutGame = breakoutGame;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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

        SpriteDrawable spriteDrawable_blank =
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("images/blank_bg.png"))));

        blank_style = new TextButton.TextButtonStyle();
        blank_style.down = spriteDrawable_blank;
        blank_style.up = spriteDrawable_blank;
        blank_style.font = new BitmapFont(Gdx.files.internal("fonts/breakout.fnt"),
                Gdx.files.internal("fonts/breakout.png"), false);
    }

    /**
     * Initial buttons
     */
    private void initBtn(){
        // "level1" button.
        level1 = new TextButton("Level1", btn_style);
        level1.setPosition(MyGame.w/2 - level1.getWidth()/2,
        		MyGame.h/2 + level1.getHeight()*5/2);
        level1.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(1);
            }
        });

        // "level2" button.
        level2 = new TextButton("Level2", btn_style);
        level2.setPosition(MyGame.w/2 - level2.getWidth()/2,
        		MyGame.h/2 + level2.getHeight()*3/2);
        level2.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(2);
            }
        });

        // "level3" button.
        level3 = new TextButton("Level3", btn_style);
        level3.setPosition(MyGame.w/2 - level3.getWidth()/2,
        		MyGame.h/2 + level3.getHeight()*1/2);
        level3.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                setScreen(3);
            }
        });

        // "level4" button.
        level4 = new TextButton("Level4", btn_style);
        level4.setPosition(MyGame.w/2 - level4.getWidth()/2,
        		MyGame.h/2 - level4.getHeight()*1/2);
        level4.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
//                setScreen(4);
            }
        });

        // "level5" button.
        level5 = new TextButton("Level5", btn_style);
        level5.setPosition(MyGame.w/2 - level5.getWidth()/2,
        		MyGame.h/2 - level5.getHeight()*3/2);
        level5.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
//                setScreen(5);
            }
        });

        // "Back" button.
        back = new TextButton("Back", blank_style);
        back.setPosition(6, 4);
        back.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                breakoutGame.setScreen(breakoutGame.menuScreen);
            }
        });

        stage.addActor(level1);
        stage.addActor(level2);
        stage.addActor(level3);
        stage.addActor(level4);
        stage.addActor(level5);
        stage.addActor(back);
    }

    private void setScreen(int levelNum) {
        switch (levelNum) {
            case 1:
                gameScreen = new GameScreen(breakoutGame.getmClient(), breakoutGame, 1);
                breakoutGame.setScreen(gameScreen);
                break;
            case 2:
                gameScreen = new GameScreen(breakoutGame.getmClient(), breakoutGame, 2);
                breakoutGame.setScreen(gameScreen);
                break;
            case 3:
                gameScreen = new GameScreen(breakoutGame.getmClient(), breakoutGame, 3);
                breakoutGame.setScreen(gameScreen);
                break;
            case 4:
                gameScreen = new GameScreen(breakoutGame.getmClient(), breakoutGame, 4);
                breakoutGame.setScreen(gameScreen);
                break;
            case 5:
                gameScreen = new GameScreen(breakoutGame.getmClient(), breakoutGame, 5);
                breakoutGame.setScreen(gameScreen);
                break;
            default:
                break;
        }
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
}
