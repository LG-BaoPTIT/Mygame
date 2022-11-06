package tile_interactive;
import java.awt.Color;
import entity.*;
import main.GamePanel;

public class IT_DestructibleWall extends InteractiveTile {
    public IT_DestructibleWall(GamePanel gp,int col, int row){
        super(gp,col,row);
        this.gp = gp;
        this.worldX = gp.tileSize * col;
        this.worldY = gp.tileSize * row;
        down1 = setup("/tiles_interactive/destructiblewall",gp.tileSize,gp.tileSize);
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(Entity entity){
        boolean isCorrectItem = false;
        if(entity.currentWeapon.type == type_pickaxe){
            isCorrectItem = true;
        }
        return isCorrectItem;
    }
    public void playSE(){
        gp.playSE(20);
    }
    public InteractiveTile getDestroyedForm(){
        InteractiveTile tile = null;
        return tile;
    }
    
    public Color getPaticleColor(){
        Color color = new Color(65,65,65);
        return color;
    }
    public int getParticleSize(){
        int size = 6; //6 pixles
        return size;
    }
    public int getParticalSpeed(){
        int speed = 1;
        return speed;
    }
    public int getParticleMaxLife(){
        int maxLife = 20;
        return maxLife;
    }
}
