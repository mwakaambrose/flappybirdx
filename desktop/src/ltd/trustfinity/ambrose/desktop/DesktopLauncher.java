package ltd.trustfinity.ambrose.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import ltd.trustfinity.ambrose.FlappyBirdRed;

public class DesktopLauncher {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyBirdRed.WIDTH;
		config.height = FlappyBirdRed.HEIGHT;
		new LwjglApplication(new FlappyBirdRed(), config);
	}
}
