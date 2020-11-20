package djs.game.slidepuzzle;

import java.util.Random;

public class GameState {
    // variables
    private byte m_stars[][];

    // methods
    public GameState(){
        this.m_stars = new byte[Constants.EDifficulty.values().length][Constants.NUM_LEVELS];

        Random rand = new Random();
        for (int i = 0; i < Constants.EDifficulty.values().length; ++i){
            for (int j = 0; j < Constants.NUM_LEVELS; ++j){
                this.m_stars[i][j] = (byte)(rand.nextInt() % 4);
            }
        }
    }

    public byte get_num_stars(Constants.EDifficulty difficulty, int level){
        return this.m_stars[difficulty.ordinal()][level - 1];
    }

    public void set_num_stars(Constants.EDifficulty difficulty, int level, byte stars){
        this.m_stars[difficulty.ordinal()][level - 1] = stars;
    }
}
