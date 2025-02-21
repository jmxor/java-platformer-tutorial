package entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {
    private final int ticksPerAnimation = 15;
    private final float speed = 2.0f;
    private boolean isAttacking = false;
    private boolean isMoving = false;
    private BufferedImage[][] animations;
    private int animationTick;
    private int animationIndex;
    private int playerAction = IDLE;
    private boolean up, left, right, down;

    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    private void loadAnimations() {
        InputStream is = getClass().getResourceAsStream("/player_sprites.png");

        try {
            BufferedImage img = ImageIO.read(is);
            animations = new BufferedImage[9][6];

            for (int i = 0; i < animations.length; i++) {
                for (int j = 0; j < animations[i].length; j++) {
                    animations[i][j] = img.getSubimage(j * 64, i * 40, 64, 40);
                }
            }
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

    public void updatePosition() {
        isMoving = false;
        if (left && !right) {
            x -= speed;
            isMoving = true;
        } else if (right && !left) {
            x += speed;
            isMoving = true;

        }

        if (up && !down) {
            y -= speed;
            isMoving = true;

        } else if (down && !up) {
            y += speed;
            isMoving = true;

        }
    }

    private void updateAnimationTick() {
        animationTick++;
        if (animationTick >= ticksPerAnimation) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= getSpriteAmount(playerAction)) {
                animationIndex = 0;
                isAttacking = false;
            }
        }
    }

    private void setAnimation() {
        int startAnimation = playerAction;

        if (isMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (isAttacking) {
            playerAction = ATTACK_1;
        }

        if (startAnimation != playerAction) {
            animationIndex = animationTick = 0;
        }
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void render(Graphics g) {
        g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 128, 80, null);

    }

    public void resetDirBooleans() {
        left = right = down = up = false;
    }

    public void setAttacking(boolean attacking) {
        isAttacking = attacking;
    }
}
