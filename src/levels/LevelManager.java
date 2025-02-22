package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelManager {
    private Game game;
    private BufferedImage[] levelSprites;
    private Level levelOne;

    public LevelManager(Game game) {
        this.game = game;
        importOutsideSprites();
        levelOne = new Level(LoadSave.getLevelData());
    }

    private void importOutsideSprites() {
        BufferedImage img = LoadSave.getSpriteAtlas(LoadSave.LEVEL_ATLAS);
        levelSprites = new BufferedImage[48];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 12; j++) {
                int index = i * 12 + j;
                levelSprites[index] = img.getSubimage(j * 32, i * 32, 32, 32);
            }
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < Game.TILES_HEIGHT; i++) {
            for (int j = 0; j < Game.TILES_WIDTH; j++) {
                int index = levelOne.getSpriteIndex(j, i);
                g.drawImage(levelSprites[index], j * Game.TILES_SIZE, i * Game.TILES_SIZE, Game.TILES_SIZE, Game.TILES_SIZE, null);

            }
        }
    }

    public void update() {

    }
}
