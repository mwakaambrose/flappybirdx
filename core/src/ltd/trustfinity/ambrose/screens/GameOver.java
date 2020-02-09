package ltd.trustfinity.ambrose.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import ltd.trustfinity.ambrose.FlappyBirdRed;

public class GameOver implements Screen {

    FlappyBirdRed flappyBirdRed;
    Stage stage;

    private Texture bg, gameover;
    private int score;
    private static final int TOTAL_SCORE = 9;
    private Array<Texture> scores;

    private ImageButton retryImageButton;

    public GameOver(FlappyBirdRed flappyBirdRed, int score) {
        this.flappyBirdRed = flappyBirdRed;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        flappyBirdRed.cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);

        this.score = score;

        scores = new Array<>();
        for (int i = 0; i <= TOTAL_SCORE; i++){
            scores.add(new Texture(i+".png"));
        }
    }

    @Override
    public void show() {

        bg = new Texture("background-day.png");

        gameover = new Texture("gameover.png");

        Table table = new Table();
        table.setFillParent(true);

        // temporary until we have asset manager in
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));

        //create buttons
        TextButton post_scores = new TextButton("Post Scores online", skin);
        TextButton retry = new TextButton("Retry", skin);
        TextButton exit = new TextButton("Exit", skin);

        //add buttons to table
        //table.add(post_scores).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(retry).fillX().uniformX();
        table.row();
        table.add(exit).fillX().uniformX();

        // create button listeners
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        post_scores.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                //flappyBirdRed.setScreen(new MainMenu(flappyBirdRed));
            }
        });

        retry.addListener(new InputListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                flappyBirdRed.setScreen(new Play(flappyBirdRed));
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
        stage.getBatch().draw(gameover, flappyBirdRed.cam.position.x - (gameover.getWidth() / 2f), flappyBirdRed.cam.position.y + 90);
        stage.getBatch().draw(scores.get(score), flappyBirdRed.cam.position.x - (scores.get(score).getWidth() / 2f), flappyBirdRed.cam.position.y + (gameover.getHeight() + 100));
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
        gameover.dispose();
        stage.dispose();
    }
}
