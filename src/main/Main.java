package main;
import javax.swing.JFrame;

public class Main {

    public static JFrame window;
	public static void main(String[] args) {

		window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Dòng code tr
		window.setResizable(false);
		window.setTitle("2D Adventure");// Thay đổi tiêu đề của cửa sổ 

		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel); // thêm gamePanel vào cửa sổ 
        gamePanel.config.loadConfig();
		// 
        if(gamePanel.fullScreenOn == true) { // nếu ấn vào nút fullScreen thì 
            window.setUndecorated(true);// Tạo khung tràn viền của màn hình
        }
 
		// kích thước khung để phù hợp với kích thước ưu thích của thành phần phụ
		// <=> GamePanel
		window.pack();

		
		
		window.setLocationRelativeTo(null);// hiển thị của sổ lên chính giữa màn hình
		window.setVisible(true);// thiết lập khả năng hiện thị của thành phần 

        gamePanel.setupGame();
		gamePanel.startGameThread();

	}

}
