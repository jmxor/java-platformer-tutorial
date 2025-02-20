package main;

public class Game implements Runnable {
    private GameWindow gameWindow;
    private GamePanel gamePanel;
    private Thread gameThread;

    private final int FPS_SET = 120;


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

    @Override
    public void run() {
        double timePerFrameNS = 1000000000.0 / FPS_SET;
        long lastFrameTimeNS = System.nanoTime();
        long nowNS;
        int frames = 0;
        long lastCheckMS = System.currentTimeMillis();

        while(true){
            nowNS = System.nanoTime();
            if (nowNS - lastFrameTimeNS >= timePerFrameNS){
                gamePanel.repaint();
                lastFrameTimeNS = nowNS;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheckMS >= 1000) {
                lastCheckMS = System.currentTimeMillis();
                System.out.println("fps: " + frames);
                frames = 0;
            }
        }
    }
}
