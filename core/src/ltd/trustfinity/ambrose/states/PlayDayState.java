package ltd.trustfinity.ambrose.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.Timer;

import ltd.trustfinity.ambrose.FlappyBirdRed;
import ltd.trustfinity.ambrose.scores.TimeBasedScore;
import ltd.trustfinity.ambrose.sprites.Bird;
import ltd.trustfinity.ambrose.sprites.TubeGreen;

public class PlayDayState extends State {

    private static  final int TUBE_SPACING = 125;
    private static final int TUBE_COUNT = 4;
    private static final int GROUND_Y_OFFSET = -50;

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

    PlayDayState(GameStateManager gsm) {
        super(gsm);
        bird = new Bird(50, 200);
        cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);
        background = new Texture("background-day.png");
        tubeGreen = new TubeGreen(100);
        ground = new Texture("ground.png");

        groundPosition1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPosition2 = new Vector2((cam.position.x - cam.viewportWidth / 2) + ground.getWidth(), GROUND_Y_OFFSET);

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
    public void handleInput() {
        if (Gdx.input.justTouched()){
            bird.jump();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        updateGround();
        bird.update(dt);

        cam.position.x = bird.getPosition().x + 80;

        currentScore = timeBasedScore.getScore();


        for (int i = 0; i < tubes.size; i++){

            TubeGreen tube = tubes.get(i);

            if (cam.position.x - (cam.viewportWidth /2 ) > tube.getPosTopTube().x + tube.getPosTopTube().y) {
                tube.reposition(tube.getPosTopTube().x + ((TubeGreen.TUBE_WIDTH + TUBE_SPACING) * TUBE_COUNT));
            }


            if (tube.collides(bird.getBounds())){
                tubeCollision.play(0.4f);
                //Gdx.input.vibrate(100);
                System.out.println("Bird collided with tube");
                gsm.set(new GameOverState(gsm, currentScore));
                break;
            }
        }

        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET){
            currentScore--;
            death.play(0.5f);
            System.out.println("Bird collided with ground");
            gsm.set(new PlayDayState(gsm));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, cam.position.x - (cam.viewportWidth / 2), cam.position.y - (cam.viewportHeight / 2));
        spriteBatch.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);

        for (TubeGreen tube : tubes) {
            //spriteBatch.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            spriteBatch.draw(tube.getBottomTube(), tube.getPosBottomTube().x, tube.getPosBottomTube().y);
        }

        spriteBatch.draw(scores.get(currentScore), cam.position.x - (scores.get(currentScore).getWidth() / 2f), cam.position.y);

        spriteBatch.draw(ground, groundPosition1.x, groundPosition1.y);
        spriteBatch.draw(ground, groundPosition2.x, groundPosition2.y);

        spriteBatch.end();
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
        if (cam.position.x - (cam.viewportWidth / 2) > groundPosition1.x + ground.getWidth())
            groundPosition1.add(ground.getWidth() * 2, 0);

        if (cam.position.x - (cam.viewportWidth / 2) > groundPosition2.x + ground.getWidth())
            groundPosition2.add(ground.getWidth() * 2, 0);
    }
}
