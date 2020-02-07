package ltd.trustfinity.ambrose.states;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ltd.trustfinity.ambrose.FlappyBirdRed;

public class MenuState extends State {

    private Texture background;
    private Texture playbtn;

    public MenuState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(false, FlappyBirdRed.WIDTH / 2, FlappyBirdRed.HEIGHT / 2);
        background = new Texture("background-day.png");
        playbtn = new Texture("message.png");
    }

    @Override
    public void handleInput() {
        if (Gdx.input.justTouched()){
            //gsm.set(new GameOverState(gsm));
            gsm.set(new PlayDayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.setProjectionMatrix(cam.combined);
        spriteBatch.begin();
        spriteBatch.draw(background, 0, 0);
        spriteBatch.draw(playbtn, cam.position.x - (playbtn.getWidth() / 2), cam.position.y);
        spriteBatch.end();
    }

    @Override
    protected void dispose() {
        background.dispose();
        playbtn.dispose();
        System.out.println("Menu state dispose");
    }
}
