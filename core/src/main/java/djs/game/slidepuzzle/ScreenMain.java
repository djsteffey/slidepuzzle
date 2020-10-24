package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.Random;

public class ScreenMain extends ScreenAbstract{
	// tag
	private static final String TAG = ScreenMain.class.getSimpleName();

	// methods
	public ScreenMain(IGameServices game_services) {
		super(game_services);

		Gdx.app.log(TAG, "ScreenMain()");

		TextButton tb = new TextButton("Play", this.m_game_services.get_ui_skin());
		tb.setSize(256, 128);
		tb.setPosition((720 - tb.getWidth()) / 2, (1280 - tb.getHeight()) / 2);
		tb.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenMain.this.m_game_services.set_next_screen(
						new ScreenPlay(
								ScreenMain.this.m_game_services,
								Math.abs(new Random().nextInt())
						)
				);
			}
		});
		this.m_stage.addActor(tb);

		tb = new TextButton("Quit", this.m_game_services.get_ui_skin());
		tb.setSize(256, 128);
		tb.setPosition((720 - tb.getWidth()) / 2, (1280 - tb.getHeight()) / 2 - tb.getHeight() - 8);
		tb.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		this.m_stage.addActor(tb);
	}
}