package ltd.trustfinity.ambrose.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public class Bird {

    private static final int GRAVITY = -15;

    private Vector3 position;
    private Vector3 velocity;
    private Texture bird;
    private Rectangle bounds;

    private Animation birdAnimation;
    private Texture birdSprite;

    private static final int MOVEMENT = 100;

    private Sound flap;

    public Bird(int x, int y) {
        this.position = new Vector3(x, y, 0);
        this.velocity = new Vector3(0, 0, 0);

        birdSprite = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(birdSprite), 3, 0.5f);

        bounds = new Rectangle(x, y, birdSprite.getWidth() / 3, birdSprite.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("wing.wav"));
    }

    public void update(float dt){

        birdAnimation.update(dt);

        if (position.y > 0){
            velocity.add(0, GRAVITY, 0);
        }

        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);

        if (position.y < 0){
            position.y = 0;
        }

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public void jump(){
        flap.play(0.3f);
        velocity.y = 250;
    }

    public  Rectangle getBounds(){
        return bounds;
    }

    public void dispose(){
        birdSprite.dispose();
        flap.dispose();
    }

}
