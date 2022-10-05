package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;
import java.util.*;
import java.io.*;
import entity.Entity;
import entity.Player;

import tile.TileManager;

// inherited JPanel
//12345132132
public class GamePanel extends JPanel implements Runnable{
	//Screen setting
	final int originalTileSize = 16; //16*16
	final int scale = 3;  
	public final int tileSize = originalTileSize*scale;// 48*48
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize*maxScreenCol; //768px
	public final int screenHeight = tileSize*maxScreenRow; //576px
	
        //world setting
        public final int maxWorldCol = 50;
        public final int maxWorldRow = 50;
//        public final int worldWidth = tileSize * maxWorldCol ;
//	public final int worldHeight = tileSize * maxWorldRow ;
        //FPS
	int FPS = 60;
	
	TileManager tileM = new TileManager(this);
        public KeyHandler keyH = new KeyHandler(this);
        Sound music = new Sound();
        Sound se = new Sound();
	
	public CollisionChecker cChecker =new CollisionChecker(this);
        public AssetSetter aSetter = new AssetSetter(this);
        public UI ui = new UI(this);
        public EventHandler eHandler = new EventHandler(this);
        Thread gameThread;
	//ENTITY AND OBJECT
          
        public Player player = new Player(this,keyH);
        public Entity obj[] = new Entity[10];
	public Entity npc[] = new Entity[10];
        public Entity monster[] = new Entity[20];
        ArrayList<Entity> entityList = new ArrayList<>();
        
	// GAME STATE
	public int gameState;
        public final int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int dialogueState = 3;


	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.black);
		
		//setDoubleBuffered : If set to true, all the drawing 
		//from this component will be done in an off screen painting buffer.
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		//setFocusable(true): gamePanel can focus to receive key input
		this.setFocusable(true);
	}
        
        public void setupGame() {
            aSetter.setObject();
            aSetter.setNPC();
            aSetter.setMonster();
//            playMusic(0);
            gameState = titleState;
        }
        
	
	public void startGameThread() {
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS; // 0.016666..7s for each time drawing
		double nextDrawTime = System.nanoTime() + drawInterval; //next draw time = current time + 0.01666...7
		
		while(gameThread != null) {
			
			
			//1: Update the information such as character positions
			update();
			//2: Draw the screen with the updated information
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime/=1000000;
				
				if(remainingTime < 0 ) {
					remainingTime = 0;
				}
				
				Thread.sleep((long)remainingTime);
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void update() {

		if(gameState == playState) {
                    // PLAYER
                    player.update();
                    // NPC
                    for(int i = 0; i < npc.length;i++) {
                        if(npc[i] != null) {
                            npc[i].update();
			}
                    }
                    for (int i = 0; i < monster.length;i++) {
                        if(monster[i] != null) {
                            monster[i].update();
                        }
                    }
		}
		if(gameState == pauseState) {
			// nothing
		}
	}
	
	public void paintComponent(Graphics g) { // this is repaint method
		//Graphics is the class drawing object 
		
		super.paintComponent(g);
		//super is object of the father class
		
		Graphics2D g2 = (Graphics2D)g;
		// //DEBUG
		long drawStart = 0;
		if(keyH.checkDrawTime == true){
			drawStart = System.nanoTime();
		}
                //TITLE SCREEN
                if(gameState == titleState) {
                    ui.draw(g2);
                }
                else {
                    //TILE
                    tileM.draw(g2);		
                    //ADD ENTITIES TO THE LIST
                    entityList.add(player);
                    for(int i=0;i<npc.length;i++){
			if(npc[i] != null){
                            entityList.add(npc[i]);
			}
                    }
                    for(int i=0;i<obj.length;i++){
			if(obj[i] != null){
                            entityList.add(obj[i]);
			}
                    }
                    for(int i=0;i<monster.length;i++){
			if(monster[i] != null){
                            entityList.add(monster[i]);
			}
                    }
                    //SORT
                    Collections.sort(entityList, new Comparator<Entity>() {
                    @Override
                    public int compare(Entity e1, Entity e2){
			int result = Integer.compare(e1.worldY, e2.worldY);
                            return result;
                    }
                    });
                    //DRAW ENTITIES
                    for(int i=0;i<entityList.size();i++){
			entityList.get(i).draw(g2);
                    }
                    //EMPTY ENTITY LIST
                    entityList.clear();
                    //UI
                    ui.draw(g2);
                }
                
		
		 //DEBUG
		if(keyH.checkDrawTime==true){
			long drawEnd =System.nanoTime();
			long passed = drawEnd - drawStart;
			g2.setColor(Color.white);
			g2.drawString("Draw Time" + passed, 10, 400);
			System.out.println("Draw Time:" + passed);
		}
		


		//Dispose of this graphics context and release any system resources that it is using
		g2.dispose();
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
