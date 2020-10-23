package djs.game.slidepuzzle.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import djs.game.slidepuzzle.SlideGame;

public class Lwjgl3Launcher {
	public static void main(String[] args) {
		// create config
		Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
		configuration.setTitle("Slide");
		configuration.setWindowedMode(720 / 2, 1280 / 2);
		configuration.setResizable(false);
		configuration.setInitialBackgroundColor(Color.BLACK);
		configuration.setWindowPosition(2200, 100);
		configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");

		// create game
		SlideGame game = new SlideGame();

		// run it
		new Lwjgl3Application(game, configuration);
	}
}