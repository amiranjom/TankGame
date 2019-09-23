package TankGame.GameObjects;

import TankGame.ID;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UnbreakableBox extends GameObject {

    public UnbreakableBox(int x, int y, ID id, BufferedImage img) {
        super(x, y, 0, 0, id, img);
    }
    public void drawImage(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img,x,y,32,32,null);
    }

    @Override
    public void update() {
    }

    public Rectangle getBounds(){
        return new Rectangle(x,y,32,32);
    }


}
