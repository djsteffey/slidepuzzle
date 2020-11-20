package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class ScreenPlay extends ScreenAbstract implements GestureDetector.GestureListener {
    // tag
    private static final String TAG = ScreenPlay.class.getSimpleName();

    // variables
    private Level m_level;
    private Label m_num_moves_label;
    private TextButton m_button_reset;
    private TextButton m_button_next;
    private TextButton m_button_quit;

    // methods
    public ScreenPlay(IGameServices game_services, int seed, Level.ELevelDifficulty difficulty) {
        super(game_services);

        Gdx.app.log(TAG, "ScreenPlay()");

        // create the level
        this.generate_level(seed, difficulty);

        // reset button
        this.m_button_reset = new TextButton("Reset", this.m_game_services.get_ui_skin());
        this.m_button_reset.setSize(128, 64);
        this.m_button_reset.setPosition(720 - 8 - this.m_button_reset.getWidth() - 8 - this.m_button_reset.getWidth() - 8 - this.m_button_reset.getWidth(), 8);
        this.m_button_reset.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenPlay.this.generate_level(ScreenPlay.this.m_level.get_seed(), ScreenPlay.this.m_level.get_difficulty());
            }
        });
        this.m_stage.addActor(this.m_button_reset);

        // next Button
        this.m_button_next = new TextButton("Next", this.m_game_services.get_ui_skin());
        this.m_button_next.setSize(128, 64);
        this.m_button_next.setPosition(720 - 8 - this.m_button_next.getWidth() - 8 - this.m_button_next.getWidth(), 8);
        this.m_button_next.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenPlay.this.generate_level(ScreenPlay.this.m_level.get_seed() + 1, ScreenPlay.this.m_level.get_difficulty());
            }
        });
        this.m_stage.addActor(this.m_button_next);

        // quit
        this.m_button_quit = new TextButton("Quit", this.m_game_services.get_ui_skin());
        this.m_button_quit.setSize(128, 64);
        this.m_button_quit.setPosition(720 - 8 - this.m_button_quit.getWidth(), 8);
        this.m_button_quit.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenPlay.this.m_game_services.set_next_screen(new ScreenMain(ScreenPlay.this.m_game_services));
            }
        });
        this.m_stage.addActor(this.m_button_quit);

        // num moves
        this.m_num_moves_label = new Label("Moves: 0\nOptimal: " + this.m_level.get_optimal_moves_count(), this.m_game_services.get_ui_skin());
        this.m_num_moves_label.setSize(256, 128);
        this.m_num_moves_label.setAlignment(Align.center);
        this.m_num_moves_label.setPosition(720 / 2.0f, 1280 - this.m_num_moves_label.getHeight() / 2 - 8, Align.center);
        this.m_stage.addActor(this.m_num_moves_label);

        // other
        this.m_stage.setDebugAll(true);
    }

    @Override
    public void render(float delta) {
        this.m_num_moves_label.setText("Moves: " + this.m_level.get_num_moves() + "\nOptimal: " + this.m_level.get_optimal_moves_count());
        super.render(delta);
    }

    @Override
    public void show() {
        Gdx.app.log(TAG, "show()");

        InputMultiplexer im = new InputMultiplexer();
        im.addProcessor(new GestureDetector(this));
        im.addProcessor(this.m_stage);
        Gdx.input.setInputProcessor(im);
    }

    private void generate_level(int seed, Level.ELevelDifficulty difficulty){
        // remove existing
        if (this.m_level != null){
            this.m_level.remove();
        }

        // create new
        this.m_level = new Level(
                new Level.ILevelListener() {
                    @Override
                    public void on_complete(Level level) {
                        ScreenPlay.this.m_button_reset.setTouchable(Touchable.disabled);
                        ScreenPlay.this.m_button_reset.setDisabled(true);
                        ScreenPlay.this.m_button_next.setTouchable(Touchable.disabled);
                        ScreenPlay.this.m_button_next.setDisabled(true);
                        ScreenPlay.this.m_button_quit.setTouchable(Touchable.disabled);
                        ScreenPlay.this.m_button_quit.setDisabled(true);
                        // show dialog box
                        LevelCompleteDialog dlg = new LevelCompleteDialog(
                                new LevelCompleteDialog.ILevelCompleteDialogListener() {
                                    @Override
                                    public void on_retry(LevelCompleteDialog dlg) {
                                        ScreenPlay.this.generate_level(ScreenPlay.this.m_level.get_seed(), ScreenPlay.this.m_level.get_difficulty());
                                        dlg.remove();
                                        ScreenPlay.this.m_button_reset.setTouchable(Touchable.enabled);
                                        ScreenPlay.this.m_button_reset.setDisabled(false);
                                        ScreenPlay.this.m_button_next.setTouchable(Touchable.enabled);
                                        ScreenPlay.this.m_button_next.setDisabled(false);
                                        ScreenPlay.this.m_button_quit.setTouchable(Touchable.enabled);
                                        ScreenPlay.this.m_button_quit.setDisabled(false);
                                    }

                                    @Override
                                    public void on_next(LevelCompleteDialog dlg) {
                                        ScreenPlay.this.generate_level(ScreenPlay.this.m_level.get_seed() + 1, ScreenPlay.this.m_level.get_difficulty());
                                        dlg.remove();
                                        ScreenPlay.this.m_button_reset.setTouchable(Touchable.enabled);
                                        ScreenPlay.this.m_button_reset.setDisabled(false);
                                        ScreenPlay.this.m_button_next.setTouchable(Touchable.enabled);
                                        ScreenPlay.this.m_button_next.setDisabled(false);
                                        ScreenPlay.this.m_button_quit.setTouchable(Touchable.enabled);
                                        ScreenPlay.this.m_button_quit.setDisabled(false);
                                    }

                                    @Override
                                    public void on_quit(LevelCompleteDialog dlg) {
                                        ScreenPlay.this.m_game_services.set_next_screen(new ScreenMain(ScreenPlay.this.m_game_services));
                                    }
                                },
                                ScreenPlay.this.m_game_services.get_ui_skin(),
                                ScreenPlay.this.m_level.get_num_moves()
                        );
                        dlg.setPosition(720 / 2.0f, 1280 / 2.0f, Align.center);
                        ScreenPlay.this.m_stage.addActor(dlg);
                    }
                },
                seed,
                difficulty
        );
        this.m_level.setPosition(720.0f / 2, 1280.0f / 2, Align.center);
        this.m_stage.addActor(this.m_level);

        // make sure it is in the back
        this.m_level.toBack();
    }

    // gesturelistener
    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        Gdx.app.log(TAG, "fling(" + velocityX + ", " + velocityY + ", " + button + ")");

        if (Math.abs(velocityX) > Math.abs(velocityY)){
            if (velocityX < -350.0f){
                // left
                this.m_level.fling_direction(-1, 0);
            }
            else if (velocityX > 350.0f){
                // right
                this.m_level.fling_direction(1, 0);
            }
        }
        else{
            if (velocityY < -350.0f){
                // up
                this.m_level.fling_direction(0, 1);
            }
            else if (velocityY > 350.0f){
                // down
                this.m_level.fling_direction(0, -1);
            }
        }
        return true;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }

    @Override
    public void pinchStop() {

    }
}