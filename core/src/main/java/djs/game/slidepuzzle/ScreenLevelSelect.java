package djs.game.slidepuzzle;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;

public class ScreenLevelSelect extends ScreenAbstract{
    // variables
    private static LevelRangeSelector s_selectors[];
    private Constants.EDifficulty m_difficulty;


    // methods
    public ScreenLevelSelect(IGameServices game_services, Constants.EDifficulty difficulty) {
        super(game_services);

        // save
        this.m_difficulty = difficulty;

        // build lrs if needed
        if (ScreenLevelSelect.s_selectors == null){
            this.build_selectors(game_services.get_ui_skin(), game_services.get_asset_manager());
        }

        // set the percentages
        for (int i = 0; i < 100; ++i){
            int total_stars = 0;
            int complete = 0;
            for (int j = 0; j < 100; ++j){
                byte stars = game_services.get_game_state().get_num_stars(this.m_difficulty, (i * 100) + j + 1);
                if (stars > 0){
                    total_stars += stars;
                    complete += 1;
                }
            }
            float cleared_percent = (complete / 100.0f) * 100;
            float complete_percent = (total_stars / 300.0f) * 100;
            ScreenLevelSelect.s_selectors[i].set_percentages(cleared_percent, complete_percent);
        }

        // table of lrs
        Table holding_table = new Table();
        holding_table.defaults().pad(4.0f);
        for (int i = 0; i < 100; ++i){
            holding_table.add(ScreenLevelSelect.s_selectors[i]);
            holding_table.row();
        }
        ScrollPane sp = new ScrollPane(holding_table);

        // cleared check box
        CheckBox cb = new CheckBox("Show Cleared", this.m_game_services.get_ui_skin());
        cb.setPosition(8, 8 + cb.getHeight() + 4);
        cb.getImage().setScaling(Scaling.fill);
        cb.getImageCell().size(48, 48);
        cb.setChecked(true);
        this.m_stage.addActor(cb);

        // complete check box
        cb = new CheckBox("Show Completed", this.m_game_services.get_ui_skin());
        cb.setPosition(8, 8);
        cb.getImage().setScaling(Scaling.fill);
        cb.getImageCell().size(48, 48);
        cb.setChecked(true);
        this.m_stage.addActor(cb);

        // back button
        TextButton tb = new TextButton("Back", this.m_game_services.get_ui_skin());
        tb.setSize(192, 192 / 2.0f);
        tb.setPosition(720 - 8 - tb.getWidth(), 8);
        tb.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenLevelSelect.this.m_game_services.set_next_screen(
                        new ScreenDifficulty(ScreenLevelSelect.this.m_game_services)
                );
            }
        });
        this.m_stage.addActor(tb);

        // set size and position of scrollpane
        sp.setSize(720 - 8 - 8, 1280 - 8 - cb.getHeight() - 4 - cb.getHeight() - 4 - 8);
        sp.setPosition(8, 8 + cb.getHeight() + 4 + cb.getHeight() + 4);
        sp.debug();
        this.m_stage.addActor(sp);
    }

    private void build_selectors(Skin skin, AssetManager am){
        // allocate the selectors array
        ScreenLevelSelect.s_selectors = new LevelRangeSelector[100];

        // build all 100 of them
        for (int i = 0; i < 100; ++i){
            int start = (i * 100) + 1;
            int end = start + 99;
            ScreenLevelSelect.s_selectors[i] = new LevelRangeSelector(start, end, skin, am);
            ScreenLevelSelect.s_selectors[i].addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    LevelRangeSelector s = (LevelRangeSelector)event.getTarget();
                }
            });
        }
    }
}
