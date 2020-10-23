package djs.game.slidepuzzle;

public class TilePosition {
    private int m_x;
    private int m_y;

    public TilePosition(int x, int y){
        this.m_x = x;
        this.m_y = y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        TilePosition other = (TilePosition) o;
        if (this.m_x == other.m_x && this.m_y == other.m_y){
            return true;
        }
        return false;
    }

    public int get_x(){
        return this.m_x;
    }

    public int get_y(){
        return this.m_y;
    }
}
