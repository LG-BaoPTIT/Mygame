package my2dgame;
//1111111111111111111111111
import javax.swing.JFrame;

/**
 *
 * @author LGB
 */
public class My2DGame {

   
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false); //Can't resize the window
        window.setTitle("2D Adventure 1.0");
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
        gamePanel.startGameThread();
    }
    
}
