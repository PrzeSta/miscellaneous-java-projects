package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame {

    protected int size;
    protected BoardPanel boardPanel;
    private final JLabel endgameInfo;

    private JMenuBar menuBar;
    private JMenu menuGame, menuMoves, menuSettings, menuHelp;
    private JMenuItem newGame, exitGame, colorPawn, colorChecked, colorBackground, checkCell, moveLeft, moveRight, moveUp, moveDown, help, about;
    private JRadioButton french, english;
    private JSpinner xSpinner, ySpinner;

    public MainWindow(int size){
        this.size = size;

        this.setTitle("Peg Solitaire");
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeMenu();

        endgameInfo = new JLabel("You haven't won yet");
        boardPanel = new BoardPanel(size, endgameInfo, french, english);

        this.add(boardPanel, BorderLayout.CENTER);
        this.add(endgameInfo, BorderLayout.PAGE_END);
        this.setSize(size, size);
        this.setVisible(true);
        this.pack();

    }

    private void initializeMenu(){
        menuBar = new JMenuBar();

        menuGame = new JMenu("Game");
        menuGame.setMnemonic(KeyEvent.VK_G);
        newGame = new JMenuItem("Start new game", KeyEvent.VK_N);
        newGame.addActionListener(ev -> restart());
        exitGame = new JMenuItem("Exit game", KeyEvent.VK_X);
        exitGame.addActionListener(ev -> System.exit(NORMAL));
        menuGame.add(newGame);
        menuGame.add(exitGame);

        menuMoves = new JMenu("Move");
        menuMoves.setMnemonic(KeyEvent.VK_M);
        menuGame.setMnemonic(KeyEvent.VK_G);
        xSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 7, 1));
        ySpinner = new JSpinner(new SpinnerNumberModel(4, 1, 7, 1));
        checkCell = new JMenuItem("Check pawn (P)");
        checkCell.addActionListener(ev -> boardPanel.checkByMenu((int) xSpinner.getValue() - 1, (int) ySpinner.getValue() - 1));
        checkCell.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, ActionEvent.CTRL_MASK));
        moveLeft = new JMenuItem("Left (A)");
        moveLeft.addActionListener(ev -> boardPanel.moveByMenu(Direction.LEFT));
        moveLeft.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
        moveRight = new JMenuItem("Right (D)");
        moveRight.addActionListener(ev -> boardPanel.moveByMenu(Direction.RIGHT));
        moveRight.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_D, ActionEvent.CTRL_MASK));
        moveUp = new JMenuItem("Up (W)");
        moveUp.addActionListener(ev -> boardPanel.moveByMenu(Direction.UP));
        moveUp.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
        moveDown = new JMenuItem("Down (S)");
        moveDown.addActionListener(ev -> boardPanel.moveByMenu(Direction.DOWN));
        moveDown.setAccelerator(
                KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
        menuMoves.add(xSpinner);
        menuMoves.add(ySpinner);
        menuMoves.add(checkCell);
        menuMoves.add(moveLeft);
        menuMoves.add(moveRight);
        menuMoves.add(moveUp);
        menuMoves.add(moveDown);

        menuSettings = new JMenu("Settings");
        menuSettings.setMnemonic(KeyEvent.VK_S);
        colorPawn = new JMenuItem("Pawn color");
        colorPawn.addActionListener(ev -> { boardPanel.colorPawn =
                JColorChooser.showDialog(this, "Choose pawn color", Color.BLACK);
                boardPanel.repaint();});
        colorChecked = new JMenuItem("Checked pawn color");
        colorChecked.addActionListener(ev -> { boardPanel.colorChecked =
                JColorChooser.showDialog(this, "Choose checked pawn  color", Color.YELLOW);
                boardPanel.repaint();});
        colorBackground = new JMenuItem("Background color");
        colorBackground.addActionListener(ev -> {
            boardPanel.colorBackground =
                JColorChooser.showDialog(this, "Choose background color", Color.WHITE);
                boardPanel.repaint();});
        menuSettings.add(colorPawn);
        menuSettings.add(colorChecked);
        menuSettings.add(colorBackground);
        french = new JRadioButton("French version");
        french.addActionListener(ev -> {
            if (english.isSelected()){
                english.setSelected(false);
            }
        });
        english = new JRadioButton("English version");
        english.addActionListener(ev -> {
            if (french.isSelected()){
                french.setSelected(false);
            }
        });
        french.setSelected(true);
        menuSettings.add(english);
        menuSettings.add(french);

        menuHelp = new JMenu("Help&About");
        help = new JMenuItem("Game rules");
        help.addActionListener(ev -> JOptionPane.showMessageDialog(this,
                "Come on, check it on Internet.\n Yes, I am too lazy to write some meaningful instructions.",
                "How to play",
                JOptionPane.PLAIN_MESSAGE));
        about = new JMenuItem("About");
        menuHelp.add(help);
        about.addActionListener(ev -> {JOptionPane.showMessageDialog(this,
                "Title: Peg Solitaire \nAuthor: Przemyslaw Stasiuk \nVersion: 1.2",
                        "About",
                        JOptionPane.PLAIN_MESSAGE);});
        menuHelp.add(about);

        menuBar.add(menuGame);
        menuBar.add(menuMoves);
        menuBar.add(menuSettings);
        menuBar.add(Box.createHorizontalGlue());
        menuBar.add(menuHelp);
        this.setJMenuBar(menuBar);

    }

    private void restart(){
        endgameInfo.setText("You haven't won yet");
        if(french.isSelected())
            boardPanel.restart(false);
        else if(english.isSelected())
            boardPanel.restart(true);
        french.setEnabled(false);
        english.setEnabled(false);
    }
}
