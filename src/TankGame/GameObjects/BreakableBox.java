package TankGame.GameObjects;

import TankGame.Handler;
import TankGame.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class BreakableBox extends GameObject {
    private Handler handler;
    private boolean hit = false;

    public BreakableBox(int x, int y, ID id, BufferedImage img, Handler handler) {
        super(x, y, 0, 0, id, img);
        this.handler = handler;
    }

    @Override
    public void update() {
        for (Bullet temp : handler.getBullets()) {
            if (getBounds().intersects(temp.getBounds())) {
                this.hit = true;
                temp.setWallHit(true);
            }
        }
        for (Tank temp : handler.getTank()) {
            if (getBounds().intersects(temp.getBounds())) {
                this.hit = true;
                temp.setWallhit(true);
            }
        }
    }

    public boolean isHit() {
        return hit;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, x, y, 32, 32, null);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 32, 32);
    }


}
