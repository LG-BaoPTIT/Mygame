package entity;

import java.util.Random;

import java.awt.Rectangle;
import main.GamePanel;

public class NPC_OldMan extends Entity{

    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 1;

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width =30;
        solidArea.height = 30;

        getImage();
        setDialogue();
    }
    public void getImage() {
		
        up1 = setup("/npc/oldman_up_1",gp.tileSize, gp.tileSize);
        up2 = setup("/npc/oldman_up_2",gp.tileSize, gp.tileSize);
        down1 = setup("/npc/oldman_down_1",gp.tileSize, gp.tileSize);
        down2 = setup("/npc/oldman_down_2",gp.tileSize, gp.tileSize);
        left1 = setup("/npc/oldman_left_1",gp.tileSize, gp.tileSize);
        left2 = setup("/npc/oldman_left_2",gp.tileSize, gp.tileSize);
        right1 = setup("/npc/oldman_right_1",gp.tileSize, gp.tileSize);
        right2 = setup("/npc/oldman_right_2",gp.tileSize, gp.tileSize);
        
	}
    public void setDialogue(){
        dialogues[0] = "Xin chao ke phieu luu.";
        dialogues[1] = "Cau den hon dao nay de\ntim kho bau sao?";
        dialogues[2] = "Ta la mot phap su... \nNhung qua gia de mao hiem lan nua.";
        dialogues[3] = "Chuc may man.";

    }
    public void setAction() {
        if(onPath == true){
            int goalCol=(gp.player.worldX + gp.player.solidArea.x)/gp.tileSize;
            int goalRow=(gp.player.worldY + gp.player.solidArea.y)/gp.tileSize;
            searchPath( goalCol, goalRow);

        }
        else {
            actionLockCounter ++;

            if(actionLockCounter == 120) {
                Random random = new Random();
                int i = random.nextInt(100) + 1;// ngau nhien chon 1 so 1 -> 100
    
                if( i <= 25) {
                    direction = "up";
                }
                if( i > 25 && i <= 50) {
                    direction = "down";
                }
                if(i > 50 && i <= 75) {
                    direction = "left";
                } 
                if(i>75 && i <= 100) {
                    direction = "right";
                }
    
                actionLockCounter = 0;
            } 
        }

    }
    public void speak(){
        super.speak();
        onPath = true;
    }
}
