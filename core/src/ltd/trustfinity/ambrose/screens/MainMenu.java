package ltd.trustfinity.ambrose.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
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
import ltd.trustfinity.ambrose.states.GameStateManager;
import ltd.trustfinity.ambrose.states.PlayDayState;

public class MainMenu implements Screen {

    private Texture background;
    private Texture playbtn;

    Stage stage;
    FlappyBirdRed game;

    public MainMenu(FlappyBirdRed game) {
        this.game = game;
        game.cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {

        background = new Texture("background-day.png");
        playbtn = new Texture("message.png");

        Table table = new Table();
        table.setFillParent(true);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //create buttons
        TextButton play = new TextButton("play", skin);
        TextButton info = new TextButton("info", skin);
        TextButton exit = new TextButton("exit", skin);

        play.scaleBy(0.5f);

        //add buttons to table
        table.add(play).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(info).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Game exiting");
                Gdx.app.exit();
            }
        });

        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Playscreen");
                game.setScreen(new Play(game));
            }
        });

        info.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                System.out.println("Infoscreen");
                game.setScreen(new Info(game));
                return true;
            }
        });

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {

        stage.getBatch().setProjectionMatrix(game.cam.combined);
        // clear the screen ready for next set of images to be drawn
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0);
        stage.getBatch().draw(playbtn, game.cam.position.x - (playbtn.getWidth() / 2), game.cam.position.y - 80);
        stage.getBatch().end();

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

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
        background.dispose();
        playbtn.dispose();
        stage.dispose();
        System.out.println("Menu state dispose");
    }
}
