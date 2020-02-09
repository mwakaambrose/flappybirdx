package ltd.trustfinity.ambrose.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ltd.trustfinity.ambrose.FlappyBirdRed;

public class Info implements Screen {

    FlappyBirdRed flappyBirdRed;
    Stage stage;

    private Texture bg;

    BitmapFont title, instruction, company;

    Info(FlappyBirdRed game) {
        this.flappyBirdRed = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        flappyBirdRed.cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);
    }

    @Override
    public void show() {
        bg = new Texture("background-day.png");

        title = new BitmapFont();
        instruction = new BitmapFont();
        company = new BitmapFont();

        Table table = new Table();
        table.bottom();
        table.setFillParent(true);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //create buttons
        TextButton retry = new TextButton("Main Menu", skin);
        TextButton exit = new TextButton("Exit", skin);

        //add buttons to table
        table.row().pad(10, 0, 10, 0);
        table.add(retry).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX().padBottom(60);

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        retry.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                flappyBirdRed.setScreen(new MainMenu(flappyBirdRed));
                return true;
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        stage.getBatch().setProjectionMatrix(flappyBirdRed.cam.combined);
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(bg, 0, 0);
        title.draw(stage.getBatch(), "Instructions", flappyBirdRed.cam.position.x - 100, flappyBirdRed.cam.position.y + 150);
        title.draw(stage.getBatch(), "Tap! Tap, \npass through the tubes and \nscore as \nhigh as you can. \nColliding with the tube ends \nthe game with your current score. \nColliding with the ground ends the game \nbut with one score less. \nTap away!.", flappyBirdRed.cam.position.x - 100, flappyBirdRed.cam.position.y + 100);
        title.draw(stage.getBatch(), "2020 Trustfinity Tech", flappyBirdRed.cam.position.x - 100, flappyBirdRed.cam.position.y - 80);
        stage.getBatch().end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // change the stage's viewport when teh screen size is changed
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        bg.dispose();
        stage.dispose();
    }
}
