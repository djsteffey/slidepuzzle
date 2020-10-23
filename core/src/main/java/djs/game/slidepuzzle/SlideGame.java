package djs.game.slidepuzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SlideGame extends Game implements IGameServices {
	// tag
	private static final String TAG = SlideGame.class.getSimpleName();

	// variables
	private ScreenAbstract m_next_screen;
	private Skin m_ui_skin;

	// methods
	public SlideGame(){
		this.m_next_screen = null;
		this.m_ui_skin = null;
	}

	@Override
	public void create() {
		Gdx.app.log(TAG, "create()");

		// load the ui skin
		this.m_ui_skin = new Skin(Gdx.files.internal("ui/skin.json"));

		// generate some ttf fonts
		int sizes[] = { 24, 32, 48};
		for (int i = 0; i < sizes.length; ++i){
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/droid_serif_bold.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameters.size = sizes[i];
			BitmapFont font = generator.generateFont(parameters);
			this.m_ui_skin.add("font-" + sizes[i], font);
		}

		// set default fonts to size 24 of this ttf
		this.m_ui_skin.get(TextButton.TextButtonStyle.class).font = this.m_ui_skin.getFont("font-32");

		// setup the screen
		this.set_next_screen(new ScreenMain(this));
	}

	@Override
	public void render() {
		if (this.m_next_screen != null){
			this.setScreen(this.m_next_screen);
			this.m_next_screen = null;
		}
		super.render();
	}

	// igameservices
	@Override
	public void set_next_screen(ScreenAbstract screen) {
		Gdx.app.log(TAG, "set_next_screen(" + screen.getClass().getSimpleName() + ")");

		this.m_next_screen = screen;
	}

	@Override
	public Skin get_ui_skin() {
		return this.m_ui_skin;
	}
}