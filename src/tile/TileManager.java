package tile;

import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

import javax.imageio.ImageIO;
import javax.swing.plaf.ColorUIResource;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gp;
    public Tile[] tile;
    public int mapTileNum[][][];
    boolean drawPath = false;
    ArrayList<String> fileNames = new ArrayList<>();
    ArrayList<String> collisinStatus = new ArrayList<>();
    public TileManager(GamePanel gp)  {
        
        this.gp = gp;
        //Doc file data
        InputStream is = getClass().getResourceAsStream("/maps/tiledata.txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        // geting tile name and collision info from the file
        String line;
        try {
            while((line = br.readLine())!=null){
                fileNames.add(line);
                collisinStatus.add(br.readLine());
            }
            br.close();
        } catch (IOException e) {
        
            e.printStackTrace();
        }
        tile = new Tile[fileNames.size()];
        getTileImage();

        //get the maxworldcoll & row
       

        mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];

        loadMap("/maps/worldmap.txt",0);
        loadMap("/maps/indoor01.txt",1);
        loadMap("/maps/dungeon01.txt",2);
        loadMap("/maps/dungeon02.txt",3);


    }
    
    public void getTileImage() {   

                for(int i=0;i<fileNames.size();i++){
                    String fileName;
                    boolean collison;
                    //Get a file name;
                    fileName = fileNames.get(i);
                    //Get a collision status
                    if(collisinStatus.get(i).equals("true")){
                        collison = true;
                    }
                    else{
                        collison=false;
                    }
                    setup(i, fileName, collison);
                    
                }    
        }
    public void setup(int index, String imageName, boolean collision ){
        UtilityTool uTool = new UtilityTool();
        try{
            tile[index] = new Tile();
            tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName ));
            tile[index].image= uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
            tile[index].collision = collision;
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    public void loadMap(String filePath,int map) {
        
        try {
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            
            int col = 0;
            int row = 0;
            
            while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
                String line = br.readLine();
                while(col < gp.maxWorldCol) {
                    String numbers[] = line.split(" ");
                    
                    int num = Integer.parseInt(numbers[col]);
                    
                    mapTileNum[map][col][row] = num;
                    col++;
                }
                if(col == gp.maxWorldCol) {
                    col = 0;
                    row++;
                }
            }
            br.close();
            
            
        }catch(Exception e) {
        
        }
    }
    public void draw(Graphics2D g2) {
        
        int worldCol = 0;
        int worldRow = 0;
        
        
        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
            
            int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
            
            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX =worldX -gp.player.worldX + gp.player.screenX;
            int screenY =worldY -gp.player.worldY + gp.player.screenY;
            
            if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
               worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&    
               worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&   
               worldY - gp.tileSize < gp.player.worldY + gp.player.screenY    
               ){
                g2.drawImage(tile[tileNum].image, screenX, screenY, null);
               
            }
            
             worldCol++;

            if(worldCol == gp.maxWorldCol) {
                worldCol = 0;
                worldRow++;
               
            }
        }
        // if(drawPath == true){
        //     g2.setColor(new Color(255,0,0,70));
        //     for(int i=0;i<gp.pFinder.pathList.size();i++){
        //         int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
        //         int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
        //         int screenX =worldX -gp.player.worldX + gp.player.screenX;
        //         int screenY =worldY -gp.player.worldY + gp.player.screenY;
                
        //         g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
        //     }
        // }
    }
}