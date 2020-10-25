package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Level extends Group {
    // listener
    public interface ILevelListener{
        void on_complete(Level level);
    }

    // enum
    public enum ELevelDifficulty{
        EASY,
        MEDIUM,
        HARD
    }
    private enum ETile{
        EMPTY, BLOCKED, GOAL
    };

    // tag
    private static final String TAG = Level.class.getSimpleName();

    // variables
    private ILevelListener m_listener;
    private int m_block_size;
    private int m_field_width;
    private int m_field_height;
    private ETile[][] m_field;
    private TextureRegion m_block_texture_region;
    private Hero m_hero;
    private boolean m_is_moving;
    private boolean m_is_over;
    private int m_num_moves;
    private int m_seed;
    private ELevelDifficulty m_difficulty;
    private ParticleEffect m_pe;

    // methods
    public Level(ILevelListener listener, int seed, ELevelDifficulty difficulty){
        Gdx.app.log(TAG, "Level(" + seed + ", " + difficulty + ")");

        // save values
        this.m_listener = listener;
        this.m_seed = seed;
        this.m_difficulty = difficulty;

        // setup block size based on difficulty
        switch (this.m_difficulty){
            case EASY: { this.m_block_size = 48; } break;
            case MEDIUM: { this.m_block_size = 32; } break;
            case HARD: { this.m_block_size = 16; } break;
            default: { this.m_block_size = 96; } break;
        }

        // generate the level
        Random rand = new Random(this.m_seed);
        this.generate(rand);

        // graphics for blocks
        this.m_block_texture_region = new TextureRegion(
                new Texture(Gdx.files.internal("block.png"))
        );

        // size of the level group object
        this.setSize(this.m_field_width * this.m_block_size, this.m_field_height * this.m_block_size);

        // other
        this.m_is_over = false;
        this.m_is_moving = false;
        this.m_num_moves = 0;

        this.m_pe = new ParticleEffect();
        this.m_pe.load(Gdx.files.internal("particles/test"), Gdx.files.internal(""));
        this.m_pe.start();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // draw field
        for (int y = 0; y < this.m_field_height; ++y){
            for (int x = 0; x < this.m_field_width; ++x){
                switch (this.m_field[x][y]){
                    case EMPTY:{

                    } break;
                    case BLOCKED:{
                        batch.setColor(Color.RED);
                        batch.draw(this.m_block_texture_region, this.getX() + x * this.m_block_size, this.getY() + y * this.m_block_size, this.m_block_size, this.m_block_size);
                    } break;
                    case GOAL:{
                        batch.setColor(Color.GREEN);
                        batch.draw(this.m_block_texture_region, this.getX() + x * this.m_block_size, this.getY() + y * this.m_block_size, this.m_block_size, this.m_block_size);
                    } break;
                }
            }
        }
        batch.setColor(Color.WHITE);

        this.m_pe.getEmitters().first().setPosition(this.m_hero.getX() + this.m_hero.getWidth() / 2.0f + this.getX(), this.m_hero.getY() + this.m_hero.getHeight() / 2.0f + this.getY());
        this.m_pe.update(Gdx.graphics.getDeltaTime());
        this.m_pe.draw(batch);
        if (this.m_pe.isComplete()){
            this.m_pe.reset();
        }

        super.draw(batch, parentAlpha);


    }

    private boolean is_valid_tile_position(int tile_x, int tile_y){
        if (tile_x < 0){
            return false;
        }
        if (tile_x > this.m_field_width - 1){
            return false;
        }
        if (tile_y < 0){
            return false;
        }
        if (tile_y > this.m_field_height - 1){
                return false;
        }
        return true;
    }

    public ETile get_field_value(int tile_x, int tile_y){
        return this.m_field[tile_x][tile_y];
    }

    public void fling_direction(int dx, int dy){
        // checks
        if (this.m_is_over){
            return;
        }
        if (this.m_is_moving){
            return;
        }

        // initial target is the current hero position
        int tx = this.m_hero.get_tile_x();
        int ty = this.m_hero.get_tile_y();

        // see how far we can go before running into obstacle
        while (true){
            // check if it is a valid position
            if (this.is_valid_tile_position(tx + dx, ty + dy) == false){
                // not valid so break
                break;
            }

            // it is valid; see if open
            if (this.get_field_value(tx + dx, ty + dy) == ETile.EMPTY) {
                // empty so can move there
                tx += dx;
                ty += dy;
            }
            else if (this.get_field_value(tx + dx, ty + dy) == ETile.GOAL) {
                tx += dx;
                ty += dy;
                break;
            }
            else{
                // blocked
                break;
            }
        }

        // see if we actually moved
        if (tx == this.m_hero.get_tile_x() && ty == this.m_hero.get_tile_y()){
            // didnt actually move
            return;
        }

        // calculate the distance
        float distance = (float)Math.sqrt(
                Math.pow((tx - this.m_hero.get_tile_x()) * this.m_block_size, 2) + Math.pow((ty - this.m_hero.get_tile_y()) * this.m_block_size, 2)
        );

        // set the position of the hero
        this.m_hero.set_tile_position(tx, ty);

        // flag as moving
        this.m_is_moving = true;
        this.m_num_moves += 1;

        // animate
        float duration = distance / (1088.0f * 1.25f);
        this.m_hero.addAction(
                Actions.sequence(
                        Actions.moveTo(
                                this.m_hero.get_tile_x() * this.m_block_size,
                                this.m_hero.get_tile_y() * this.m_block_size,
                                duration
                        ),
                        new Action() {
                            @Override
                            public boolean act(float delta) {
                                Level.this.m_is_moving = false;
                                if (Level.this.m_field[Level.this.m_hero.get_tile_x()][Level.this.m_hero.get_tile_y()] == ETile.GOAL){
                                    Level.this.m_is_over = true;
                                    Level.this.m_listener.on_complete(Level.this);
                                }
                                return true;
                            }
                        }
                )
        );
    }

    public int get_num_moves(){
        return this.m_num_moves;
    }

    public int get_seed(){
        return this.m_seed;
    }

    public ELevelDifficulty get_difficulty(){
        return this.m_difficulty;
    }

    private void generate(Random rand){
        // generate the size
        this.m_field_width = (int)((720 * 0.85f) / this.m_block_size);
        this.m_field_height = (int)((1280 * 0.85f) / this.m_block_size);
        this.m_field = new ETile[this.m_field_width][this.m_field_height];

        // rand start position on bottom
        int sx = rand.nextInt(this.m_field_width);
        int sy = 0;

        // rand end position on top
        int ex = rand.nextInt(this.m_field_width);
        int ey = rand.nextInt(this.m_field_height / 2) + this.m_field_height / 2;//this.m_field_height - 1;

        // fill the field
        for (int y = 0; y < this.m_field_height; ++y){
            for (int x = 0; x < this.m_field_width; ++x){
                // 12.5% chance to fill
                int r = rand.nextInt(1000);
                if (r < 100){
                    this.m_field[x][y] = ETile.BLOCKED;
                }
                else{
                    this.m_field[x][y] = ETile.EMPTY;
                }
            }
        }
        this.m_field[sx][sy] = ETile.EMPTY;
        this.m_field[ex][ey] = ETile.GOAL;

        // bfs solver
        Queue<TilePosition> queue = new LinkedList<>();
        queue.add(new TilePosition(sx, sy));
        boolean[][] visited = new boolean[this.m_field_width][this.m_field_height];
        visited[sx][sy] = true;
        while (true){
            // check if queue empty
            if (queue.isEmpty()){
                // empty queue and didnt find goal....so try again
                this.generate(rand);
                return;
            }

            // get the head item which is the position to move from
            TilePosition position = queue.poll();

            // check if it is the goal
            if (this.m_field[position.get_x()][position.get_y()] == ETile.GOAL){
                // all done
                break;
            }

            // go through the 4 directions
            List<TilePosition> directions = new ArrayList<TilePosition>(){{
                add(new TilePosition(1, 0)); add(new TilePosition(-1, 0)); add(new TilePosition(0, -1)); add(new TilePosition(0, 1));
            }};
            for (TilePosition dir : directions){
                // move in that direction until we cant
                int cx = position.get_x();
                int cy = position.get_y();
                while (true){
                    if (this.is_valid_tile_position(cx + dir.get_x(), cy + dir.get_y()) == false){
                        // cant move there so cx, cy is as far as we can go
                        break;
                    }
                    if (this.m_field[cx + dir.get_x()][cy + dir.get_y()] == ETile.BLOCKED){
                        // cant move there so cx, cy is as far as we can go
                        break;
                    }
                    if (this.m_field[cx + dir.get_x()][cy + dir.get_y()] == ETile.GOAL){
                        // we can move on it and we are done
                        cx += dir.get_x();
                        cy += dir.get_y();
                        break;
                    }

                    // none of the above so move
                    cx += dir.get_x();
                    cy += dir.get_y();
                }

                // check if was already visited
                if (visited[cx][cy] == false){
                    visited[cx][cy] = true;
                    queue.add(new TilePosition(cx, cy));
                }
            }
        }

        // make hero
        this.m_hero = new Hero(
                sx,
                sy,
                this.m_block_size
        );
        this.addActor(this.m_hero);
    }
}
