package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_BlackFire extends Projectile
{
    GamePanel gp;
    public OBJ_BlackFire(GamePanel gp) {
        super(gp);
        this.gp=gp;

        name = "FireBall";
        speed = 5;
        maxLife = 80;
        life = maxLife;
        attack = 5;
        useCost = 1;
        alive = false;
        getImage();
    }
    
    public void getImage(){
        up1 = setup("/projectile/blackfire_up_1",gp.tileSize, gp.tileSize);
        up2 = setup("/projectile/blackfire_up_2",gp.tileSize, gp.tileSize);
        down1 = setup("/projectile/blackfire_down_1",gp.tileSize, gp.tileSize);
        down2 = setup("/projectile/blackfire_down_2",gp.tileSize, gp.tileSize);
        left1 = setup("/projectile/blackfire_left_1",gp.tileSize, gp.tileSize);
        left2 = setup("/projectile/blackfire_left_2",gp.tileSize, gp.tileSize);
        right1 = setup("/projectile/blackfire_right_1",gp.tileSize, gp.tileSize);
        right2 = setup("/projectile/blackfire_right_2",gp.tileSize, gp.tileSize);
    }
    public boolean haveResource(Entity user){
        boolean haveResource = false;
        if(user.mana >= useCost){
            haveResource = true;
        }
        return haveResource;
    }
    public void suptractResource(Entity user){
        user.mana -= useCost;
    }
}