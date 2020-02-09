package ltd.trustfinity.ambrose.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.Timer;

import ltd.trustfinity.ambrose.FlappyBirdRed;
import ltd.trustfinity.ambrose.scores.TimeBasedScore;
import ltd.trustfinity.ambrose.sprites.Bird;
import ltd.trustfinity.ambrose.sprites.TubeGreen;

class Play implements Screen {

    private static  final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;
    private final FlappyBirdRed game;
    Stage stage;

    private Array<TubeGreen> tubes;

    private Bird bird;
    private Texture background;
    private TubeGreen tubeGreen;
    private Texture ground;

    private Timer timer;
    private TimeBasedScore timeBasedScore;

    private int score;
    private int currentScore;
    private static final int TOTAL_SCORE = 9;
    private Array<Texture> scores;

    private Vector2 groundPosition1, groundPosition2;

    private Sound death, tubeCollision, scoreSound;

    TextureAtlas monster;
    Animation<TextureRegion> animation;
    int timePassed;

    Play(FlappyBirdRed game) {
        this.game = game;
        game.cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        background = new Texture("background-day.png");
        tubeGreen = new TubeGreen(100);
        ground = new Texture("ground.png");

        monster = new TextureAtlas(Gdx.files.internal("monster.atlas"));
        animation = new Animation<TextureRegion>(1/5f, monster.getRegions(), Animation.PlayMode.LOOP);

        bird = new Bird(50, 200);

        groundPosition1 = new Vector2(game.cam.position.x - game.cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPosition2 = new Vector2((game.cam.position.x - game.cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

        tubes = new Array<TubeGreen>();


        for (int i = 0; i < TUBE_COUNT; i++){
            tubes.add(new TubeGreen(i * (TUBE_SPACING + TubeGreen.TUBE_WIDTH)));
        }

        death = Gdx.audio.newSound(Gdx.files.internal("hit.wav"));
        tubeCollision = Gdx.audio.newSound(Gdx.files.internal("die.wav"));
        scoreSound = Gdx.audio.newSound(Gdx.files.internal("point.wav"));

        timer = new Timer();
        timeBasedScore = new TimeBasedScore();
        timer.schedule(timeBasedScore, 0, 10000);

        this.score = score;

        scores = new Array<>();
        for (int i = 0; i <= TOTAL_SCORE; i++){
            scores.add(new Texture(i+".png"));
        }
    }

    @Override
    public void render(float delta) {
        stage.getBatch().setProjectionMatrix(game.cam.combined);
        stage.getBatch().begin();
        stage.getBatch().draw(background, game.cam.position.x - (game.cam.viewportWidth / 2), game.cam.position.y - (game.cam.viewportHeight / 2));
        stage.getBatch().draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for (TubeGreen tube : tubes) {
            stage.getBatch().draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            stage.getBatch().draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }

        stage.getBatch().draw(scores.get(currentScore), game.cam.position.x - (scores.get(currentScore).getWidth() / 2f), game.cam.position.y);


        //timePassed += Gdx.graphics.getDeltaTime();
        //stage.getBatch().draw(animation.getKeyFrame(timePassed, true), 300, 200);

        stage.getBatch().draw(ground, groundPosition1.x, groundPosition1.y);
        stage.getBatch().draw(ground, groundPosition2.x, groundPosition2.y);

        stage.getBatch().end();


        handleInput();
        updateGround();
        bird.update(delta);

        game.cam.position.x = bird.getPosition().x + 80;

        currentScore = timeBasedScore.getScore();


        for (int i = 0; i < tubes.size; i++){

            TubeGreen tube = tubes.get(i);

            if (game.cam.position.x - (game.cam.viewportWidth /2 ) > tube.getPosTopTube().x + tube.getPosTopTube().y) {
                tube.reposition(tube.getPosTopTube().x + ((TubeGreen.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }


            if (tube.collides(bird.getBounds())){
                tubeCollision.play(0.4f);
                //Gdx.input.vibrate(100);
                System.out.println("Bird collided with tube");
                game.setScreen(new GameOver(game, currentScore));
                break;
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            currentScore--;
            death.play(0.5f);
            System.out.println("Bird collided with ground");
            game.setScreen(new GameOver(game, currentScore));
        }

        game.cam.update();
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
        bird.dispose();
        background.dispose();

        for (TubeGreen tube : tubes) {
            tube.dispose();
        }

        ground.dispose();
        death.dispose();
        tubeCollision.dispose();
        scoreSound.dispose();

        System.out.println("Play state disposed");
    }

    private void updateGround(){
        if (game.cam.position.x - (game.cam.viewportWidth / 2) > groundPosition1.x + ground.getWidth())
            groundPosition1.add(ground.getWidth() * 2, 0);

        if (game.cam.position.x - (game.cam.viewportWidth / 2) > groundPosition2.x + ground.getWidth())
            groundPosition2.add(ground.getWidth() * 2, 0);
    }

    private void handleInput() {
        if (Gdx.input.justTouched()){
            bird.jump();
        }
    }
}
