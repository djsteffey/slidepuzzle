package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import java.util.LinkedList;
import java.util.List;

public class Hero extends Actor {
    // tag
    private static final String TAG = Hero.class.getSimpleName();

    // const
    private static final int NUM_BLUR_COUNT = 10;

    // variables
    private int m_tile_x;
    private int m_tile_y;
    private int m_block_size;
    private TextureRegion m_block_texture_region;
    private List<Vector2> m_last_positions;

    // methods
    public Hero(int tile_x, int tile_y, int block_size){
        Gdx.app.log(TAG, "Hero(" + tile_x + ", " + tile_y + ", " + block_size + ")");

        this.setSize(block_size, block_size);
        this.m_tile_x = tile_x;
        this.m_tile_y = tile_y;
        this.m_block_size = block_size;
        this.setPosition(this.m_tile_x * this.m_block_size, this.m_tile_y * this.m_block_size);
        this.m_block_texture_region = new TextureRegion(
                new Texture(Gdx.files.internal("block.png"))
        );

        this.m_last_positions = new LinkedList<>();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // draw older positions
        for (int i = 0; i < this.m_last_positions.size(); ++i){
            Vector2 pos = this.m_last_positions.get(i);
            float alpha = (0.5f / NUM_BLUR_COUNT) * (i + 1);
            batch.setColor(0.0f, 0.0f, 1.0f, alpha);
            batch.draw(this.m_block_texture_region, pos.x, pos.y, this.m_block_size, this.m_block_size);
        }

        // draw current
        batch.setColor(0.0f, 0.0f, 1.0f, 1.0f);
        batch.draw(this.m_block_texture_region, this.getX(), this.getY(), this.m_block_size, this.m_block_size);

        // reset color
        batch.setColor(Color.WHITE);

        // super
        super.draw(batch, parentAlpha);

        // push current positions into last positions
        this.m_last_positions.add(new Vector2(this.getX(), this.getY()));
        if (this.m_last_positions.size() > NUM_BLUR_COUNT){
            this.m_last_positions.remove(0);
        }
    }

    public void set_tile_position(int tile_x, int tile_y){
        this.m_tile_x = tile_x;
        this.m_tile_y = tile_y;
    }

    public int get_tile_x(){
        return this.m_tile_x;
    }

    public int get_tile_y(){
        return this.m_tile_y;
    }
}
