package game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;

public class UserInterface extends Canvas implements KeyListener {

    private Maze maze;
    private final int widthWindow;
    private final int heightWindow;
    private final int widthMaze;
    private final int heightMaze;
    private int lineSize;
    private final int bigXOffset;
    private final int bigYOffset;
    private final int smallXOffset;
    private final int smallYOffset;
    private Agent agent;
    final Random rand = new Random();
    private boolean isRunning;
    private boolean isEnded;


    private enum Direction{
        LEFT, RIGHT, UP, DOWN
    }

    public UserInterface(int widthWindow, int heightWindow, int widthMaze, int heightMaze){
        this.widthWindow = widthWindow;
        this.heightWindow = heightWindow;
        this.widthMaze = widthMaze;
        this.heightMaze = heightMaze;
        bigXOffset = widthWindow / widthMaze;
        bigYOffset = heightWindow / heightMaze;
        isRunning = true;
        isEnded = false;

        try {
	    	String basePath = new File("").getAbsolutePath();
            if(widthWindow / widthMaze >= 38) {
                agent = new Agent(rand.nextInt(widthMaze * heightMaze), ImageIO.read(new File(basePath + "/spiderBig.jpg")));
                lineSize = 4;
            }
            else {
                agent = new Agent(rand.nextInt(widthMaze * heightMaze), ImageIO.read(new File(basePath + "/spiderSmall.jpg")));
                lineSize = 2;
            }
        }
        catch(Exception ex){
            System.out.println("Exception found: " + ex.getMessage());
        }

        smallXOffset = bigXOffset - lineSize;
        smallYOffset = bigYOffset - lineSize;

        maze = new Maze(heightMaze, widthMaze);

        setBackground (Color.WHITE);
        addKeyListener(this);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(widthWindow, heightWindow);
    }

    @Override
    public void paint(Graphics g) {
        int currentX = 0;
        int currentY = 0;
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, bigXOffset, bigYOffset);
        g.setColor(Color.BLACK);

        for(Cell c : maze.cells){
            if(!c.up)
               g.fillRect(currentX, currentY, bigXOffset,lineSize);
            if(!c.down)
                g.fillRect(currentX, currentY + smallYOffset, bigXOffset, lineSize);
            if(!c.left)
                g.fillRect(currentX, currentY, lineSize, bigYOffset);
            if(!c.right)
                g.fillRect(currentX + smallXOffset, currentY, lineSize, bigYOffset);

            if(c.id % widthMaze == widthMaze - 1){
                currentX = 0;
                currentY += bigYOffset;
            }
            else
                currentX += bigXOffset;
        }

        g.drawImage(agent.image, (agent.currentLocation % widthMaze) * bigXOffset + 6, (agent.currentLocation / widthMaze) * bigYOffset + 6, null);
    }

    @Override
    public void update(Graphics g) {
        if(isRunning) {
            g.setColor(Color.WHITE);
            if (lineSize == 4)
                g.fillRect((agent.previousLocation % widthMaze) * bigXOffset + 6, (agent.previousLocation / widthMaze) * bigYOffset + 6, 26, 26);
            else
                g.fillRect((agent.previousLocation % widthMaze) * bigXOffset + 6, (agent.previousLocation / widthMaze) * bigYOffset + 6, 16, 16);

            g.drawImage(agent.image, (agent.currentLocation % widthMaze) * bigXOffset + 6, (agent.currentLocation / widthMaze) * bigYOffset + 6, null);
        }
        else {
            if(!isEnded)
                isRunning = true;
            g.setColor(Color.WHITE);
            g.fillRect(0, 0, widthWindow, heightWindow);
            this.paint(g);
        }
    }

    private void moveAgent(Direction direction){
        switch (direction){
            case LEFT:
                if(maze.cells.get(agent.currentLocation).left)
                    agent.move(agent.currentLocation - 1);
            break;
            case RIGHT:
                if(maze.cells.get(agent.currentLocation).right)
                    agent.move(agent.currentLocation + 1);
            break;
            case UP:
                if(maze.cells.get(agent.currentLocation).up)
                    agent.move(agent.currentLocation - widthMaze);
            break;
            case DOWN:
                if(maze.cells.get(agent.currentLocation).down)
                    agent.move(agent.currentLocation + widthMaze);
            break;
        }
        this.repaint();
        if(agent.currentLocation == 0)
            isEnded = true;
    }

    public void keyPressed(KeyEvent evt){
        int key = evt.getKeyCode();

        if(key == KeyEvent.VK_R){
            isRunning = false;
            isEnded = false;
            maze = null;
            maze = new Maze(heightMaze, widthMaze);
            agent.currentLocation = rand.nextInt(widthMaze * heightMaze - 1) + 1;
            this.repaint();
        }
        else if (isRunning && !isEnded)
            switch (key) {
                case KeyEvent.VK_LEFT -> moveAgent(Direction.LEFT);
                case KeyEvent.VK_RIGHT -> moveAgent(Direction.RIGHT);
                case KeyEvent.VK_UP -> moveAgent(Direction.UP);
                case KeyEvent.VK_DOWN -> moveAgent(Direction.DOWN);
            }
    }

    public void keyReleased(KeyEvent evt) {}
    public void keyTyped(KeyEvent evt) {}
}
