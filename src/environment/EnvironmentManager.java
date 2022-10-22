//Xử lí ánh sáng và thêm hiệu ứng
package environment;

import main.GamePanel;
import java.awt.Graphics2D;
public class EnvironmentManager {
    GamePanel gp;
    Lighting lighting;

    public EnvironmentManager(GamePanel gp){
        this.gp=gp;
    }
    public void setup(){
        //Sezi of circle
        lighting = new Lighting(gp, 500);

    }
    public void draw(Graphics2D g2){
        lighting.draw(g2);
    }
}
