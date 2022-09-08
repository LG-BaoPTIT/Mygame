/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my2dgame;

import entity.Player;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.plaf.PanelUI;

/**
 *
 * @author LGB
 */
public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3 ;
    
    public final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize *  maxScreenCol;// 786 pixels
    final int screenHeight = tileSize * maxScreenRow;//576 pixels
    
    
    //FPS
    int FPS =60;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Player player = new Player(this,keyH);
    //
    int playerX=100;
    int playerY=100;
    int playerSpeed=4;
    
    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        
    }
    public void startGameThread(){
            
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override  
    public void run (){
        double drawInterval = 1000000000/FPS; 
        double nextDrawTimo =System.nanoTime() + drawInterval;
        while(gameThread != null){
            long currentTime = System.nanoTime();
            

           // 1 UPDATE: cap nhap vi tri thong tin nhan vat;
           update();
           // 2 DRAW:ve ra man hinh voi thong tin dc update;
           repaint();
           
           try{
               double remainingTime = nextDrawTimo -  System.nanoTime();;
               remainingTime = remainingTime/1000000;
               
               if(remainingTime<0){
                   remainingTime=0;
               }
               Thread.sleep((long)remainingTime);
               nextDrawTimo += drawInterval;
               
           } catch (InterruptedException e){
               e.printStackTrace();
           }
                    
        }
    }
    
    public void update(){
        player.update();
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 =(Graphics2D)g;
        player.draw(g2);
        g2.dispose();
        
    }
}
