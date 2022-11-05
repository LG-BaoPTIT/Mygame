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
public class OBJ_Shield_Wood extends Entity{
    public static final String objName = "Shield Wood";
    public OBJ_Shield_Wood(GamePanel gp) {
        super(gp);
        type = type_shiled;
        name = objName;
        down1 = setup("/objects/shield_wood",gp.tileSize,gp.tileSize);
        defenseValue = 1;
        description = "[" + name + "]\nMade by wood.";
        price = 30;
        
    }
    
}
