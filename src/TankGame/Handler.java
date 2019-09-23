package TankGame;

import TankGame.GameObjects.*;
import TankGame.PowerUps.Shield;
import TankGame.PowerUps.ExtraLife;

import java.awt.*;
import java.util.ArrayList;

public class Handler {
    private ArrayList<Tank> tank;
    private ArrayList<UnbreakableBox> Unbox;
    private ArrayList<Bullet> bullets;
    private ArrayList<BreakableBox> box;
    private ArrayList<Shield> shields;
    private ArrayList<ExtraLife> life;
    private TRE tre;

    public Handler(TRE tre) {
        tank = new ArrayList<>();
        Unbox = new ArrayList<>();
        bullets = new ArrayList<>();
        box = new ArrayList<>();
        shields = new ArrayList<>();
        life = new ArrayList<>();
        this.tre=tre;
    }
    //Graphics
    public void render(Graphics g){
        for(Tank temp : tank){
            temp.drawImage(g);
        }
        for(Bullet temp : bullets){
            temp.drawImage(g);
        }
        for(UnbreakableBox temp : Unbox){
            temp.drawImage(g);
        }
        for(BreakableBox temp: box ){
            temp.drawImage(g);
        }
        for(Shield temp: shields ){
            temp.drawImage(g);
        }
        for(ExtraLife temp: life){
            temp.drawImage(g);
        }


    }
    //Object
    public void update(){
        for(Tank temp : tank){
            temp.update();
        }
        for(Bullet temp : bullets){
            temp.update();
        }
        for(BreakableBox temp : box){
            temp.update();
        }
        for(Shield temp : shields){
            temp.update();
        }
        for(ExtraLife temp : life){
            temp.update();
        }


    }
    public void drawImage(){

    }
    public void addObject(Object temp){
        if(temp instanceof Tank){
            tank.add((Tank)temp);
        }
        if(temp instanceof UnbreakableBox){
            Unbox.add((UnbreakableBox)temp);
        }
        if(temp instanceof Bullet){
            bullets.add((Bullet) temp);
        }
        if(temp instanceof BreakableBox){
            box.add((BreakableBox) temp);
        }
        if(temp instanceof Shield){
            shields.add((Shield) temp);
        }
        if(temp instanceof ExtraLife){
            life.add((ExtraLife) temp);
        }
    }

    public ArrayList<Shield> getShields() {
        return shields;
    }

    public Tank getTanks(ID id){
        for(Tank temp : tank){
            if(temp.getId()==id) return temp;
        }
        return null;
    }

    public ArrayList<Tank> getTank() {
        return tank;
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<ExtraLife> getLife() {
        return life;
    }

    public ArrayList<UnbreakableBox> getBox() {
        return Unbox;
    }

    public ArrayList<BreakableBox> getUnbox() {
        return box;
    }

    public void removeBullet(Bullet i){
        bullets.remove(i);
        System.out.println("Bullet removed");
    }
    public void removeBox(BreakableBox i){
        box.remove(i);
        System.out.println("Wall removed");
    }
    public void removeSheild(Shield i){
        shields.remove(i);
        System.out.println("Shield removed");
    }
    public void removeLife (ExtraLife i){
        life.remove(i);
    }

}


