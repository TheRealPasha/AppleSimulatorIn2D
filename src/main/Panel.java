package main;

import data.Data;
import entity.Player;
import objects.Collectables;
import objects.Objects;
import objects.eggs.Egg1;
import objects.eggs.Eggs;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel implements Runnable {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public final int SCREEN_WIDTH = (int) screenSize.getWidth();
    public final int SCREEN_HEIGHT = (int) screenSize.getHeight();

    public final int TILE_SIZE = 120;
    public final double SCALE = 1.5;

    public final int SCALED_TILE_SIZE = (int) (TILE_SIZE * SCALE);

    //public final int MAX_SCREEN_COL = (int) Math.ceil((double) SCREEN_WIDTH / SCALED_TILE_SIZE);
   // public final int MAX_SCREEN_ROW = (int) Math.ceil((double) SCREEN_HEIGHT / SCALED_TILE_SIZE);

    Thread gameThread;
    public KeyHandler keyHandler = new KeyHandler();
   // public Graphics gr;
    public Player player = new Player(this,keyHandler);
    public TileManager tileManager = new TileManager(this);
    public CollisionHandler collisionHandler = new CollisionHandler(this);
    public final Collectables[] coll = new Collectables[50];
    public final Objects[] obj = new Objects[3];
    public final Eggs[] eggs = new Eggs[3];
    public AssetSetter assetSetter = new AssetSetter(this);
    public JLayeredPane layeredPane;
    public Data data;
    {
        try {
            data = new Data(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public Sound sound = new Sound();
    public static Panel panel;

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    //public final int worldWidth = TILE_SIZE * maxWorldCol;
   // public final int worldHeight = TILE_SIZE * maxWorldRow;;

    final int fps = 60;

    public Panel(JLayeredPane layeredPane) {
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.decode("#78abe0"));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
        this.layeredPane = layeredPane;
        panel = this;
        //System.out.println(this.getParent().toString());
    }

    public void setupGame() {
        assetSetter.generateObjects();

        eggs[0] = new Egg1(this);

        System.arraycopy(eggs, 0, obj, 0, eggs.length);

        playMusic(0);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void playMusic(int i) {
        sound.setFile(i, 0.15f);
        sound.play();
        sound.loop();
    }

    public void stopMusic() {
        sound.stop();
    }

    public void playSound(int i) {
        sound.setFile(i, 1f);
        sound.play();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/fps;
        double nextDraw = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = nextDraw - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);

                nextDraw += drawInterval;
            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
                System.out.println("Exception");
                e.printStackTrace();
            }
        }
    }
    public void update() {
        player.update();
        if (!this.isFocusOwner() && KeyboardFocusManager.getCurrentKeyboardFocusManager().getFocusOwner() != null) {
           this.requestFocus();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {

                obj[i].draw(g2, this, i);
            }
        }

        player.draw(g2);

        for (int i = 0; i < coll.length; i++) {
            if (coll[i] != null) {

                coll[i].draw(g2, this, i);
            }
        }
       // System.out.println(System.nanoTime() - start);
        g2.dispose();
    }
}
