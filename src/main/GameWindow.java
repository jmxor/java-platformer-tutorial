package main;

import javax.swing.*;

// One way to use JPanel/JFrame, for other way see GamePanel.java
public class GameWindow {
    private JFrame jframe;

    public GameWindow(GamePanel gamePanel) {
        jframe = new JFrame();

        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(gamePanel);
        jframe.setLocationRelativeTo(null);
        jframe.pack(); // set window size to preferred size of internal components
        jframe.setVisible(true);
    }
}
