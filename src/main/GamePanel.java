package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import ai.PathFinder;

import java.util.*;
import java.io.*;
import entity.Entity;
import entity.Player;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import tile.TileManager;
import tile_interactive.InteractiveTile;

// ke thua JPanel
public class GamePanel extends JPanel implements Runnable {
    //cai dat hien thi

    final int originalTileSize = 16; //16*16
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;// 48*48
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //960px
    public final int screenHeight = tileSize * maxScreenRow; //576px

    //setting map
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;
    
    //toan man hinh 
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;
    
    
    //FPS
    int FPS = 60;
    
    // he thong
    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler(this);
    Sound music = new Sound();
    Sound se = new Sound();

    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Config config = new Config(this);
    public PathFinder pFinder = new PathFinder(this);
    Thread gameThread;

    //ENTITY VA OBJECT
    public Player player = new Player(this, keyH);
    public Entity obj[][] = new Entity[maxMap][20];
    public Entity npc[][] = new Entity[maxMap][10];
    public Entity monster[][] = new Entity[maxMap][20];
    public InteractiveTile iTile[][] = new InteractiveTile[maxMap][50];
    public Entity projectile[][] = new Entity[maxMap][20];
   // public ArrayList<Entity> projectileList = new ArrayList<>();
    public ArrayList<Entity> particleList = new ArrayList<>();
    ArrayList<Entity> entityList = new ArrayList<>();

    // Trang thai game
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);

        //setDoubleBuffered : Neu set true ,tat ca se duoc ve truoc khi load 
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        //setFocusable(true): gamePanel nhan input de hon
        this.setFocusable(true);
    }

    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        gameState = titleState;
        
        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        
        if(fullScreenOn == true) {
            setFullScreen();
        }
        
    }
    public void retry(){
        player.setDefaultPositions();
        player.restoreLifeAndMan();
        aSetter.setNPC();
        aSetter.setMonster();
    }
    public void restart(){
        player.setDefaultValues();
        player.setDefaultPositions();
        player.restoreLifeAndMan(); 
        player.setItems();
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
    }
    public void setFullScreen() {
        
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);
        
        //Lay kich thuoc toan man hinh
        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
        
    }   
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; // 0.016666..7s moi lan update tren man hinh
        double nextDrawTime = System.nanoTime() + drawInterval; //lan update tiep theo = hien tai + 0,0166...7s

        while (gameThread != null) {

            //1: update thay doi tren man hinh vd: vi tri player
            update();
            //2: ve lai tren man hinh nhung thong tin duoc update
            drawToTempScreen();// ve truoc = buffer
            drawToScreen();// ve tu buffer le man hinh
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
               
                e.printStackTrace();
            }
        }
    }

    public void update() {

        if (gameState == playState) {
            // Nguoi choi
            player.update();
            // NPC
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    npc[currentMap][i].update();
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    if (monster[currentMap][i].alive == true && monster[currentMap][i].dying == false) {
                        monster[currentMap][i].update();
                    }
                    if (monster[currentMap][i].alive == false) {
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    if (projectile[currentMap][i].alive == true) {
                        projectile[currentMap][i].update();
                    }
                    if (projectile[currentMap][i].alive == false) {
                        projectile[currentMap][i] = null;
                    }
                }

            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    
                    if (particleList.get(i).alive == true) {
                        particleList.get(i).update();
                    }
                    if (particleList.get(i).alive == false) {
                        particleList.remove(i);
                    }
                }

            }
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
        }
        if (gameState == pauseState) {
            
        }
    }
    public void drawToTempScreen() {
        //DEBUG
        long drawStart = 0;
        if (keyH.checkDrawTime == true) {
            drawStart = System.nanoTime();
        }
        //Man hinh chinh
        if (gameState == titleState) {
            ui.draw(g2);
        } else {
            //TILE
            tileM.draw(g2);
            //TILE co the tuong tac duoc 
            for (int i = 0; i < iTile[1].length; i++) {
                if (iTile[currentMap][i] != null) {
                    iTile[currentMap][i].draw(g2);
                }
            }
            //Them thuc the vao danh sach
            entityList.add(player);
            for (int i = 0; i < npc[1].length; i++) {
                if (npc[currentMap][i] != null) {
                    entityList.add(npc[currentMap][i]);
                }
            }
            for (int i = 0; i < obj[1].length; i++) {
                if (obj[currentMap][i] != null) {
                    entityList.add(obj[currentMap][i]);
                }
            }
            for (int i = 0; i < monster[1].length; i++) {
                if (monster[currentMap][i] != null) {
                    entityList.add(monster[currentMap][i]);

                }
            }

            for (int i = 0; i < projectile[1].length; i++) {
                if (projectile[currentMap][i] != null) {
                    entityList.add(projectile[currentMap][i]);
                }
            }
            for (int i = 0; i < particleList.size(); i++) {
                if (particleList.get(i) != null) {
                    entityList.add(particleList.get(i));
                }
            }
            //sap xep
            Collections.sort(entityList, new Comparator<Entity>() {
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
            });
            //ve thuc the
            for (int i = 0; i < entityList.size(); i++) {
                entityList.get(i).draw(g2);
            }
            //clear list
            entityList.clear();
            //UI
            ui.draw(g2);
        }

        //DEBUG
        if (keyH.checkDrawTime == true) {
            long drawEnd = System.nanoTime();
            long passed = drawEnd - drawStart;
            g2.setColor(Color.white);
            g2.drawString("Draw Time" + passed, 10, 400);
            System.out.println("Draw Time:" + passed);
        }
    }
    public void drawToScreen() {
        
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();

    }

    public void stopMusic() {
        music.stop();
    }

    public void playSE(int i) {

        se.setFile(i);
        se.play();
    }
}
