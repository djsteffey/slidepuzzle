package djs.game.slidepuzzle;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GridBreadthFirstSearch{
    // variables


    // methods
    public GridBreadthFirstSearch(){

    }

    public void solve(int width, int height, GridPoint2 start, GridPoint2 goal){
        // a queue to hold the nodes
        Queue<GridPoint2> queue = new LinkedList<>();

        // add the start node
        queue.add(start.cpy());

        // visited array
        boolean[][] visited = new boolean[width][height];

        // array for the parent of each position
        GridPoint2[][] parent = new GridPoint2[width][height];

        // mark the start as visited
        visited[start.x][start.y] = true;

        // loop forever (until one of the conditions are met that is)
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
            if (this.m_field[position.get_x()][position.get_y()] == Level.ETile.GOAL){
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
                    if (this.m_field[cx + dir.get_x()][cy + dir.get_y()] == Level.ETile.BLOCKED){
                        // cant move there so cx, cy is as far as we can go
                        break;
                    }
                    if (this.m_field[cx + dir.get_x()][cy + dir.get_y()] == Level.ETile.GOAL){
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
    }
}
