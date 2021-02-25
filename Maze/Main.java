import game.*;
import javax.swing.JFrame;

public class Main {

    /*
    * proposed sizes:
    * nice medium maze -> 1200, 800, 30, 20
    * nice big maze -> 1800, 1000, 45, 25
    * square small maze -> 560, 560, 20, 20
    * biggest square maze -> 980, 980, 35, 35
    */

    public static void main(String []args) {
        UserInterface mazeCanvas = new UserInterface(1200, 800, 30, 20);
        JFrame frame = new JFrame("Maze");
        frame.add(mazeCanvas);
        frame.pack();
        frame.setVisible(true);
    }
}