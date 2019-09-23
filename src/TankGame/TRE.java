/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TankGame;


import TankGame.DisplayUi.Camera;
import TankGame.GameObjects.*;
import TankGame.PowerUps.Shield;
import TankGame.PowerUps.ExtraLife;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static javax.imageio.ImageIO.read;

/**
 * @author anthony-pc
 */
public class TRE extends JPanel {


    public static final int SCREEN_WIDTH = 1280;
    public static final int SCREEN_HEIGHT = 960;

    private BufferedImage world;
    private BufferedImage menuBack;
    private BufferedImage gameOver;
    private BufferedImage floor;
    private BufferedImage level;
    private BufferedImage wall;
    private BufferedImage breakable;
    private BufferedImage helpMenu;
    private BufferedImage shield;
    private BufferedImage life;

    private static Camera c1, c2;
    private ID id = ID.Menu;
    private Menu menu;
    private Help help;



    private Handler handler;


    private static Graphics2D buffer;
    private JFrame jf;



    public static void main(String[] args) {

        Thread x;
        TRE trex = new TRE();

        trex.init();
        trex.run();


    }


    private void init() {
        this.jf = new JFrame("Tank Rotation");
        this.world = new BufferedImage((TRE.SCREEN_WIDTH * 2)+300, (TRE.SCREEN_HEIGHT * 2) + 100, BufferedImage.TYPE_INT_RGB);
        BufferedImage t1img = null, t2img = null, bullet = null;
        try {
            BufferedImage tmp;
            System.out.println(System.getProperty("user.dir"));
            /*
             * note class loaders read files from the out folder (build folder in netbeans) and not the
             * current working directory.
             */
            t1img = read(new File("resources/tank1.png"));
            t2img = read(new File("resources/tank2.png"));
            floor = read(new File("resources/ice.png"));
            wall = read(new File("resources/Wall2.png"));
            breakable = read(new File("resources/Wall.png"));
            level = read(new File("resources/map.png"));
            bullet = read(new File("resources/Rocket.png"));
            menuBack = read(new File("resources/Tankk1.png"));
            gameOver = read(new File("resources/Tankk.png"));
            helpMenu = read(new File("resources/Help.png"));
            shield = read(new File("resources/Shield.png"));
            life = read(new File("resources/life.png"));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        handler = new Handler(this);

        handler.addObject(new Tank(1500, 150, 0, t1img, ID.Tank1, handler, bullet));

        handler.addObject(new Tank(700, 400, 0, t2img, ID.Tank2, handler, bullet));

        TankControl tc1 = new TankControl(handler.getTanks(ID.Tank1), KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_K);

        TankControl tc2 = new TankControl(handler.getTanks(ID.Tank2), KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_F);

        c1 = new Camera(0, 0);

        c2 = new Camera(0, 0);

        loadMap();


        this.jf.setLayout(new BorderLayout());
        this.jf.add(this);

        menu = new Menu(this, menuBack);

        help = new Help(helpMenu, this);

        this.jf.addKeyListener(tc1);
        this.jf.addKeyListener(tc2);
        this.addMouseListener(menu);
        this.addMouseListener(help);
        this.jf.setSize(TRE.SCREEN_WIDTH, TRE.SCREEN_HEIGHT + 30);
        this.jf.setResizable(false);
        jf.setLocationRelativeTo(null);

        this.jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.jf.setVisible(true);


    }

    public void run() {
        try {

            while (true) {
                tick();
                repaint(new Rectangle(SCREEN_WIDTH, SCREEN_HEIGHT));    //Repaints the whole screen every time


                /* Anything above is for cleaning and removing unsuable object at the right time.

                * */

                for (Bullet temp : handler.getBullets()) {
                    if (temp.getX() > SCREEN_WIDTH * 2 + 50) {
                        handler.removeBullet(temp);
                        break;
                    }
                    if (temp.getX() < 0) {
                        handler.removeBullet(temp);
                        break;
                    }
                    if (temp.getY() < 0) {
                        handler.removeBullet(temp);
                        break;
                    }
                    if (temp.getY() > SCREEN_HEIGHT * 2 + 50) {
                        handler.removeBullet(temp);
                        break;
                    }
                    if (temp.isHit()) {
                        handler.removeBullet(temp);
                        break;
                    }
                }
                for (BreakableBox temp : handler.getUnbox()) {
                    if (temp.isHit()) {
                        handler.removeBox(temp);
                        break;
                    }
                }
                for(Shield temp: handler.getShields()){
                    if(temp.isHit()){
                        handler.removeSheild(temp);
                        break;
                    }
                }
                for(ExtraLife temp: handler.getLife()){
                    if(temp.isHit()){
                        handler.removeLife(temp);
                        break;
                    }
                }
                for (Tank temp : handler.getTank()) {
                    if (temp.isGameOver()) id = ID.GameOver;
                }

                System.out.println("X: "+handler.getTanks(ID.Tank1).getX() +
                    " Y: "+handler.getTanks(ID.Tank1).getY() +
                    " Angle: "+handler.getTanks(ID.Tank1).getAngle());
                System.out.println("\nX: "+handler.getTanks(ID.Tank2).getX() +
                    " Y: "+handler.getTanks(ID.Tank2).getY() +
                    " Angle: "+handler.getTanks(ID.Tank2).getAngle());

                Thread.sleep(1000 / 144);
            }
        } catch (InterruptedException ignored) {

        }
    }

    @Override
    public void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        if (ID.Menu == id) {
            g2.setColor(Color.black);
            g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
            menu.drawImage(g);

        }
        if (ID.Help == id) {
            help.drawImage(g);
        }
        if (ID.Start == id) {
            buffer = world.createGraphics();
            c1.tick(handler.getTanks(ID.Tank1));
            c2.tick(handler.getTanks(ID.Tank2));
            try {

                BufferedImage newWrold = world.getSubimage((int) c1.getX(), (int) c1.getY(), 640, SCREEN_HEIGHT);
                BufferedImage newWrold2 = world.getSubimage((int) c2.getX(), (int) c2.getY(), 640, SCREEN_HEIGHT);


                super.paintComponent(g2);


                buffer.drawImage(floor, 0, 0, SCREEN_WIDTH * 2+300, SCREEN_HEIGHT * 2, null);
                handler.render(buffer);
                g2.drawImage(newWrold, 0, 0, null);
                g2.drawImage(newWrold2, 640, 0, null);
                g2.drawImage(world, 550, 760, 200, 200, null);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (ID.GameOver == id) {
            g2.drawImage(gameOver, 0, 0, 1280, 960, null);

        }

    }


    public void loadMap() {
        int w = level.getWidth();
        int h = level.getHeight();

        for (int xx = 0; xx < w; xx++) {
            for (int yy = 0; yy < h; yy++) {
                int pixel = level.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                if (red == 255) {
                    handler.addObject(new UnbreakableBox(xx * 32, yy * 32, ID.UnbreakableBox, wall));
                }
                if (red == 251) {
                    handler.addObject(new BreakableBox(xx * 32, yy * 32, ID.Box, breakable, handler));
                }
                if(red == 247){
                    handler.addObject((new Shield(xx*32,yy*32,ID.PowerUps,shield,handler)));
                }
                if(red == 240){
                    handler.addObject(new ExtraLife(xx*32,yy*32,life,ID.Life,handler));
                }
            }
        }
    }

    public void tick() {
        handler.update();
    }

    public void setId(ID id) {
        this.id = id;
    }
}
