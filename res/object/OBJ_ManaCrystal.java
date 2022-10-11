package object;

import main.GamePanel;
import entity.Entity;
public class OBJ_ManaCrystal extends Entity{
    GamePanel gp;
    public OBJ_ManaCrystal(GamePanel gp){
        super(gp);
        this.gp = gp;
        name ="Mana Crystal";
        image = setup("/objects/manacrystal_full",gp.tileSize,gp.tileSize);
        image2 = setup("/objects/manacrystal_blank",gp.tileSize,gp.tileSize);
    }
}
