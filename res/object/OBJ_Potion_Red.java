/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package object;

import entity.Entity;
import main.GamePanel;

/**
 *
 * @author admin
 */
public class OBJ_Potion_Red extends Entity{
    GamePanel gp;
 
    
    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
        
        this.gp = gp;
        type = type_consumable;
        name = "Red_Potion";
        value = 5;
        down1 = setup("/objects/potion_red",gp.tileSize, gp.tileSize);
        description = "[Red Potion]\nheals your life by " + value + ".";
        price = 25;
    }
    public boolean use(Entity entity) {
        
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the " + name + "!\n"
            + "Your life has been recovered by " + value + ".";
        entity.life += value;
        gp.playSE(2);
        return true;
    }
    
}
