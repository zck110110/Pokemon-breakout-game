package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
/*
 * Author  Group Id: 24
           Group member:
           Siqi Wu (631111)
           Chieh Cheng Lai (589252)
           Chuan Qin (621715)
           Chikai Zhang (644368)
 */
public class GameOver implements Screen {

    private MyGame breakoutGame;

    private TextButton.TextButtonStyle btn_style;
    private TextButton menu, exit;

    private Stage stage;
    private SpriteBatch batch;
    private Texture bg;
    private BitmapFont bf;

    public GameOver(MyGame breakoutGame){ this.breakoutGame = breakoutGame; }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bg, 0, 0);
        bf.setScale(1.2f);
        bf.draw(batch, "Game Over", MyGame.w*2/10, MyGame.h*8/10);
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
        bf = new BitmapFont(Gdx.files.internal("fonts/breakout.fnt"));
        stage = new Stage();
        initBtnStyle();
        initBtn();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Initial button style
     */
    private void initBtnStyle() {

        SpriteDrawable spriteDrawable_btn =
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("images/btn_bg.png"))));

        btn_style = new TextButton.TextButtonStyle();
        btn_style.down = spriteDrawable_btn;
        btn_style.up = spriteDrawable_btn;
        btn_style.font = new BitmapFont(Gdx.files.internal("fonts/breakout.fnt"),
                Gdx.files.internal("fonts/breakout.png"), false);
    }

    /**
     * Initial buttons
     */
    private void initBtn() {

        // "menu" button.
        menu = new TextButton("Menu", btn_style);
        menu.setPosition(MyGame.w/2 - menu.getWidth()/2,
        		MyGame.h/2 + menu.getHeight()*1);
        menu.addListener(new InputListener() {
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

        // "exit" button.
        exit = new TextButton("Exit", btn_style);
        exit.setPosition(MyGame.w/2 - exit.getWidth()/2,
        		MyGame.h/2);
        exit.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                Gdx.app.exit();
                System.exit(0);
            }
        });

        stage.addActor(menu);
        stage.addActor(exit);
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



