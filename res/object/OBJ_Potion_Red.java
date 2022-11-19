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
        name = "Binh Mau";
        value = 5;
        down1 = setup("/objects/potion_red",gp.tileSize, gp.tileSize);
        description = "[Binh Mau]\nPhuc hoi " + value + " hp.";
        price = 25;
    }
    public void use(Entity entity) {
        
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "Su dung " + name + "!\n"
            + "Mau hoi: " + value + ".";
        entity.life += value;
        gp.playSE(2);
    }
    
}
