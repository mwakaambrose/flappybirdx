package ltd.trustfinity.ambrose;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Objects;

import ltd.trustfinity.ambrose.states.GameStateManager;
import ltd.trustfinity.ambrose.states.MenuState;

public class FlappyBirdRed extends Game {

	// Should only be one in the entire game
	public SpriteBatch batch;
	private BitmapFont font;
	private Texture bg, menubg;

	private GameStateManager gameStateManager;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 800;

	private Music cover;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();
		cover = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		cover.setVolume(0.07f);
		cover.setLooping(true);
		cover.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gameStateManager.push(new MenuState(gameStateManager));
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gameStateManager.update(Gdx.graphics.getDeltaTime());
		gameStateManager.render(batch);
	}

	@Override
	public void dispose() {
		super.dispose();
		cover.stop();
		cover.dispose();
		System.out.println("Music stopped and disposed");
	}
}
