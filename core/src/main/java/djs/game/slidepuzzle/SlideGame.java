package djs.game.slidepuzzle;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;



public class SlideGame extends Game implements IGameServices {
	// tag
	private static final String TAG = SlideGame.class.getSimpleName();

	// variables
	private ScreenAbstract m_next_screen;
	private Skin m_ui_skin;
	private AssetManager m_asset_manager;
	private GameState m_game_state;

	// methods
	public SlideGame(){
		this.m_next_screen = null;
		this.m_ui_skin = null;
		this.m_asset_manager = null;
		this.m_game_state = null;
	}

	@Override
	public void create() {
		Gdx.app.log(TAG, "create()");

		// load the ui skin
		this.m_ui_skin = new Skin(Gdx.files.internal("ui/skin.json"));

		// generate some ttf fonts
		int sizes[] = { 24, 32, 48, 64, 96};
		for (int i = 0; i < sizes.length; ++i){
			FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/droid_serif_bold.ttf"));
			FreeTypeFontGenerator.FreeTypeFontParameter parameters = new FreeTypeFontGenerator.FreeTypeFontParameter();
			parameters.size = sizes[i];
			BitmapFont font = generator.generateFont(parameters);
			this.m_ui_skin.add("font-" + sizes[i], font);
		}

		// set default fonts to size 24 of this ttf
		this.m_ui_skin.get(TextButton.TextButtonStyle.class).font = this.m_ui_skin.getFont("font-32");
		this.m_ui_skin.get(Label.LabelStyle.class).font = this.m_ui_skin.getFont("font-48");
		this.m_ui_skin.get("small", Label.LabelStyle.class).font = this.m_ui_skin.getFont("font-32");
		this.m_ui_skin.get("medium", Label.LabelStyle.class).font = this.m_ui_skin.getFont("font-48");
		this.m_ui_skin.get("large", Label.LabelStyle.class).font = this.m_ui_skin.getFont("font-64");
		this.m_ui_skin.get("default", CheckBox.CheckBoxStyle.class).font = this.m_ui_skin.getFont("font-48");

		// load assets
		this.load_assets();

		// game state
		this.m_game_state = new GameState();

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

	private void load_assets(){
		this.m_asset_manager = new AssetManager();
		this.m_asset_manager.load("cleared.png", Texture.class);
		this.m_asset_manager.load("complete.png", Texture.class);
		this.m_asset_manager.load("block.png", Texture.class);
		this.m_asset_manager.load("particle.png", Texture.class);
		this.m_asset_manager.load("nine.png", Texture.class);
		this.m_asset_manager.load("star.png", Texture.class);
		this.m_asset_manager.load("star_empty.png", Texture.class);
		this.m_asset_manager.finishLoading();
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

	@Override
	public AssetManager get_asset_manager() {
		return this.m_asset_manager;
	}

	@Override
	public GameState get_game_state() {
		return this.m_game_state;
	}
}