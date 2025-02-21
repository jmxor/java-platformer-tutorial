package main;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS_SET = 120;
    private final int UPS_SET = 200;


    public Game(){
        gamePanel = new GamePanel();
        gameWindow = new GameWindow(gamePanel);
        gamePanel.requestFocus();
        startGameLoop();
    }

    private void startGameLoop() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void update(){
        gamePanel.updateGame();
    }

    @Override
    public void run() {
        double timePerFrameNS = 1000000000.0 / FPS_SET;
        double timePerUpdateNS = 1000000000.0 / UPS_SET;
        long previousTimeNS = System.nanoTime();;
        int frames = 0;
        int updates = 0;
        double deltaFrameNS = 0;
        double deltaUpdateNS = 0;

        long lastCheckMS = System.currentTimeMillis();

        while(true){
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
}
