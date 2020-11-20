package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import java.util.Random;

public class ScreenDifficulty extends ScreenAbstract{
    // tag
    private static final String TAG = ScreenDifficulty.class.getSimpleName();

    // variables


    // methods
    public ScreenDifficulty(IGameServices game_services) {
        super(game_services);

        Gdx.app.log(TAG, "ScreenDifficulty()");

        // button size
        float button_width = 256.0f;
        float button_height = button_width * Constants.GOLDEN_RATIO;

        // table for the buttons
        Table table = new Table();
        table.defaults().pad(4.0f);

        // easy
        TextButton tb = new TextButton("Easy", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenLevelSelect(ScreenDifficulty.this.m_game_services, Constants.EDifficulty.EASY)
                );
            }
        });
        table.add(tb).size(button_width, button_height);

        // medium
        tb = new TextButton("Medium", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenLevelSelect(ScreenDifficulty.this.m_game_services, Constants.EDifficulty.MEDIUM)
                );
            }
        });
        table.row();
        table.add(tb).size(button_width, button_height);

        // extra medium
        tb = new TextButton("Extra Medium", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenLevelSelect(ScreenDifficulty.this.m_game_services, Constants.EDifficulty.EXTRA_MEDIUM)
                );
            }
        });
        table.row();
        table.add(tb).size(button_width, button_height);

        // hard
        tb = new TextButton("Hard", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenLevelSelect(ScreenDifficulty.this.m_game_services, Constants.EDifficulty.HARD)
                );
            }
        });
        table.row();
        table.add(tb).size(button_width, button_height);

        // back
        tb = new TextButton("Back", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenMain(ScreenDifficulty.this.m_game_services)
                );
            }
        });
        table.row();
        table.add(tb).size(button_width, button_height);

        // position table
        table.setPosition(Constants.SCREEN_WIDTH / 2.0f, Constants.SCREEN_HEIGHT / 2.0f, Align.center);

        // add table
        this.m_stage.addActor(table);
    }
}