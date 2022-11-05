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
public class OBJ_Axe extends Entity{

    public static final String objName = "Fighter's Axe";
    
    public OBJ_Axe(GamePanel gp) {
        super(gp);
        
        type = type_axe;
        name = objName;
        down1 = setup("/objects/axe", gp.tileSize,gp.tileSize);
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "[Woodcutter's Axe]\nA bit rusty but still \ncan cut some tree.";
        price = 75;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;
    }
    
}
