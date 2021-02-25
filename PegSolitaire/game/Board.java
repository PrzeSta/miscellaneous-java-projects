package game;

public class Board {

    protected enum Cell {
        NONEXISTENT, NOT_ON_BOARD, ON_BOARD
    }

    protected boolean isComplicated;
    protected Cell[][] cells;

    public Board(boolean input){
        this.isComplicated = input;
        this.cells = new Cell[7][7];
        initiateEnglishBoard();
    }

    private void initiateEnglishBoard(){

        boolean[][] grid = {
                {false, false, true, true, true, false, false},
                {false, false, true, true, true, false, false},
                {true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true},
                {true, true, true, true, true, true, true},
                {false, false, true, true, true, false, false},
                {false, false, true, true, true, false, false}
        };

        if(isComplicated){
            grid[1][1] = true;
            grid[1][5] = true;
            grid[5][1] = true;
            grid[5][5] = true;
        }

        for(int x = 0; x < 7; x++){
            for(int y = 0; y < 7; y++){
                if(grid[x][y]){
                    cells[x][y] = Cell.ON_BOARD;
                }
                else
                    cells[x][y] = Cell.NONEXISTENT;
            }
        }
        cells[3][3] = Cell.NOT_ON_BOARD;
    }

    private boolean hasNeighbour(int x, int y, Direction dir){
        switch(dir) {
            case LEFT:
                if(x - 1 >= 0)
                    return cells[y][x - 1] == Cell.ON_BOARD;
            break;
            case RIGHT:
                if(x + 1 < 7)
                    return cells[y][x + 1] == Cell.ON_BOARD;
            break;
            case UP:
                if(y - 1 >= 0)
                    return cells[y - 1][x] == Cell.ON_BOARD;
            break;
            case DOWN:
                if(y + 1 < 7)
                    return cells[y + 1][x] == Cell.ON_BOARD;
            break;
        }
        return false;
    }

    protected boolean canJump(int x, int y, Direction dir) {
        if (hasNeighbour(x, y, dir)) {
            switch(dir) {
                case LEFT:
                    if(x - 2 >= 0)
                        return cells[y][x - 2] == Cell.NOT_ON_BOARD;
                break;
                case RIGHT:
                    if(x + 2 < 7)
                        return cells[y][x + 2] == Cell.NOT_ON_BOARD;
                break;
                case UP:
                    if(y - 2 >= 0)
                        return cells[y - 2][x] == Cell.NOT_ON_BOARD;
                break;
                case DOWN:
                    if(y + 2 < 7)
                        return cells[y + 2][x] == Cell.NOT_ON_BOARD;
                    break;
            }
            return false;
        }
        return false;
    }

    public void move(int startX, int startY, Direction dir){
        if(canJump(startX, startY, dir)){
            cells[startY][startX] = Cell.NOT_ON_BOARD;
            switch(dir) {
                case LEFT:
                    cells[startY][startX - 1] = Cell.NOT_ON_BOARD;
                    cells[startY][startX - 2] = Cell.ON_BOARD;
                break;
                case RIGHT:
                    cells[startY][startX + 1] = Cell.NOT_ON_BOARD;
                    cells[startY][startX + 2] = Cell.ON_BOARD;
                break;
                case UP:
                    cells[startY - 1][startX] = Cell.NOT_ON_BOARD;
                    cells[startY - 2][startX] = Cell.ON_BOARD;
                break;
                case DOWN:
                    cells[startY + 1][startX] = Cell.NOT_ON_BOARD;
                    cells[startY + 2][startX] = Cell.ON_BOARD;
                break;
            }
        }
    }

    public boolean hasEnded(){
        for(int y = 0; y < 7; y++)
            for (int x = 0; x < 7; x++) {
                if (cells[y][x] == Cell.ON_BOARD && (canJump(x, y, Direction.LEFT) ||
                        canJump(x, y, Direction.RIGHT) ||
                        canJump(x, y, Direction.UP) ||
                        canJump(x, y, Direction.DOWN)))
                    return false;
            }
        return true;
    }

    public int howManyLeft(){
        int acc = 0;
        for(int y = 0; y < 7; y++) 
            for (int x = 0; x < 7; x++)
                if (cells[y][x] == Cell.ON_BOARD)
                    acc += 1;
        return acc;
    }


    public String toString(){
        String acc = "";
        for(int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (cells[y][x] == Cell.ON_BOARD)
                    acc += "*" + " ";
                else
                    acc += "  ";
            }
            acc += "\n";
        }

        return acc;
    }
}
