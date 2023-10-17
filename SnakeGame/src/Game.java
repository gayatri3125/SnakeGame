import javax.swing.JFrame;

public class Game {
public static void main(String[] args) {
	int width=600;
	int height=width;
	JFrame frame= new JFrame();
	frame.setVisible(true);
	frame.setSize(width, height);
	frame.setLocationRelativeTo(null);
	frame.setResizable(false);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	snakeGame sg= new snakeGame(width, height);
	frame.add(sg);
	frame.pack();
	sg.requestFocus();
}
}
