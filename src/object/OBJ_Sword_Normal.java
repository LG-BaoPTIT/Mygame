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
public class OBJ_Sword_Normal extends Entity{
    public static final String objName = "Sword Normal";
    public OBJ_Sword_Normal(GamePanel gp) {
        super(gp);
        
        type = type_sword;
        name = objName;
        down1 = setup("/objects/sword_normal", gp.tileSize,gp.tileSize);
        attackValue = 1;
        attackArea.width = 36;
        attackArea.height = 36;
        description = "[" + name + "]\nAn old sword.";
        price = 30;
        knockBackPower = 2;
        motion1_duration = 5;
        motion2_duration = 25;
    }
    
}
