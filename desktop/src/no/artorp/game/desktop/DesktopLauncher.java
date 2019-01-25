package no.artorp.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import no.artorp.game.FlappyBirdGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = FlappyBirdGame.WIDTH;
		config.height = FlappyBirdGame.HEIGHT;
		config.title = FlappyBirdGame.TITLE;
		config.x = 850;
		config.y = 20;
		new LwjglApplication(new FlappyBirdGame(), config);
	}
}
