package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

import java.util.Random;

public class ScreenDifficulty extends ScreenAbstract{
    // tag
    private static final String TAG = ScreenDifficulty.class.getSimpleName();

    // variables
    private Group m_buttons_group;

    // methods
    public ScreenDifficulty(IGameServices game_services) {
        super(game_services);

        Gdx.app.log(TAG, "ScreenDifficulty()");

        // button size
        int button_width = 256;
        int button_height = 128;
        int button_separation = 8;

        // buttons
        this.m_buttons_group = new Group();
        this.m_buttons_group.setSize(button_width,(4 * button_height) + (3 * button_separation));
        this.m_buttons_group.setPosition(720 / 2, 1280 / 2, Align.center);
        this.m_stage.addActor(this.m_buttons_group);

        // easy
        TextButton tb = new TextButton("Easy", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenPlay(
                                ScreenDifficulty.this.m_game_services,
                                Math.abs(new Random().nextInt()),
                                Level.ELevelDifficulty.EASY
                        )
                );
            }
        });
        this.m_buttons_group.addActor(tb);

        // medium
        tb = new TextButton("Medium", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenPlay(
                                ScreenDifficulty.this.m_game_services,
                                Math.abs(new Random().nextInt()),
                                Level.ELevelDifficulty.MEDIUM
                        )
                );
            }
        });
        this.m_buttons_group.addActor(tb);

        // hard
        tb = new TextButton("Hard", this.m_game_services.get_ui_skin());
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenDifficulty.this.m_game_services.set_next_screen(
                        new ScreenPlay(
                                ScreenDifficulty.this.m_game_services,
                                Math.abs(new Random().nextInt()),
                                Level.ELevelDifficulty.HARD
                        )
                );
            }
        });
        this.m_buttons_group.addActor(tb);

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
        this.m_buttons_group.addActor(tb);

        // set their sizes
        for (int i = 0; i < this.m_buttons_group.getChildren().size; ++i){
            Actor a = this.m_buttons_group.getChild(i);
            a.setSize(button_width, button_height);
            a.setPosition(0, this.m_buttons_group.getHeight() - ((i + 1) * button_height) - (i * button_separation));
        }
    }
}