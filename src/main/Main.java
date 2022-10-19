package main;
//hai con than lan con ahihi
//1234
import javax.swing.JFrame;

public class Main {
    
    public static JFrame window;

	public static void main(String[] args) {

		window = new JFrame();
		
		//make a proper 'X' closing button
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Adventure");
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
                
                gamePanel.config.loadConfig();
                if(gamePanel.fullScreenOn == true) {
                    window.setUndecorated(true);
                }
		
		// size the window to fit the preferred size of it subcomponent
		// <=> GamePanel
		window.pack();
		
		// not specify the position of the window <==> the window is 
		// centered on screen
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
        gamePanel.setupGame();
		gamePanel.startGameThread();
		
	}

}
