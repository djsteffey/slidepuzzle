package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class ScreenMain extends ScreenAbstract{
	// methods
	public ScreenMain(IGameServices game_services) {
		super(game_services);

		// button size
		float button_width = 256.0f;
		float button_height = button_width * Constants.GOLDEN_RATIO;

		// table to hold the buttons
		Table table = new Table();
		table.defaults().pad(4.0f);

		// play button
		TextButton tb = new TextButton("Play", this.m_game_services.get_ui_skin());
		tb.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				ScreenMain.this.m_game_services.set_next_screen(
						new ScreenDifficulty(
								ScreenMain.this.m_game_services
						)
				);
			}
		});
		table.add(tb).size(button_width, button_height);

		// quit button
		tb = new TextButton("Quit", this.m_game_services.get_ui_skin());
		tb.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		table.row();
		table.add(tb).size(button_width, button_height);

		// position table in center
		table.setPosition(Constants.SCREEN_WIDTH / 2.0f, Constants.SCREEN_HEIGHT / 2.0f, Align.center);

		// add it
		this.m_stage.addActor(table);
	}
}