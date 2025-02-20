package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;

import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

// One way to use JPanel/JFrame, for other way see GameWindow.java
public class GamePanel extends JPanel {
    private MouseInputs mouseInputs;
    private float xDelta = 100, yDelta = 100;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, ticksPerAnimation = 15;
    private int playerAction = IDLE;
    private int playerDirection = -1;
    private boolean playerMoving = false;


    public GamePanel() {
        importImg();
        loadAnimations();

        setPanelSize();
        mouseInputs = new MouseInputs(this);
        addKeyListener(new KeyboardInputs(this));
        addMouseListener(mouseInputs);
        addMouseMotionListener(mouseInputs);
    }

    private void setPanelSize() {
        Dimension size = new Dimension(1280, 800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);
    }

    private void importImg() {

        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            img = ImageIO.read(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void loadAnimations() {
        animations = new BufferedImage[9][6];

        for (int i = 0; i < animations.length; i++) {
            for (int j = 0; j < animations[i].length; j++) {
                animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
            }
        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ticksPerAnimation) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
            }
        }
    }

    private void setAnimation() {
        if (playerMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }
    }

    public void updatePosition() {
        if (playerMoving) {
            switch (playerDirection) {
                case LEFT:
                    xDelta -= 2;
                    break;
                case UP:
                    yDelta -= 2;
                    break;
                case RIGHT:
                    xDelta += 2;
                    break;
                case DOWN:
                    yDelta += 2;
                    break;
            }
        }
    }

    public void setDirection(int direction) {
        this.playerDirection = direction;
        playerMoving = true;
    }

    public void setMoving(boolean moving) {
        playerMoving = moving;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateAnimationTick();
        setAnimation();
        updatePosition();

        g.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 128, 80, null);
    }
}
