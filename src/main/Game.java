package main;

import entities.Player;

import java.awt.*;

public class Game implements Runnable {
    private final int FPS_SET = 120;
    private final int UPS_SET = 200;
    private final GameWindow gameWindow;
    private final GamePanel gamePanel;
    private Thread gameThread;
    private Player player;


    public Game() {
        initClasses();

        gamePanel = new GamePanel(this);
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();

        startGameLoop();

    }

    public Player getPlayer() {
        return player;
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void initClasses() {
        player = new Player(200, 200);
    }

    public void update() {
        player.update();
    }

    public void render(Graphics g) {
        player.render(g);
    }

    @Override
    public void run() {
        double timePerFrameNS = 1000000000.0 / FPS_SET;
        double timePerUpdateNS = 1000000000.0 / UPS_SET;
        long previousTimeNS = System.nanoTime();
        int frames = 0;
        int updates = 0;
        double deltaFrameNS = 0;
        double deltaUpdateNS = 0;

        long lastCheckMS = System.currentTimeMillis();

        while (true) {
            long currentTimeNS = System.nanoTime();

            deltaFrameNS += (currentTimeNS - previousTimeNS) / timePerFrameNS;
            deltaUpdateNS += (currentTimeNS - previousTimeNS) / timePerUpdateNS;
            previousTimeNS = currentTimeNS;

            // Update if required
            if (deltaUpdateNS >= 1) {
                update();
                updates++;
                deltaUpdateNS--;
            }

            // Re-render if required
            if (deltaFrameNS >= 1) {
                gamePanel.repaint();
                frames++;
                deltaFrameNS--;
            }

            if (System.currentTimeMillis() - lastCheckMS >= 1000) {
                lastCheckMS = System.currentTimeMillis();
                System.out.println("FPS: " + frames + " | UPS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    public void windowFocusLost() {
        player.resetDirBooleans();
    }
}
