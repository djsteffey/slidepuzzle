package djs.game.slidepuzzle;

import com.badlogic.gdx.math.GridPoint2;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GridBreadthFirstSearch{
    // callback
    public interface ICallback{
        boolean can_move_to(int x, int y);
    }

    // variables


    // methods
    public GridBreadthFirstSearch(){

    }

    public List<GridPoint2> solve(int width, int height, GridPoint2 start, GridPoint2 goal, ICallback callback){
        // a queue to hold the nodes
        Queue<GridPoint2> queue = new LinkedList<>();

        // visited array
        boolean[][] visited = new boolean[width][height];

        // array for the parent of each position
        GridPoint2[][] parent = new GridPoint2[width][height];

        // add the start node to the queue, mark as visited, with no parent
        queue.add(start.cpy());
        visited[start.x][start.y] = true;
        parent[start.x][start.y] = new GridPoint2(-1, -1);

        // loop forever (until one of the conditions are met that is)
        while (true){
            // check if queue empty
            if (queue.isEmpty()){
                // no path
                return null;
            }

            // get the head item which is the position to move from
            GridPoint2 position = queue.poll();

            // check if it is the goal
            if (position.x == goal.x && position.y == goal.y){
                // all done...create path from goal to start
                List<GridPoint2> path = new ArrayList<>();
                while (true){
                    // add the position
                    path.add(position);

                    // if it is the start then we are done
                    if (position.x == start.x && position.y == start.y){
                        Collections.reverse(path);
                        return path;
                    }

                    // move to the next position
                    position = parent[position.x][position.y];
                }
            }

            // go through the 4 directions
            List<GridPoint2> directions = new ArrayList<GridPoint2>(){{
                add(new GridPoint2(1, 0)); add(new GridPoint2(-1, 0)); add(new GridPoint2(0, -1)); add(new GridPoint2(0, 1));
            }};
            for (GridPoint2 dir : directions){
                // move in that direction until we cant
                int cx = position.x;
                int cy = position.y;
                while (true){
                    // see if we can move another step in the direction
                    if (callback.can_move_to(cx + dir.x, cy + dir.y) == false){
                        // cant move there; so we are as far as we can go
                        break;
                    }

                    // we can move there; check for goal
                    if (cx == goal.x && cy == goal.y){
                        // we can move on it and we are done
                        cx += dir.x;
                        cy += dir.y;
                        break;
                    }

                    // none of the above so can go there
                    cx += dir.x;
                    cy += dir.y;
                }

                // check if was already visited
                if (visited[cx][cy] == false){
                    // not visited; mark visited, set the parent, add to queue
                    visited[cx][cy] = true;
                    parent[cx][cy] = position.cpy();
                    queue.add(new GridPoint2(cx, cy));
                }
            }
        }
    }
}
