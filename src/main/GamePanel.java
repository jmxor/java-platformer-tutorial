package main;

import javax.swing.*;
import java.awt.*;

// One way to use JPanel/JFrame, for other way see GameWindow.java
public class GamePanel extends JPanel {
    public GamePanel() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(100, 100, 200, 50);
    }
}
