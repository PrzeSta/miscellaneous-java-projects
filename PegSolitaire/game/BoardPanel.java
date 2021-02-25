package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardPanel extends JPanel implements MouseListener {

    private int size;
    private int cellSize;
    private int borderSize;
    protected Color colorPawn;
    protected Color colorChecked;
    protected Color colorBackground;
    private Board board;
    protected Boolean isRunning;
    private Boolean cellChecked;

    private final JLabel endgameInfo;
    private final JRadioButton french;
    private final JRadioButton english;

    private int startX;
    private int startY;

    private JPopupMenu movePopup;
    private JMenuItem moveLeft;
    private JMenuItem moveRight;
    private JMenuItem moveUp;
    private JMenuItem moveDown;


    public BoardPanel(int size, JLabel endgameInfo, JRadioButton french, JRadioButton english) {
        this.endgameInfo = endgameInfo;
        this.french = french;
        this.english = english;
        this.size = size;
        this.cellSize = size / 7;
        this.borderSize = cellSize / 10;
        this.colorPawn = Color.BLACK;
        this.colorChecked = Color.YELLOW;
        this.colorBackground = Color.WHITE;
        this.setBackground(colorBackground);
        this.board = new Board(false);
        this.isRunning = false;
        this.cellChecked = false;
        this.setPreferredSize(new Dimension(size, size));
        addMouseListener(this);
        initializePopup();
    }


    protected void restart(Boolean isComplicated){
        this.isRunning = true;
        this.cellChecked = false;
        this.board = new Board(isComplicated);
        this.repaint();
    }

    private void initializePopup(){
        movePopup = new JPopupMenu();
        moveLeft = new JMenuItem("left");
        moveLeft.addActionListener(ev -> {
            board.move(startX, startY, Direction.LEFT);
            this.repaintAndCheck();
        });
        moveRight = new JMenuItem("right");
        moveRight.addActionListener(ev -> {
            board.move(startX, startY, Direction.RIGHT);
            this.repaintAndCheck();
        });
        moveUp = new JMenuItem("up");
        moveUp.addActionListener(ev -> {
            board.move(startX, startY, Direction.UP);
            this.repaintAndCheck();
        });
        moveDown = new JMenuItem("down");
        moveDown.addActionListener(ev -> {
            board.move(startX, startY, Direction.DOWN);
            this.repaintAndCheck();
        });
        movePopup.add(moveLeft);
        movePopup.add(moveRight);
        movePopup.add(moveUp);
        movePopup.add(moveDown);
    }

    private void updatePopup(){
        if(board.canJump(startX, startY, Direction.LEFT))
            moveLeft.setEnabled(true);
        else
            moveLeft.setEnabled(false);

        if(board.canJump(startX, startY, Direction.RIGHT))
            moveRight.setEnabled(true);
        else
            moveRight.setEnabled(false);

        if(board.canJump(startX, startY, Direction.UP))
            moveUp.setEnabled(true);
        else
            moveUp.setEnabled(false);

        if(board.canJump(startX, startY, Direction.DOWN))
            moveDown.setEnabled(true);
        else
            moveDown.setEnabled(false);
    }

    protected void checkByMenu(int x, int y){
        if(board.cells[y][x] == Board.Cell.ON_BOARD){
            cellChecked = true;
            startX = x;
            startY = y;
            this.repaintAndCheck();
        }
    }

    protected void moveByMenu(Direction direction){
        if (cellChecked) {
            if(board.canJump(startX, startY, direction))
                cellChecked = false;
            board.move(startX, startY, direction);
            repaintAndCheck();
        }
    }

    private void repaintAndCheck(){
        if(isRunning) {
            this.repaint();
            if(board.hasEnded()){
                isRunning = false;
                if(board.howManyLeft() > 1)
                    endgameInfo.setText("You made it, sadly there are " + board.howManyLeft() + " pawns left!");
                else
                    endgameInfo.setText("You won!");
                french.setEnabled(true);
                english.setEnabled(true);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        size = Math.min(this.getHeight(), this.getWidth());
        cellSize = size / 7;
        borderSize = cellSize / 10;
        g.setColor(colorBackground);
        g.fillRect(0, 0, size, size);
        g.setColor(colorPawn);
        int xOffset = borderSize;
        int yOffset = borderSize;
        for (int y = 0; y < 7; y++) {
            for (int x = 0; x < 7; x++) {
                if (board.cells[y][x] == Board.Cell.ON_BOARD)
                    g.fillOval(xOffset, yOffset, cellSize - 2 * borderSize, cellSize - 2 * borderSize);
                xOffset += cellSize;
            }
            xOffset = borderSize;
            yOffset += cellSize;
        }

        if(cellChecked) {
            g.setColor(colorChecked);
            g.fillOval(startX * cellSize + borderSize, startY * cellSize + borderSize, cellSize - 2 * borderSize, cellSize - 2 * borderSize);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isRunning){
            if(SwingUtilities.isLeftMouseButton(e)) {
                if (!cellChecked) {
                    startX = e.getX() / cellSize;
                    startY = e.getY() / cellSize;
                    if(board.cells[startY][startX] == Board.Cell.ON_BOARD) {
                        cellChecked = true;
                        this.repaint();
                    }
                } else {
                    int endX = e.getX() / cellSize;
                    int endY = e.getY() / cellSize;
                    cellChecked = false;

                    if (startX - 2 == endX && startY == endY)
                        board.move(startX, startY, Direction.LEFT);
                    else if (startX + 2 == endX && startY == endY)
                        board.move(startX, startY, Direction.RIGHT);
                    else if (startX == endX && startY - 2 == endY)
                        board.move(startX, startY, Direction.UP);
                    else if (startX == endX && startY + 2 == endY)
                        board.move(startX, startY, Direction.DOWN);
                    else {
                        if(board.cells[endY][endX] == Board.Cell.ON_BOARD) {
                            cellChecked = true;
                            startX = endX;
                            startY = endY;
                        }
                        else
                            cellChecked = false;
                    }
                    repaintAndCheck();
                }
            }
            else if(SwingUtilities.isRightMouseButton(e)){
                startX = e.getX() / cellSize;
                startY = e.getY() / cellSize;
                if(board.cells[startX][startY] == Board.Cell.ON_BOARD) {
                    updatePopup();
                    movePopup.show(this, startX * cellSize + cellSize / 2, startY * cellSize + cellSize / 2);
                    cellChecked = false;
                }

            }
        }
    }

    public void mousePressed(MouseEvent event){}

    public void mouseReleased(MouseEvent event){}

    public void mouseEntered(MouseEvent event){}

    public void mouseExited(MouseEvent event){}

}
