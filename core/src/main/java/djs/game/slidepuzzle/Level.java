package djs.game.slidepuzzle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class Level extends Group {
    // tag
    private static final String TAG = Level.class.getSimpleName();

    // enum
    private enum ETile{
        EMPTY, BLOCKED, GOAL
    };

    // variables
    private int m_block_size;
    private int m_field_width;
    private int m_field_height;
    private ETile[][] m_field;
    private TextureRegion m_block_texture_region;
    private Hero m_hero;

    // methods
    public Level(int seed, int block_size){
        Gdx.app.log(TAG, "Level(" + seed + ", " + block_size + ")");

        // values
        this.m_block_size = block_size;

        // generate the level
        this.generate2(new Random(seed));

        // graphics
        this.m_block_texture_region = new TextureRegion(
                new Texture(Gdx.files.internal("block.png"))
        );

        // size
        this.setSize(this.m_field_width * this.m_block_size, this.m_field_height * this.m_block_size);
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

        // calculate the distance
        float distance = (float)Math.sqrt(
                Math.pow((tx - this.m_hero.get_tile_x()) * 48, 2) + Math.pow((ty - this.m_hero.get_tile_y()) * 48, 2)
        );

        // set the position of the hero
        this.m_hero.set_tile_position(tx, ty);

        // animate
        this.m_hero.addAction(Actions.moveTo(
                this.m_hero.get_tile_x() * 48,
                this.m_hero.get_tile_y() * 48,
                distance / (48.0f * 32)
                )
        );
    }

    private void generate(Random rand){
        // generate the size
        this.m_field_width = (int)((720 * 0.85f) / this.m_block_size);
        this.m_field_height = (int)((1280 * 0.85f) / this.m_block_size);
        this.m_field = new ETile[this.m_field_width][this.m_field_height];

        // make the hero and place on the board
        this.m_hero = new Hero(
                rand.nextInt(this.m_field_width),
                rand.nextInt(this.m_field_height),
                this.m_block_size
        );
        this.addActor(this.m_hero);

        // init each block
        for (int y = 0; y < this.m_field_height; ++y){
            for (int x = 0; x < this.m_field_width; ++x){
                int r = rand.nextInt(100);
                if (r < 98){
                    this.m_field[x][y] = ETile.EMPTY;
                }
                else{
                    this.m_field[x][y] = ETile.BLOCKED;
                }
            }
        }
        this.m_field[this.m_hero.get_tile_x()][this.m_hero.get_tile_y()] = ETile.EMPTY;

        // now run some hero simulated moves to find the exit
        int hx = this.m_hero.get_tile_x();
        int hy = this.m_hero.get_tile_y();
        int last_dx = 0;
        int last_dy = 0;
        int num_moves = 5; //rand.nextInt(5) + 5;

        // loop while moves left
        while (num_moves > 0){
            // find a direction to move
            int dx = 0;
            int dy = 0;
            for (int tries = 0; tries < 100; ++tries) {
                switch (rand.nextInt(4)) {
                    case 0: {
                        dx = -1;
                        dy = 0;
                    }
                    break;  // left
                    case 1: {
                        dx = 1;
                        dy = 0;
                    }
                    break;  // right
                    case 2: {
                        dx = 0;
                        dy = 1;
                    }
                    break;  // up
                    case 3: {
                        dx = 0;
                        dy = -1;
                    }
                    break;  // down
                }

                // make sure not same direction we just went
                if (dx == last_dx && dy == last_dy) {
                    // same direction so ignore
                    continue;
                }

                // make sure not where we just came from
                if (dx == -last_dx || dy == -last_dy) {
                    // same direction we just came from
                    continue;
                }

                // make sure the direction is valid on the field
                if (this.is_valid_tile_position(hx + dx, hy + dy) == false){
                    // not valid
                    continue;
                }

                // make sure not blocked
                if (this.m_field[hx + dx][hy + dy] == ETile.BLOCKED){
                    // cant go that direction
                    continue;
                }

                // get here then can go dx, dy direction
                break;
            }

            // make sure we actually got a direction and not just 0, 0
            if (dx == 0 && dy == 0){
                // cant move from this spot so try generate all over again
                this.generate(rand);
                return;
            }

            // so we can move dx, dy and save it as last
            last_dx = dx;
            last_dy = dy;

            // our target distance we want to move
            int target_distance = rand.nextInt(6) + 6;
            int current_distance = 0;

            // keep moving dx, dy until we cant
            while (true){
                // check if we can move
                if (this.is_valid_tile_position(hx + dx, hy + dy) == false){
                    // cant move that direction any more
                    break;
                }
                if (this.m_field[hx + dx][hy + dy] == ETile.BLOCKED){
                    // cant move that direction any more
                    break;
                }

                // move that direction
                hx += dx;
                hy += dy;

                // increment distance
                current_distance += 1;
                if (current_distance == target_distance){
                    // make the next one a block
                    if (this.is_valid_tile_position(hx + dx, hy + dy)){
                        this.m_field[hx + dx][hy + dy] = ETile.BLOCKED;
                    }
                }
            }

            // move complete
            Gdx.app.log(TAG, dx + " " + dy);
            num_moves -= 1;
        }

        // now the hx, hy is the destination
        this.m_field[hx][hy] = ETile.GOAL;
    }

    private void generate2(Random rand){
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
                this.generate2(rand);
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
