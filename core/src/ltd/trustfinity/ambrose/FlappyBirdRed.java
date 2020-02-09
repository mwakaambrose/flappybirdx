package ltd.trustfinity.ambrose;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.Objects;

import ltd.trustfinity.ambrose.screens.GameOver;
import ltd.trustfinity.ambrose.screens.MainMenu;
import ltd.trustfinity.ambrose.states.GameStateManager;
import ltd.trustfinity.ambrose.states.MenuState;

public class FlappyBirdRed extends Game {

	// Should only be one in the entire game
	public SpriteBatch batch;
	private BitmapFont font;
	private Texture bg, menubg;
	private ShapeRenderer shapeRenderer;

	public OrthographicCamera cam;

	GameStateManager gameStateManager;

	public static final int WIDTH = 480;
	public static final int HEIGHT = 1024;

	private Music cover;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		gameStateManager = new GameStateManager();

		cam = new OrthographicCamera();

		cover = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
		cover.setVolume(0.07f);
		cover.setLooping(true);
		cover.play();

		shapeRenderer = new ShapeRenderer();
		font = new BitmapFont();

		Gdx.gl.glClearColor(1, 0, 0, 1);
		setScreen(new MainMenu(this));
	}

	@Override
	public void dispose() {
		super.dispose();
		cover.stop();

		cover.dispose();
		batch.dispose();
		shapeRenderer.dispose();
		font.dispose();

		System.out.println("Music stopped and disposed");
	}
}
