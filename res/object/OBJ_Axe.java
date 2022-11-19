package object;

import entity.Entity;
import main.GamePanel;

/**
 *
 * @author admin
 */
public class OBJ_Axe extends Entity{
    
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        
        type = type_axe;
        name = "Riu Tho San";
        down1 = setup("/objects/axe", gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Riu tho san]\nMot chiec riu da cu \nnhung van dung duoc";
        price = 75;
        knockBackPower = 10;
    }
    
}
