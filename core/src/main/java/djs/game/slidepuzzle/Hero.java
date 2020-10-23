package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Hero extends Actor {
    // tag
    private static final String TAG = Hero.class.getSimpleName();

    // variables
    private int m_tile_x;
    private int m_tile_y;
    private int m_block_size;
    private TextureRegion m_block_texture_region;

    // methods
    public Hero(int tile_x, int tile_y, int block_size){
        Gdx.app.log(TAG, "Hero(" + tile_x + ", " + tile_y + ", " + block_size + ")");

        this.m_tile_x = tile_x;
        this.m_tile_y = tile_y;
        this.m_block_size = block_size;
        this.setPosition(this.m_tile_x * this.m_block_size, this.m_tile_y * this.m_block_size);
        this.m_block_texture_region = new TextureRegion(
                new Texture(Gdx.files.internal("block.png"))
        );
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(Color.BLUE);
        batch.draw(this.m_block_texture_region, this.getX(), this.getY(), this.m_block_size, this.m_block_size);
        batch.setColor(Color.WHITE);
        super.draw(batch, parentAlpha);
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
