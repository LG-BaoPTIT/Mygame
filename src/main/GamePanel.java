package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import object.SuperObject;
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
        KeyHandler keyH = new KeyHandler();
        Sound music = new Sound();
        Sound se = new Sound();
	
	public CollisionChecker cChecker =new CollisionChecker(this);
        public AssetSetter aSetter = new AssetSetter(this);
        public UI ui = new UI(this);
        Thread gameThread;
//        ENTITY AND OBJECT
        
        
        public Player player = new Player(this,keyH);
        public SuperObject obj[] = new SuperObject[10];
        
	
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
            
            playMusic(0);
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
		player.update();
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
		

        //TILE
        tileM.draw(g2);
                
        // OBJECT
                    for(int i = 0 ; i < obj.length; i++) {
                    
                        if(obj[i] != null) {
                        
                            obj[i].draw(g2, this);
                        }
                    }
                
        //PLAYER
		player.draw(g2);
        //UI
        ui.draw(g2);
		
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
