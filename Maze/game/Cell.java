package game;

public class Cell {
    protected final int id;
    protected boolean up;
    protected boolean down;
    protected boolean left;
    protected boolean right;

    protected Cell(int id){
        this.id = id;
        up = false;
        down = false;
        left = false;
        right = false;
    }

    public String toString(){
        return id + " " + left + " " + right + " " + up + " " + down;
    }
}
