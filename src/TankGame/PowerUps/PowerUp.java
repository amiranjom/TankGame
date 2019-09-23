package TankGame.PowerUps;

import TankGame.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class PowerUp {
    protected int x, y;
    BufferedImage img;
    protected ID id;

    public PowerUp(int x, int y, BufferedImage img, ID id) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.id = id;
    }
    public abstract void update();
    public abstract void drawImage(Graphics g);

}
