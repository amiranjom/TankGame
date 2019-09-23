package TankGame.GameObjects;


import TankGame.Handler;
import TankGame.ID;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author anthony-pc
 */
public class Tank extends GameObject {


    //    public ArrayList<Bullet> bullet = new ArrayList<>();
    private int angle;

    private Handler handler;
    private BufferedImage bullet;

    private boolean hit;
    private boolean isWallhit = false;
    private int health = 100;
    private boolean gameOver = false;
    private int shootCount = 0;


    private final int R = 2;
    private final int ROTATIONSPEED = 4;
    private int life = 3;
    private float hits = 0;


    private boolean UpPressed;
    private boolean DownPressed;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;


    public Tank(int x, int y, int angle, BufferedImage img, ID id, Handler handler, BufferedImage bullet) {
        super(x, y, 0, 0, id, img);
        this.angle = angle;
        this.handler = handler;
        this.bullet = bullet;
        this.hit = false;

    }

    void toggleUpPressed() {
        this.UpPressed = true;
    }

    void toggleDownPressed() {
        this.DownPressed = true;
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPresses() {
        this.ShootPressed = true;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    void unToggleUpPressed() {
        this.UpPressed = false;
    }

    void unToggleDownPressed() {
        this.DownPressed = false;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    public void update() {
        if (this.UpPressed) {
            this.moveForwards();
        }
        if (this.DownPressed) {
            this.moveBackwards();
        }

        collision();

        if (this.LeftPressed) {
            this.rotateLeft();
        }
        if (this.RightPressed) {
            this.rotateRight();
        }
        if (this.ShootPressed) {
            shootCount++;
            if (shootCount == 13 ) {
                shoot();
            }


        }
        if (health <= 0) {
            if (life != 0) {
                life--;
                health = 100;

            }
            if (life == 0) {
                gameOver = true;
            }
        }


    }


    private void rotateLeft() {
        this.angle -= this.ROTATIONSPEED;
    }

    private void rotateRight() {
        this.angle += this.ROTATIONSPEED;
    }

    private void moveBackwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x -= vx;
        y -= vy;
    }



    private void moveForwards() {
        vx = (int) Math.round(R * Math.cos(Math.toRadians(angle)));
        vy = (int) Math.round(R * Math.sin(Math.toRadians(angle)));
        x += vx;
        y += vy;
    }

    public void collision() {
        for (UnbreakableBox temp : handler.getBox()) {
            if (getBounds2().intersects(temp.getBounds())) {
                if (x < temp.getX()) {
                    x -= vx;
                    y -= vy;
                }
                if (x > temp.getX()) {
                    x -= vx;
                    y -= vy;
                }
                if (y > temp.getY()) {
                    x -= vx;
                    y -= vy;
                }
                if (y < temp.getY()) {
                    x -= vx;
                    y -= vy;
                }

            }
        }
        for (Tank temp : handler.getTank()) {
            if (temp.getId() == this.id) {

            } else if (temp.getId() != this.id) {
                if (getBounds2().intersects(temp.getBounds2())) {
                    x += vx * -1;
                    y += vy * -1;
                }
            }
        }


    }

    public void bulletHit() {
        hits += 0.1;
        health -= hits;

    }
    public void addLife(){
        life+=1;
    }

    public int getAngle() {
        return angle;
    }
    public void setWallhit(boolean wallhit) {
        isWallhit = wallhit;
    }

    public void setHealth(int health) {
        this.health += health;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    private void shoot() {
        handler.addObject(new Bullet(this.x, this.y, ID.Bullet, this.bullet, this, handler));
        shootCount = 0;
    }

    @Override
    public String toString() {
        return "x=" + x + ", y=" + y + ", angle=" + angle;
    }


    public void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(this.img, rotation, null);
        g.setColor(Color.red);
        g.fillRect(x, y + 60,  (int) ((health / 2) - hits), 10);
        g.setColor(new Color(75, 255, 0));
        g.fillRect(x, y + 60, (int) ((health / 2) - hits), 10);
        g.setColor(Color.blue);
        g.drawRect(x, y + 60, (int) ((health / 2) - hits), 10);
        g.setColor(Color.black);
        g.setFont(new Font("arial", 1, 15));
        g.drawString("Lives : " + life, x, y + 90);


    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, 50, 50);
    }

    public Rectangle getBounds2() {
        return new Rectangle(x - 5, y - 5, 60, 60);
    }

}
