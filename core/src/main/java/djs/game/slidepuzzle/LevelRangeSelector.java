package djs.game.slidepuzzle;

        import com.badlogic.gdx.assets.AssetManager;
        import com.badlogic.gdx.graphics.Color;
        import com.badlogic.gdx.graphics.Texture;
        import com.badlogic.gdx.graphics.g2d.NinePatch;
        import com.badlogic.gdx.scenes.scene2d.Actor;
        import com.badlogic.gdx.scenes.scene2d.ui.Container;
        import com.badlogic.gdx.scenes.scene2d.ui.Image;
        import com.badlogic.gdx.scenes.scene2d.ui.Label;
        import com.badlogic.gdx.scenes.scene2d.ui.Skin;
        import com.badlogic.gdx.scenes.scene2d.ui.Table;
        import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
        import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
        import com.badlogic.gdx.utils.Align;

        import java.util.Locale;

public class LevelRangeSelector extends Container<Table> {
    // variables
    private Label m_label_cleared;
    private Label m_label_complete;

    // methods
    public LevelRangeSelector(int start, int end, Skin skin, AssetManager am){
        // background
        NinePatch np = new NinePatch(am.get("nine.png", Texture.class), 4, 4, 4, 4);
        Drawable background = new NinePatchDrawable(np).tint(Color.ORANGE);

        // label for range
        Label range = new Label(start + " - " + end, skin, "large");
        range.setAlignment(Align.center);

        // icons
        Image icon_cleared = new Image(am.get("cleared.png", Texture.class));
        Image icon_complete = new Image(am.get("complete.png", Texture.class));

        // labels
        this.m_label_cleared = new Label("0.00%", skin, "small");
        this.m_label_cleared.setAlignment(Align.center);
        this.m_label_complete = new Label("0.00%", skin, "small");
        this.m_label_complete.setAlignment(Align.center);

        // table for cleared and complete
        Table cc_table = new Table();
        cc_table.defaults().pad(4.0f);
        cc_table.add(icon_cleared);
        cc_table.add(this.m_label_cleared).width(128.0f);
        cc_table.row();
        cc_table.add(icon_complete);
        cc_table.add(this.m_label_complete).width(128.0f);

        // table
        Table table = new Table();
        table.defaults().pad(4.0f);
        table.setBackground(background);
        table.add(range).width(384.0f);
        table.add(cc_table);

        // set table on the container
        this.setActor(table);
    }

    @Override
    public Actor hit(float x, float y, boolean touchable) {
        if (x < 0){
            return null;
        }
        if (x > this.getWidth()){
            return null;
        }
        if (y < 0){
            return null;
        }
        if (y > this.getHeight()){
            return null;
        }
        return this;
    }

    public void set_percentages(float cleared, float complete){
        this.m_label_cleared.setText(String.format(Locale.US,  "%.2f", cleared) + "%");
        this.m_label_complete.setText(String.format(Locale.US,  "%.2f", complete) + "%");
    }
}
