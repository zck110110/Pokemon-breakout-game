package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
public class NewPlayerScreen implements Screen {

    private MyGame breakoutGame;

    private TextButton.TextButtonStyle btn_style, text_style, blank_style;
    private TextButton currentPlayer, changePlayer;
    private TextButton currentPlayerName;
    private TextButton back;

    private String playerName;
    private Stage stage;
    private SpriteBatch batch;
    private Texture bg;

    public NewPlayerScreen(MyGame breakoutGame){ this.breakoutGame = breakoutGame; }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
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
        Preferences prefs = Gdx.app.getPreferences(".state");
        playerName = prefs.getString("playerName");
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

        SpriteDrawable spriteDrawable_text =
                new SpriteDrawable(new Sprite(new Texture(Gdx.files.internal("images/text_bg.png"))));

        text_style = new TextButton.TextButtonStyle();
        text_style.down = spriteDrawable_text;
        text_style.up = spriteDrawable_text;
        text_style.font = new BitmapFont(Gdx.files.internal("fonts/breakout.fnt"),
                Gdx.files.internal("fonts/breakout.png"), false);
        text_style.fontColor = Color.WHITE;

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
    private void initBtn() {
        // "current player" button.
        currentPlayer = new TextButton("Current Player", btn_style);
        currentPlayer.setPosition(MyGame.w/2 - currentPlayer.getWidth()/2,
        		MyGame.h/2 + currentPlayer.getHeight()*3/2);

        // "current player name" placeholder.
        currentPlayerName = new TextButton(playerName, text_style);
        currentPlayerName.setPosition(MyGame.w/2 - currentPlayerName.getWidth()/2,
        		MyGame.h/2 + currentPlayerName.getHeight()*6/10);


        // "change player" button.
        changePlayer = new TextButton("Change Player", btn_style);
        changePlayer.setPosition(MyGame.w/2 - changePlayer.getWidth()/2,
        		MyGame.h/2 - changePlayer.getHeight()*1/2);
        changePlayer.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y,
                                     int pointer, int button) {
            	MenuScreen.buttonSound.play(1.0f);
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y,
                                int pointer, int button) {
                MyTextInputListener listener = new MyTextInputListener();
                Gdx.input.getTextInput(listener, "Please input your name", "");
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

        stage.addActor(currentPlayer);
        stage.addActor(currentPlayerName);
        stage.addActor(changePlayer);
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

    public class MyTextInputListener implements Input.TextInputListener {

        @Override
        public void input (String text) {
            Preferences prefs = Gdx.app.getPreferences(".state");
            prefs.putString("playerName", text);
            prefs.flush();

            GameScreen.MyScore.score = 0;
            show();
        }

        @Override
        public void canceled () {

        }
    }
}


