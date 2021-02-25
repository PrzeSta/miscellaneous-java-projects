package game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Maze {

    private final int height;
    private final int width;
    private final int size;
    private final CellLocation[] grid;

    protected final ArrayList<Cell> cells = new ArrayList<>();
    protected final ArrayList<Integer> frontier = new ArrayList<>();
    protected final ArrayList<Passage> passages = new ArrayList<>();
    final Random rand = new Random();

    private enum CellLocation {
        INSIDE, OUTSIDE, FRONTIER
    }

    public Maze(int height, int width){
        this.height = height;
        this.width = width;
        size = width * height;
        grid = new CellLocation[size];
        Arrays.fill(grid, CellLocation.OUTSIDE);
        generatePassages();
        generateCells();
    }

    private Passage createPassage(int cell){
        ArrayList<Integer> neighbours = new ArrayList<>();

        if(cell - width >= 0)
            if (grid[cell - width] == CellLocation.INSIDE)
                neighbours.add(cell - width);
        if(cell + width < size)
            if(grid[cell + width] == CellLocation.INSIDE)
                neighbours.add(cell + width);
        if(cell - 1 >= 0 && cell % width != 0)
            if (grid[cell - 1] == CellLocation.INSIDE)
                neighbours.add(cell - 1);
        if(cell + 1 < size && (cell + 1) % width != 0)
            if (grid[cell + 1] == CellLocation.INSIDE)
                neighbours.add(cell + 1);

        int otherEnd = neighbours.get(rand.nextInt(neighbours.size()));

        if(Math.abs(cell - otherEnd) == 1)
            return new Passage(cell, otherEnd, Passage.Direction.HORIZONTAL);
        else
            return new Passage(cell, otherEnd, Passage.Direction.VERTICAL);

    }

    private void setNeighboursAsFrontier(int cell){
        if(cell - width >= 0)
            if (grid[cell - width] == CellLocation.OUTSIDE){
                grid[cell - width] = CellLocation.FRONTIER;
                frontier.add(cell - width);
            }
        if(cell + width < size)
            if(grid[cell + width] == CellLocation.OUTSIDE){
                grid[cell + width] = CellLocation.FRONTIER;
                frontier.add(cell + width);
            }
        if(cell - 1 >= 0 && cell % width != 0)
            if (grid[cell - 1] == CellLocation.OUTSIDE){
                grid[cell - 1] = CellLocation.FRONTIER;
                frontier.add(cell - 1);
            }
        if(cell + 1 < size && (cell + 1) % width != 0)
            if (grid[cell + 1] == CellLocation.OUTSIDE){
                grid[cell + 1] = CellLocation.FRONTIER;
                frontier.add(cell + 1);
            }
    }

    private void removeFromFrontier(int cell){
        grid[cell] = CellLocation.INSIDE;
        frontier.remove(Integer.valueOf(cell));
    }

    private void generatePassages(){
        int currentCell = rand.nextInt(height * width);
        grid[currentCell] = CellLocation.INSIDE;
        setNeighboursAsFrontier(currentCell);

        while(!frontier.isEmpty()){
            currentCell = frontier.get(rand.nextInt(frontier.size()));
            passages.add(createPassage(currentCell));
            removeFromFrontier(currentCell);
            setNeighboursAsFrontier(currentCell);
        }
    }

    private void generateCells(){
        for(int i = 0; i < size; i++)
            cells.add(new Cell(i));

        for(Passage p : passages){
            if(p.direction == Passage.Direction.HORIZONTAL){
                cells.get(p.cell1).right = true;
                cells.get(p.cell2).left = true;
            }
            else{
                cells.get(p.cell1).down = true;
                cells.get(p.cell2).up = true;
            }
        }
    }
}
