/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package my2dgame;

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
    
    final int tileSize = originalTileSize * scale; //48x48 tile
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize *  maxScreenCol;// 786 pixels
    final int screenHeight = tileSize * maxScreenRow;//576 pixels
    
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    
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
        while(gameThread != null){
            long currentTime = System.nanoTime();
            

           // 1 UPDATE: cap nhap vi tri thong tin nhan vat;
           update();
           // 2 DRAW:ve ra man hinh voi thong tin dc update;
           repaint();
                    
        }
    }
    
    public void update(){
        if(keyH.upPressed == true){
            playerY -= playerSpeed;
            
        }
        else if(keyH.downPressed == true){
            playerY += playerSpeed;
            
        }
        else if(keyH.leftPressed == true){
            playerX -= playerSpeed;
            
        }
        else if(keyH.rightPressed == true){
            playerX += playerSpeed;
            
        }
        
        
    }
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        
        Graphics2D g2 =(Graphics2D)g;
        g2.setColor(Color.WHITE);
        g2.fillRect(playerX, playerY, tileSize, tileSize);
        g2.dispose();
        
    }
}
