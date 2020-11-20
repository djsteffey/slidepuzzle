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

public class LevelSelector extends Container<Table> {
    // variables
    private Table m_table;
    private Label m_level_label;
    private Image m_star_image;
    private Image m_star_empty_image;

    // methods
    public LevelSelector(int level, byte stars, Skin skin, AssetManager am){
        // background
        NinePatch np = new NinePatch(am.get("nine.png", Texture.class), 4, 4, 4, 4);
        Drawable background = new NinePatchDrawable(np).tint(Color.ORANGE);

        // label for level
        this.m_level_label = new Label("", skin, "large");
        this.m_level_label.setAlignment(Align.center);

        // star
        this.m_star_image = new Image(am.get("star.png", Texture.class));
        this.m_star_empty_image = new Image(am.get("star_empty.png", Texture.class));

        // table
        this.m_table = new Table();
        this.m_table.defaults().pad(4.0f);
        this.m_table.setBackground(background);

        // set the level and stars
        this.set_level_and_stars(level, stars);

        // set table on the container
        this.setActor(this.m_table);
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

    public void set_level_and_stars(int level, byte stars){
        // clear the table
        this.m_table.clearChildren();

        // add the level label
        this.m_level_label.setText("" + level);
        this.m_table.add(this.m_level_label).width(384.0f);

        for (int i = 0; i < 3; ++i){
            if (stars < i + 1){
                table.add(star_empty);
            }
            else{
                table.add(star);
            }
        }
    }
}
