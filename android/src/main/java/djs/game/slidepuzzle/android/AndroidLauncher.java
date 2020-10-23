package djs.game.slidepuzzle.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import djs.game.slidepuzzle.SlideGame;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// super
		super.onCreate(savedInstanceState);

		// create config
		AndroidApplicationConfiguration configuration = new AndroidApplicationConfiguration();
		configuration.hideStatusBar = true;
		configuration.useImmersiveMode = true;

		// create game
		SlideGame game = new SlideGame();

		// run it
		initialize(game, configuration);
	}
}