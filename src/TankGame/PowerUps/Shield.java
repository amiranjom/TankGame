package TankGame.PowerUps;

import TankGame.GameObjects.Tank;
import TankGame.Handler;
import TankGame.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Shield extends PowerUp {

    private Handler handler;
    private boolean hit = false;

    public Shield(int x, int y, ID id, BufferedImage img, Handler handler) {
        super(x, y,img,id);
        this.handler = handler;
    }
    public void update(){
        for(Tank temp: handler.getTank()){
            if(temp.getId() == ID.Tank1){
                if(getBounds().intersects(temp.getBounds())) {
                    temp.setHealth(100);
                    hit = true;
                }
            }
            if(temp.getId() == ID.Tank2){
                if(getBounds().intersects(temp.getBounds())) {
                    temp.setHealth(100);
                    hit = true;
                }
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
