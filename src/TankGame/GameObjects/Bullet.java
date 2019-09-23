package TankGame.GameObjects;

import TankGame.Handler;
import TankGame.ID;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Bullet extends GameObject {

    private Tank tank;
    private int angle;
    private Handler handler;
    private boolean hit = false;
    private boolean WallHit = false;

    public Bullet(int x, int y, ID id, BufferedImage img, Tank tank, Handler handler) {
        super(x + 16, y + 24, 0, 0, id, img);
        this.tank = tank;
        this.angle = tank.getAngle();
        this.handler = handler;

    }

    @Override
    public void update() {
        y += Math.round(2 * Math.sin(Math.toRadians(angle)));
        x += Math.round(2 * Math.cos(Math.toRadians(angle)));
        collision();
    }

    public void collision() {
        if (tank.id == ID.Tank1) {
            if (getBounds().intersects(handler.getTanks(ID.Tank2).getBounds())) {
                handler.getTanks(ID.Tank2).bulletHit();
                System.out.println("Tank 2 Hit");
                hit = true;
            }
        } else if (tank.id == ID.Tank2) {
            if (getBounds().intersects(handler.getTanks(ID.Tank1).getBounds())) {
                handler.getTanks(ID.Tank1).bulletHit();
                System.out.println("Tank 1 hit");
                hit = true;
            }
        }

    }

    public boolean isHit() {
        return hit;
    }


    public void setWallHit(boolean wallHit) {
        WallHit = wallHit;
    }

    public void drawImage(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), 0, 0);
        g2.drawImage(this.img, rotation, null);

    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 16, 16);
    }
}
