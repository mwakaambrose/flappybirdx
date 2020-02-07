package ltd.trustfinity.ambrose.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;


import ltd.trustfinity.ambrose.FlappyBirdRed;

public class GameOverState extends State {

    private Texture bg, gameover, playbtn;
    private int score;
    private static final int TOTAL_SCORE = 9;
    private Array<Texture> scores;

    private ImageButton retryImageButton;

    public GameOverState(GameStateManager gsm, int score) {
        super(gsm);

        cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);
        bg = new Texture("background-day.png");

        gameover = new Texture("gameover.png");
        playbtn = new Texture("message.png");

        this.score = score;

        scores = new Array<>();
        for (int i = 0; i <= TOTAL_SCORE; i++){
            scores.add(new Texture(i+".png"));
        }
    }


    @Override
    protected void handleInput() {

        if (Gdx.input.justTouched()){

//            try {
//                Thread.sleep(2000);
//
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    protected void update(float dt) {
        handleInput();
    }

    @Override
    protected void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(bg, 0, 0);
        spriteBatch.draw(gameover, cam.position.x - (gameover.getWidth() / 2f), cam.position.y);
        spriteBatch.draw(scores.get(score), cam.position.x - (scores.get(score).getWidth() / 2f), cam.position.y + (gameover.getHeight() + 10));
        spriteBatch.end();
    }

    @Override
    public void dispose() {
        bg.dispose();
        gameover.dispose();
    }
}
