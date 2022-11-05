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
public class OBJ_Boots extends Entity {
   
    public static final String objName = "Boots";

    public OBJ_Boots(GamePanel gp) {
        super(gp);
        name = objName;
        down1 = setup("/objects/boots",gp.tileSize, gp.tileSize);
    }
}
