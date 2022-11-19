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
public class OBJ_Shield_Blue extends Entity{
     public OBJ_Shield_Blue(GamePanel gp) {
        super(gp);
        type = type_shiled;
        name = "Khien Xanh";
        down1 = setup("/objects/shield_blue",gp.tileSize,gp.tileSize);
        defenseValue = 2;
        description = "[" + name + "]\nMot chiec khien sang bong";
        price = 80;
    }
    
}
