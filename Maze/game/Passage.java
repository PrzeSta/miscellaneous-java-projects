package game;

public class Passage {

    protected final int cell1;
    protected final int cell2;
    protected final Direction direction;
    protected enum Direction{
        HORIZONTAL, VERTICAL
    }

    protected Passage(int cell1, int cell2, Direction direction){
        this.cell1 = Math.min(cell1, cell2);
        this.cell2 = Math.max(cell1, cell2);
        this.direction = direction;
    }

    public String toString(){
        return cell1 + " <-> " + cell2;
    }

}
