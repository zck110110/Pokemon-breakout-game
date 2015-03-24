package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
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
public class LeaderboardScreen implements Screen {

    private MyGame breakoutGame;

    /**
     * texture parts
     */
    private TextButton.TextButtonStyle blank_style;
    private TextButton back;
    private Stage stage;
    private SpriteBatch batch;
    private Texture texture, bg;
    private BitmapFont bf;
    
    // get the highest 10 items from server
    public static String[] high=null;

    public LeaderboardScreen(MyGame breakoutGame){ this.breakoutGame = breakoutGame; }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(texture, MyGame.w/6, MyGame.h/4);
        bf.setScale(0.75f);

        for(int i = 0; i < high.length; i++){
            // highest items display
            String rank = (i+1) > 9? "10": "  ".concat(String.valueOf(i+1));
            bf.draw(batch, rank, MyGame.w*20/100, MyGame.h*81/100 - 68 * i);
            String name = high[i] == null? "  ---": high[i].split(",")[0];
            bf.draw(batch, name, MyGame.w*37/100, MyGame.h*81/100 - 68 * i);
            String score = high[i] == null? " ---": high[i].split(",")[1];
            bf.draw(batch, score, MyGame.w*60/100, MyGame.h*81/100 - 68 * i);
        }
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
        texture = new Texture(Gdx.files.internal("images/leaderboard_bg.png"));
        bf = new BitmapFont(Gdx.files.internal("fonts/breakout.fnt"));
        stage = new Stage();
        initBtnStyle();
        setBackBtn();
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Initial button style
     */
    private void initBtnStyle() {

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
    private void setBackBtn() {

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

        stage.addActor(back);
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


