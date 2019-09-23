package TankGame.GameObjects;

import TankGame.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected int x, y, vx, vy;
    BufferedImage img;
    protected ID id;

    public GameObject(int x, int y, int vx, int vy, ID id,BufferedImage img) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.img = img;
        this.id = id;
    }
    public  abstract void update();
    public abstract void drawImage(Graphics g);
    public ID getId() {
        return id;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public BufferedImage getImg() {
        return img;
    }
}
